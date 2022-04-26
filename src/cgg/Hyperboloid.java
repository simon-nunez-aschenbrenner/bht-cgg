package cgg;

import static cgtools.Vector.direction;
import static cgtools.Vector.dotProduct;
import static cgtools.Vector.negate;
import static cgtools.Vector.normalize;
import static cgtools.Vector.point;

import cgtools.Direction;
import cgtools.Point;

public class Hyperboloid implements Shape {
	
	public final Material m;
	public final double r; // Mittelradius
	public final double o; // Obere Begrenzung
	public final double u; // Untere Begrenzung
	public final double f; // Formparameter
	public final boolean noBB;

	public Hyperboloid(Material m, double r, double o, double u, double f, boolean noBB) {
		this.m = m;
		this.r = r;
		this.o = o;
		this.u = u;
		this.f = f;
		this.noBB = noBB;
	}
	
	public Hyperboloid(Material m, double r, double o, double u, double f) {
		this(m, r, o, u, f, false);
	}
	
	public Hyperboloid(Material m, double r, double h, double g) {
		this(m, r, h/2, -h/2, g, false);
	}
	
	public Hyperboloid(Material m) {
		this(m, 1, 0.5, -0.5, 1, false);
	}

	@Override
	public BoundingBox bound() {
		if (noBB) return BoundingBox.everything;
		else {
			double h = Math.max(Math.abs(o), Math.abs(u)) * 2;
			double outerR = r * Math.sqrt(1 + (h*h) / (4*f*f));
			Point max = point(outerR, o, outerR);
			Point min = point(-outerR, u, -outerR);
			return new BoundingBox(min, max);
		}
	}

	@Override
	public Hit intersect(Ray ray) {
		
		// f(x,y,z) = x^2 - g*y^2 + z^2 = r
		
		// Quelle: http://skuld.bmsc.washington.edu/people/merritt/graphics/quadrics.html
		
		double xo = ray.x0.x;
		double yo = ray.x0.y;
		double zo = ray.x0.z;
		
		double xd = ray.d.x;
		double yd = ray.d.y;
		double zd = ray.d.z;
		
		double a = xd*xd - yd*yd/f + zd*zd;
		double b = 2*xo*xd - 2*yo*yd/f + 2*zo*zd;
		double c = xo*xo - yo*yo/f + zo*zo - r;
		
		double d = b*b - 4*a*c;
		
		if (d < 0) return null;  // Strahl schneidet Hyperboloid nicht
		
		else {
			double t1 = (-b - Math.sqrt(d)) / (2*a);
			double t2 = (-b + Math.sqrt(d)) / (2*a);
			// Evtl. Tauschen, um kleinstes t zuerst zu prüfen
			if (t1 > t2) {
				double temp = t2;
				t2 = t1;
				t1 = temp;
			}
			Hit h1 = null;
			Hit h2 = null;
			if (t1 > 0) h1 = calculateHit(ray, t1); // d >= 0, mindestens ein möglicher Schnittpunkt
			if (h1 != null) return h1;
			else if (t2 > 0 && t1 != t2) h2 = calculateHit(ray, t2); // d > 0, zwei mögliche Schnittpunkte
			return h2;
		}
	}
		
	private Hit calculateHit(Ray ray, double t) {
		Point x = ray.pointAt(t);
		if (x != null) { // Schnittpunkt liegt zwischen t_min und t_max
			if (x.y > u && x.y < o) { // Schnittpunkt liegt zwischen oberer und unterer Grenze
				
				double xn = 2*x.x;
				double yn = -2*x.y/f;
				double zn = 2*x.z;
				Direction n = normalize(direction(xn, yn, zn));
				if (dotProduct(n, ray.d) > 0) n = negate(n);
				
				return new Hit(t, x, n, x.x/(2*r)+0.5, x.z/(2*r)+0.5, m);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Hyperboloid " + m.toString();
	}
}
