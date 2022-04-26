package cgg;

import static cgtools.Vector.*;
import cgtools.Direction;
import cgtools.Point;

public final class Tests {
	
	public static void test() {
		
		// Aufgabe 3.2
		Camera camera = new Camera(Math.PI/2, 10, 10);
		double[][][] rayTests = {
			{ { 0,  0}, {-1.0/Math.sqrt(3), 1.0/Math.sqrt(3), -1.0/Math.sqrt(3)} },
			{ { 5,  5}, {0, 0, -1} },
			{ {10, 10}, {1.0/Math.sqrt(3), -1.0/Math.sqrt(3), -1.0/Math.sqrt(3)} }
		};
		for (double[][] test : rayTests) {
			System.out.println(testRay(camera, test[0], test[1]));
		}
		
		// Aufgabe 3.3
		System.out.println(testHit
				(point(0,0,-2), 1, zero, direction(0,0,-1), 0, Double.MAX_VALUE));
		System.out.println(testHit
				(point(0,0,-2), 1, zero, direction(0,1,-1), 0, Double.MAX_VALUE));
		System.out.println(testHit
				(point(0,-1,-2), 1, zero, direction(0,0,-1), 0, Double.MAX_VALUE));
		System.out.println(testHit
				(point(0,0,-2), 1, point(0,0,-4), direction(0,0,-1), 0, Double.MAX_VALUE));
		System.out.println(testHit
				(point(0,0,-4), 1, zero, direction(0,0,-1), 0, 2));
	}
		
	private static String testRay(Camera camera, double[] pos, double[] res) {
		
		boolean bool;
		Ray ray = camera.generateRay(pos[0], pos[1]);
		Direction d = ray.d;
		Point x0 = ray.x0;
		
		bool = (int)(d.x * 100) == (int)(res[0] * 100) &&
			   (int)(d.y * 100) == (int)(res[1] * 100) &&
			   (int)(d.z * 100) == (int)(res[2] * 100) &&
			   x0.equals(zero);
		
		if (bool) {
			return String.format("x_0(R(%.0f, %.0f)) = (0, 0, 0)%n"
					+ "  d(R(%.0f, %.0f)) = (%.2f, %.2f, %.2f)%n",
					pos[0], pos[1], pos[0], pos[1], res[0], res[1], res[2]);
		} else {
			return String.format("Test für R(%.0f, %.0f) liefert falsche Ergebnisse%n",
					pos[0], pos[1]);
		}
		
	}
	
	private static String testHit(Point center, double radius, Point x0, Direction d,
			double t_min, double t_max) {
		
		String result;
		Sphere sphere = new Sphere(center, radius);
		Ray ray = new Ray(x0, d, t_min, t_max);
		Hit hit = sphere.intersect(ray);
		
		if (hit != null) {
			result = String.format("(%2.0f, %2.0f, %2.0f) (%2.0f, %2.0f, %2.0f)",
					hit.p.x, hit.p.y, hit.p.z,
					hit.n.x, hit.n.y, hit.n.z);
		} else {
			result = "–";
		}
		return result;
	}
}
