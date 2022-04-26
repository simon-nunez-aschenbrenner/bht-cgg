package cgg;

import static cgtools.Vector.*;
import cgtools.Color;

public class A06 {
	
	public static void main(String[] args) {
		
		int width = 1500;
		int height = 1000;
		int samples = 15;
		int depth = 15;
		
		Color lightGray = new Color(0.8, 0.8, 0.8);
		Color lightGreen = new Color(0.2, 1, 0.5);
		Color lightBlue = new Color(0, 0.2, 1);
		Color brightBlue = new Color(0.4, 1, 1);
		Color lightRed = new Color(1, 0.1, 0);
		Color brightOrange = new Color(1, 0.6, 0.3);
		Color yellow = new Color(1, 0.8, 0);
		Color white = new Color(1, 1, 1);
		
		Group group1 = new Group(
				new Background(new BackgroundMaterial(white)),
				new Plane(point(0, -1, 0), direction(0, 1, 0), new DiffusedMaterial(lightGray)),
				new Sphere(point(4, 2, -24), 5, new DiffusedMaterial(lightRed)),
				new Sphere(point(-3, 1.5, -18), 2.5, new DiffusedMaterial(lightBlue)),
				new Sphere(point(0, 0, -8), 0.8, new DiffusedMaterial(yellow)),
				new Sphere(point(1.5, 0.5, -10), 1.5, new GlassMaterial()),
				new Sphere(point(-1.5, -0.75, -8), 0.75, new MetalMaterial(0.25)),
				new Sphere(point(0.3, -0.6, -5), 0.3, new DiffusedMaterial(lightGreen))
				);
		
		Group group2 = new Group(
				new Background(new BackgroundMaterial(white)),
				new Plane(point(0, -1, 0), direction(0, 1, 0), new DiffusedMaterial(lightGray)),
				new Sphere(point(-1, 0, -5), 1, new GlassMaterial(brightBlue)),
				new Sphere(point(1, 0, -5), 1, new MetalMaterial(brightOrange))
				);
		int amount = 20;
		int radius = 5;
		for (int i = 0; i < amount; i++) {
			double x = radius * Math.cos(i*(2*Math.PI)/amount);
			double y = radius * Math.sin(i*(2*Math.PI)/amount)/4+1;
			double z = radius * Math.sin(i*(2*Math.PI)/amount)-8;
			Color hsv = new Color(1.0*i/amount, 1, 1);
			Material material = new DiffusedMaterial(Color.hsvToRgb(hsv));
			group2.addShape(new Sphere(point(x, y, z), 1, material));
		}
				
		Image image = new Image(width, height);
		
		Camera camera1 = new Camera(Math.PI/4, width, height);
		image.sample(new Raytracer(camera1, group1, depth), samples);
		image.write("doc/a06-mirrors-glass-1.png");
		
		Camera camera2 = new Camera(Math.PI/2.75, width, height);
		image.sample(new Raytracer(camera2, group2, depth), samples);
		image.write("doc/a06-mirrors-glass-2.png");
	}
}
