package cgg;

import static cgtools.Vector.*;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Vector;

public class Sphere implements Shape {
	
	public final Point center;
	public final double radius;
	public final Material material;
	
	public Sphere(Material material) {
		this(zero, 1, material);
	}
	
	public Sphere(Point center, double radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material = material;
	}
	
	public Sphere(double radius, Material material) {
		this(zero, radius, material);
	}
	
	public Sphere(Point center, double radius, Color color) {
		this(center, radius, new DiffusedMaterial(color));
	}
	
	public Sphere(Point center, double radius) {
		this(center, radius, new DiffusedMaterial());
	}
	
	@Override
	public BoundingBox bound() {
		return new BoundingBox(point(center.x-radius, center.y-radius, center.z-radius),
				point(center.x+radius, center.y+radius, center.z+radius));
	}
	
	@Override
	public Hit intersect(Ray ray) {	
		Point x0 = ray.x0;
		Vector x0mvd = subtract(x0, center);
		double a = dotProduct(ray.d, ray.d);
		double b = 2 * dotProduct(x0mvd, ray.d);
		double c = dotProduct(x0mvd, x0mvd) - (radius * radius);
		double d = b*b - 4*a*c;
				
		if (d < 0) {
			return null;
		} else {
			
			double t1 = Double.MAX_VALUE;
			double t2 = Double.MAX_VALUE;
			
			if (d > 0) { t2 = (-b - Math.sqrt(d)) / (2*a); }
			t1 = (-b + Math.sqrt(d)) / (2*a);
			
			if (t1 > t2) {
				double temp = t2;
				t2 = t1;
				t1 = temp;
			}
			
			Point x1 = ray.pointAt(t1);
			Point x2 = ray.pointAt(t2);
			
			if (x1 != null) {
				Direction n = normalize(divide(subtract(x1, center), radius));
				double[] uv = Utils.sphereTextureCoordinates(n);
				return new Hit(t1, x1, n, uv[0], uv[1], material);
			} else if (x2 != null) {
				Direction n = normalize(divide(subtract(x2, center), radius));
				double[] uv = Utils.sphereTextureCoordinates(n);
				return new Hit(t2, x2, n, uv[0], uv[1], material);
			} else {
				return null;
			}
		}
	}
	
	@Override
	public String toString() {
		return "Sphere " + material.toString();
	}
}
