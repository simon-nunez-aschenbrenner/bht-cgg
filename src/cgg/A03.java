package cgg;

import static cgtools.Vector.*;
import cgtools.Color;
import cgtools.Random;

public class A03 {
	
	public static void main(String[] args) {
		
		// Tests.test();
		
		String filename = "doc/a03-three-spheres.png";

		int width = 1500;
		int height = 1000;
		int samples = 10;
		
		double angle = Math.PI/4;
		Color background = Color.black;
		
		Image image = new Image(width, height);
		Camera camera = new Camera(angle, width, height);
		RaytraceSpheres spheres = new RaytraceSpheres(camera, background);
		
		spheres.add(new Sphere(point( 0, 0, -10), 2, Color.red));
		spheres.add(new Sphere(point(-2, 0, -13), 2, Color.green));
		spheres.add(new Sphere(point( 2, 0, -13), 2, Color.blue));
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				
				Color color = new Color(0, 0, 0);
				
				for(int xi = 0; xi != samples; xi++) {
					for(int yi = 0; yi != samples; yi++) {
						
						double rx = Random.random();
						double ry = Random.random();
						double xs = x + (xi+rx)/samples;
						double ys = y + (yi+ry)/samples;
						color = Color.add(color, spheres.getColor(xs, ys));
					}
				}	
				image.setPixel(x, y, Color.divide(color, samples * samples));
			}
		}
		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}
}
