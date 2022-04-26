package cgg;

import static cgtools.Vector.add;
import static cgtools.Vector.direction;
import static cgtools.Vector.directionTo;
import static cgtools.Vector.dotProduct;
import static cgtools.Vector.multiply;
import static cgtools.Vector.normalize;
import static cgtools.Vector.squaredLength;
import static cgtools.Vector.subtract;
import static cgtools.Vector.yAxis;
import static cgtools.Vector.zero;

import cgtools.Direction;
import cgtools.Point;

public class Cylinder implements Shape {
	
	public static final Point p = zero; // Unterer, mittlerer Endpunkt des Zylinders
	public static final Direction v = yAxis; // Zylindermittelpunktsgerade
	
	public final double l; // Länge
	public final double r; // Radius
	public final Material m; // Material des Zylindermantels

	public Cylinder(Material m) {
		this(1, 1, m);
	}
	
	public Cylinder(double l, double r, Material m) {
		this.l = l;
		this.r = r;
		this.m = m;
	}
	
	@Override
	public BoundingBox bound() throws IllegalArgumentException {
		if (!v.equals(yAxis)) throw new IllegalArgumentException("Zylindermittelpunktsgerade muss Y-Achse sein");
		Point min = subtract(p, direction(r, 0, r));
		Point max = add(p, direction(r, l, r));
		return new BoundingBox(min, max);
	}

	@Override
	public Hit intersect(Ray ray) {
				
		// Quelle: https://www.matheboard.de/archive/1188/thread.html
		Direction e = subtract(ray.d, multiply(dotProduct(ray.d, v)/squaredLength(v), v));
		Direction f = subtract(directionTo(ray.x0), multiply(dotProduct(directionTo(ray.x0), v)/squaredLength(v), v));
		double a = dotProduct(e, e);
		double b = 2 * dotProduct(e, f);
		double c = dotProduct(f, f) - r*r;
		double d = b*b - 4*a*c;
		
		if (d < 0) return null;  // Strahl schneidet Zylindermantel nicht
		
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
			if (t2 > 0 && t1 != t2) h2 = calculateHit(ray, t2); // d > 0, zwei mögliche Schnittpunkte
			
			if (h1 != null) return h1;
			else if (h2 != null) return h2;
			else return null;
		}
	}
		
	private Hit calculateHit(Ray ray, double t) {
		Point x = ray.pointAt(t);
		if (x != null) { // Schnittpunkt liegt zwischen t_min und t_max
			double d = dotProduct(x, v); // Projektion des Trefferpunktes auf die Zylindermittelpunktsgerade
			if (d > 0 && d < l) { // Schnittpunkt liegt zwischen Deckel und Boden
				Direction n = normalize(directionTo(subtract(x, multiply(v, d)))); // Normale geht von Mittelpunktsgerade durch den Trefferpunkt
				double[] uv = Utils.sphereTextureCoordinates(n);
				return new Hit(t, x, n, uv[0], -d, m);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Cylinder " + m.toString();
	}
}
