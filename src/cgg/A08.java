package cgg;

import static cgtools.Matrix.multiply;
import static cgtools.Matrix.rotation;
import static cgtools.Matrix.translation;
import static cgtools.Vector.point;
import static cgtools.Vector.yAxis;
import static cgtools.Vector.zero;
import static cgtools.Random.random;

import cgtools.Color;
import cgtools.Matrix;

public class A08 {

	public static void main(String[] args) {
		
		int width = 1500;
		int height = 1000;
		int samples = 20;
		int depth = 20;
		
		Color white = new Color(1, 1, 1);
		Color warmWhite = new Color(1, 1, 0.75);
		Color black = new Color(0, 0, 0);
		Color darkRed = new Color(0.6, 0.05, 0);
		Color warmYellow = new Color(1, 0.7, 0);
		Color brown = new Color(0.1, 0.05, 0);
		
		Group scene = new Group(
				new Background(new BackgroundMaterial(black)), // Hintergrund
				new Plane(zero, yAxis, new DiffusedMaterial(white)), // Boden
				new Sphere(point(0, 1000, 0), 300, new BackgroundMaterial(warmWhite)) // Zusätzliches Licht
				);
		
		Group bonfire = new Group();
		
		// Holzscheite
		for (int i = 0; i < 10; i++) {
			
			double s = 0.3 * random() + 0.5; // Radius
			double l = 3.0 * random() + 5.0; // Länge
			double a; do a = (random() - 0.5) * 180; while (Math.abs(a) < 30); // Rotationsparameter Aufstellwinkel
			double r = random() * 360; // Rotationsparameter Position
			double t = l/1.5*Math.signum(a); // Translationsparameter
			Matrix m = multiply(rotation(0, 1, 0, r), translation(t, s, 0), rotation(0, 0, 1, a));
			
			bonfire.addShape(new CappedCylinder(m, s, l, new DiffusedMaterial(Color.multiply(brown, random()+0.5))));
		}
		
		// Flammen
		for (int i = 0; i < 50; i++) {	
			
			double x = (random() - 0.5) * 6;
			double y = 3 * random() + 0.5;
			double z = (random() - 0.5) * 6;
			double u = (random() - 0.5) * 40; // Rotation um x-Achse
			double v = (random() - 0.5) * 40; // Rotation um y-Achse
			
			Group flame = new Group(multiply(translation(x, y, z), rotation(1, 0, 0, u), rotation(0, 0, 1, v)));
			
			double w = 0.1 * random() + 0.2; // Radius
			double l = 20 * random() + 10; // Höhe
			
			// Flammensegmente
			for (int j = 1; j < (int) l; j++) {	
				
				double f = 1.5+(Math.pow(j, -0.2)+(2.0*j)/(j-50))*0.75;
				double o = 3.5*w+j*0.2;
				double h = l/3 + random() + l/3;
				Color c = Color.clamp(Color.add(darkRed, Color.multiply(Color.divide(warmYellow, h), j)));
				
				flame.addShape(new Sphere(point(0, o, 0), w*f, new FlameMaterial(c, (int) (l-(1.0*j/1.5)))));
			}
			bonfire.addShape(flame);
		}
		
		// Blobs
		double size = 9;
		for (int i = -2; i < 3; i++) {
			
			Group blob = new Group(multiply(rotation(0, 1, 0, i*72), translation(0, 0, -12), rotation(1, 0, 0, 12)));
			Group torso = new Group(translation(0, size/2.65, 0), new Sphere(size/2.65, new DiffusedMaterial(white)));
			Group head = new Group(translation(0, size/1.2, 0), new Sphere(size/5.5, new DiffusedMaterial(white)));
			Group eyes = new Group(translation(0, size/(5.5*10), size/5.5 - size/40),
					new Sphere(point(-size/14, 0, 0), size/40, new DiffusedMaterial(black)),
					new Sphere(point( size/14, 0, 0), size/40, new DiffusedMaterial(black)));
			
			blob.addShape(torso);
			blob.addShape(head);
			head.addShape(eyes);
			bonfire.addShape(blob);
		}
		
		scene.addShape(bonfire);
		Image image = new Image(width, height);
		
		Matrix v1 = multiply(rotation(1, 0, 0, -10), rotation(0, 1, 0, 15), translation(0, 3, 45));
		Camera c1 = new Camera(Math.PI/4, width, height, v1);
		image.sample(new Raytracer(c1, scene, depth), samples);
		image.write("doc/a08-1.png");
		
		Matrix v2 = multiply(rotation(1, 0, 0, -90), translation(0, 0, 40));
		Camera c2 = new Camera(Math.PI/2, width, height, v2);
		image.sample(new Raytracer(c2, scene, depth), samples);
		image.write("doc/a08-2.png");
	}
}
