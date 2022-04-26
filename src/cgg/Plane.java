package cgg;

import static cgtools.Vector.*;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;

public class Plane implements Shape {

	public final Point p;
	public final Direction n;
	public final double r;
	public final Material m;
	
	public Plane(Material material) {
		this(zero, yAxis, 1, material);
	}
	
	public Plane(Point p, Direction n, double r, Material m) {
		this.p = p;
		this.n = normalize(n);
		this.r = r;
		this.m = m;
	}
	
	public Plane(Point p, Direction n, Material m) {
		this(p, n, Double.POSITIVE_INFINITY, m);
	}
	
	public Plane(Point p, Direction n, double r, Color c) {
		this(p, n, r, new DiffusedMaterial(c));
	}
	
	public Plane(Point p, Direction n, Color c) {
		this(p, n, Double.POSITIVE_INFINITY, c);
	}
	
	@Override
	public BoundingBox bound() {
		return BoundingBox.everything;
	}

	@Override
	public Hit intersect(Ray ray) {
		double t = calculateT(ray);
		Point x = ray.pointAt(t);
		if (x != null && length(subtract(x, p)) < r) {
			return new Hit(t, x, n, x.x/(2*r)+0.5, x.z/(2*r)+0.5, m);
		} else {
			return null;
		}
	}
	
	public double calculateT(Ray ray) {
		double dividend = dotProduct(subtract(p, ray.x0), n);
		double divisor = dotProduct(ray.d, n);
		return dividend/divisor;
	}
	
	@Override
	public String toString() {
		return "Plane " + m.toString();
	}
}
