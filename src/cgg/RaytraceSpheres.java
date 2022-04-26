package cgg;

import java.util.ArrayList;
import cgtools.Color;
import cgtools.Vector;
import cgtools.Direction;

public class RaytraceSpheres {
	
	public final Camera camera; 
	public final Color background;
	private ArrayList<Sphere> spheres;
	
	public RaytraceSpheres(Camera camera, Color background) {
		this.camera = camera;
		this.background = background;
		this.spheres = new ArrayList<Sphere>();
	}
	
	public Color getColor(double x, double y) {
		Ray ray = camera.generateRay(x, y);
		double t = Double.POSITIVE_INFINITY;
		Direction normal = null;
		Color color = null;
		
		for (Sphere s : spheres) {
			Hit hit = s.intersect(ray);
			if (hit != null && hit.t < t) {
				t = hit.t;
				normal = hit.n;
//				color = hit.color;
			}
		}	
		if (t != Double.POSITIVE_INFINITY) {
			return lightSurface(normal, color);
		} else {
			return background;
		}
	}
	
	public boolean add(Sphere sphere) {
		return spheres.add(sphere);
	}
	
	private Color lightSurface(Direction normal, Color color) {	
		Direction lightDir = Vector.normalize(Vector.direction(1, 1, 0.5));
		Color ambient = Color.multiply(0.1, color);
		Color diffuse = Color.multiply(0.9 * Math.max(0, Vector.dotProduct(lightDir, normal)), color);
		return Color.add(ambient, diffuse);
	}
}
