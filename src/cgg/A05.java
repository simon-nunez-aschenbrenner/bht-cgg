package cgg;

import static cgtools.Vector.*;
import cgtools.Color;

public class A05 {
	
	public static void main(String[] args) {
		
		double angle = Math.PI/4;
		int width = 1500;
		int height = 1000;
		int samples = 20;
		int depth = 20;
		
		Image image = new Image(width, height);
		Camera camera = new Camera(angle, width, height);
		
		Color lightGray = new Color(0.8, 0.8, 0.8);
		Color lightGreen = new Color(0.2, 1, 0.5);
		Color lightBlue = new Color(0, 0.2, 1);
		Color lightRed = new Color(1, 0.1, 0);
		Color yellow = new Color(1, 0.8, 0);
		Color purple = new Color(0.5, 0.1, 1);
		Color white = new Color(1, 1, 1);
		
		Group group = new Group(
				new Background(new BackgroundMaterial(white)),
				new Plane(point(0, -1, 0), direction(0, 1, 0), new DiffusedMaterial(lightGray)),
				new Sphere(point(4, 2, -24), 5, new DiffusedMaterial(lightRed)),
				new Sphere(point(-3, 1.5, -18), 2.5, new DiffusedMaterial(lightBlue)),
				new Sphere(point(0, 0, -8), 0.8, new DiffusedMaterial(yellow)),
				new Sphere(point(1.5, 0.5, -10), 1.5, new DiffusedMaterial(purple)),
				new Sphere(point(-1.5, -0.75, -8), 0.75, new DiffusedMaterial(white)),
				new Sphere(point(0.3, -0.6, -5), 0.3, new DiffusedMaterial(lightGreen))
				);
		
		image.sample(new Raytracer(camera, group, depth), samples);
		image.write("doc/a05-diffuse-spheres.png");
	}
}
