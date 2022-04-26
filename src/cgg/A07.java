package cgg;

import static cgtools.Matrix.translation;
import static cgtools.Matrix.rotation;
import static cgtools.Matrix.multiply;
import static cgtools.Vector.add;
import static cgtools.Vector.directionTo;
import static cgtools.Vector.direction;
import static cgtools.Vector.point;
import static cgtools.Vector.xAxis;
import static cgtools.Vector.yAxis;
import static cgtools.Vector.zAxis;

import cgtools.Color;
import cgtools.Point;
import cgtools.Matrix;
import cgtools.Random;

public class A07 {
	
	public static void main(String[] args) {
		
		int width = 1500;
		int height = 1000;
		int samples = 10;
		int depth = 10;
		
		Color warmWhite = new Color(1, 1, 0.75);
		Color darkGray = new Color(0.25, 0.25, 0.25);
		Color darkerGray = new Color(0.05, 0.05, 0.05);
		Color black = new Color(0, 0, 0);

		Color darkRed = new Color(0.6, 0.05, 0);
		Color warmYellow = new Color(1, 0.7, 0);
		Color darkBlue = new Color(0, 0, 0.3);
		
		Group adventWreath = new Group(
				new Background(new BackgroundMaterial(black)), // Hintergrund
				new Plane(point(0, 0, 0), yAxis, new DiffusedMaterial(darkerGray)), // Boden
				new Sphere(point(0, 1000, 0), 150, new FlameMaterial(warmWhite)) // Zusätzliches Licht
				);
		
		// Lampe
		adventWreath.addShape(new CappedCylinder(translation(direction(20, 0, 0)), 5, 1, new DiffusedMaterial(black))); // Fuß
		adventWreath.addShape(new CappedCylinder(multiply(translation(direction(20, 0.5, 0)), rotation(zAxis, -45)), 0.75, 15, new MetalMaterial(darkGray))); // Stab unten
		adventWreath.addShape(new CappedCylinder(multiply(translation(direction(30, 10.5, -1)), rotation(xAxis, 90)), 2, 2, new DiffusedMaterial(black))); // Gelenk
		adventWreath.addShape(new CappedCylinder(multiply(translation(direction(30, 10.5, 0)), rotation(zAxis, 45)), 0.75, 18, new MetalMaterial(darkGray))); // Stab oben
		adventWreath.addShape(new CappedCylinder(multiply(translation(direction(15, 20, 0)), rotation(zAxis, -45)), 5, 4, new DiffusedMaterial(black))); // Fassung
		adventWreath.addShape(new Sphere(point(15, 20, 0), 4, new FlameMaterial(warmWhite))); // Glühbirne
		
		// Kranz
		int wreathRadius = 9;
		int wreathElementAmount = 30;
		int wreathElementRadius = 3;
		double wreathColorVariants = 0.01;
		double wreathSizeVariants = 0.4;
		double wreathDepth = 1;		
		for (int i = 0; i < wreathElementAmount; i++) {
			double s = wreathElementRadius + (Random.random() * 0.5 - 1) * wreathSizeVariants;
			double x = wreathRadius * Math.cos(i*(2*Math.PI)/wreathElementAmount);
			double y = s-wreathDepth;
			double z = wreathRadius * Math.sin(i*(2*Math.PI)/wreathElementAmount);
			double r = 0.01 + wreathColorVariants*Random.random();
			double g = 0.15 + 15*wreathColorVariants*Random.random();
			double b = 0.01 + wreathColorVariants*Random.random();
			Material material = new DiffusedMaterial(new Color(r, g, b));
			adventWreath.addShape(new Sphere(point(x, y, z), s, material));
		}
		
		// Kerzen
		Point[] candlePositions = new Point[4];
		candlePositions[0] = point(-3, 0, -3);
		candlePositions[1] = point(3, 0, -3);
		candlePositions[2] = point(-3, 0, 3);
		candlePositions[3] = point(3, 0, 3);
		double candleRadius = 2.5;
		double candleHeight = 7;
		Color candleColor = darkRed;
		double wickRadius = 0.075;
		double wickHeight = 0.75;
		Color wickColor = black;
		for (Point p : candlePositions) {
			adventWreath.addShape(new CappedCylinder(translation(directionTo(p)), candleRadius, candleHeight, new DiffusedMaterial(candleColor)));
			adventWreath.addShape(new CappedCylinder(translation(directionTo(point(p.x, p.y+candleHeight, p.z))), wickRadius, wickHeight, new DiffusedMaterial(wickColor)));
		}
		
		// Flammen
		double flameRadius = 0.1;
		Color flameColor0 = darkBlue;
		Color flameColor1 = warmYellow;
		Point[] flamePositions = new Point[2];
		flamePositions[0] = add(candlePositions[0], direction(0, candleHeight, 0));
		flamePositions[1] = add(candlePositions[3], direction(0, candleHeight, 0));
		for (Point p : flamePositions) {
			for (int i = 1; i < 20; i++) {
				double f = 2.5+(Math.pow(i, -0.2)+(2.0*i)/(i-30))*0.75;
				double r = flameRadius*f;
				double o = 3.5*flameRadius+i*0.15;
				Color c = Color.clamp(Color.add(flameColor0, Color.multiply(Color.divide(flameColor1, 8), i)));
				adventWreath.addShape(new Sphere(point(p.x, p.y+o, p.z), r, new FlameMaterial(c)));
			}
		}
		
		Image image = new Image(width, height);
		Matrix v1 = multiply(translation(0, 2, 0), rotation(0, 1, 0, 25), rotation(1, 0, 0, -30), translation(0, 0, 40));
		Camera c1 = new Camera(Math.PI/4, width, height, v1);
		image.sample(new Raytracer(c1, adventWreath, depth), samples);
		image.write("doc/a07-1.png");
		
		Matrix v2 = multiply(translation(3, 10, 0), rotation(0, 1, 0, -40), rotation(1, 0, 0, -25), translation(0, 0, 75));
		Camera c2 = new Camera(Math.PI/4, width, height, v2);
		image.sample(new Raytracer(c2, adventWreath, depth), samples);
		image.write("doc/a07-2.png");		
	}
}
