package cgg;

import static cgtools.Vector.*;

import cgtools.Color;

public class A04 {
	
	public static void main(String[] args) {
		
		int width = 1500;
		int height = 1000;
		int samples = 10;
		
		Image image = new Image(width, height);
		Camera camera = null;
		Group group = null;
		
		// Spheres
		
		camera = new Camera(Math.PI/2, width, height);
		
		group = new Group(
				new Background(Color.gray),
				new Plane(point(0.0, -0.5, 0.0), direction(0, 1, 0), Color.white),
				new Sphere(point(-1.0, -0.25, -2.5), 0.7, Color.red),
				new Sphere(point(0.0, -0.25, -2.5), 0.5, Color.white),
				new Sphere(point(1.0, -0.25, -2.5), 0.7, Color.red)
				);
		
		image.sample(new Raytracer(camera, group), samples);
		image.write("doc/a04-3-spheres.png");
		
		// Scene
		
		camera = new Camera(Math.PI/3, width, height);

		double[][] ufoPos = {
				{-2.5, 2.25, -10}, // Ufo 0
				{0, 2.5, -30}, // Ufo 1
				{10, 7.5, -50}, // Ufo 2
				{27.5, 25, -100}, {40, 20, -100}, // Ufos 3-4
				{60, 39, -200}, {80, 48, -200}, {100, 41, -200} // Ufos 5-7
				};
		
		Color[] ufoColor = {
				new Color(1, 0.0, 1), // Lower Plane
				new Color(0.7, 1, 0.7), // Upper Plane
				new Color(0.2, 0.9, 0.2) // Sphere
				};
		
		double[][] blobPos = {
				{0.2, -0.3, -1.2}, // Blob 0
				{-2.5, 1, -10}, // Blob 1
				{-2, -1.99, -10}, // Blob 2
				{-1.75, -1.92, -10.25}, // Blob 3
				{-3, -2.3, -9} // Blob 4
				};
		
		Color[] blobColor = {
				Color.white, // Body
				Color.black // Face
				};
		
		group = new Group(
				
				// Ufo 0
				new Plane(point(ufoPos[0][0], ufoPos[0][1], ufoPos[0][2]), direction(0, -1, 0), 3.5, ufoColor[0]),
				new Plane(point(ufoPos[0][0], ufoPos[0][1] + 0.05, ufoPos[0][2]), direction(0, 1, 0), 3.5, ufoColor[1]),
				new Sphere(point(ufoPos[0][0], ufoPos[0][1] + 0.5, ufoPos[0][2]), 1, ufoColor[2]),
				// Ufo 1
				new Plane(point(ufoPos[1][0], ufoPos[1][1], ufoPos[1][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.6)),
				new Plane(point(ufoPos[1][0], ufoPos[1][1] + 0.05, ufoPos[1][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.6)),
				new Sphere(point(ufoPos[1][0], ufoPos[1][1] + 0.5, ufoPos[1][2]), 1, Color.multiply(ufoColor[2], 0.6)),
				// Ufo 2
				new Plane(point(ufoPos[2][0], ufoPos[2][1], ufoPos[2][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.3)),
				new Plane(point(ufoPos[2][0], ufoPos[2][1] + 0.05, ufoPos[2][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.3)),
				new Sphere(point(ufoPos[2][0], ufoPos[2][1] + 0.5, ufoPos[2][2]), 1, Color.multiply(ufoColor[2], 0.3)),
				// Ufo 3
				new Plane(point(ufoPos[3][0], ufoPos[3][1], ufoPos[3][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.15)),
				new Plane(point(ufoPos[3][0], ufoPos[3][1] + 0.05, ufoPos[3][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.15)),
				new Sphere(point(ufoPos[3][0], ufoPos[3][1] + 0.5, ufoPos[3][2]), 1, Color.multiply(ufoColor[2], 0.15)),
				// Ufo 4
				new Plane(point(ufoPos[4][0], ufoPos[4][1], ufoPos[4][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.15)),
				new Plane(point(ufoPos[4][0], ufoPos[4][1] + 0.05, ufoPos[4][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.15)),
				new Sphere(point(ufoPos[4][0], ufoPos[4][1] + 0.5, ufoPos[4][2]), 1, Color.multiply(ufoColor[2], 0.15)),
				// Ufo 5
				new Plane(point(ufoPos[5][0], ufoPos[5][1], ufoPos[5][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.06)),
				new Plane(point(ufoPos[5][0], ufoPos[5][1] + 0.05, ufoPos[5][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.06)),
				new Sphere(point(ufoPos[5][0], ufoPos[5][1] + 0.5, ufoPos[5][2]), 1, Color.multiply(ufoColor[2], 0.06)),
				// Ufo 6
				new Plane(point(ufoPos[6][0], ufoPos[6][1], ufoPos[6][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.06)),
				new Plane(point(ufoPos[6][0], ufoPos[6][1] + 0.05, ufoPos[6][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.06)),
				new Sphere(point(ufoPos[6][0], ufoPos[6][1] + 0.5, ufoPos[6][2]), 1, Color.multiply(ufoColor[2], 0.0625)),
				// Ufo 7
				new Plane(point(ufoPos[7][0], ufoPos[7][1], ufoPos[7][2]), direction(0, -1, 0), 3.5, Color.multiply(ufoColor[0], 0.06)),
				new Plane(point(ufoPos[7][0], ufoPos[7][1] + 0.05, ufoPos[7][2]), direction(0, 1, 0), 3.5, Color.multiply(ufoColor[1], 0.06)),
				new Sphere(point(ufoPos[7][0], ufoPos[7][1] + 0.5, ufoPos[7][2]), 1, Color.multiply(ufoColor[2], 0.06)),
				
				// Blob 0
				new Sphere(point(blobPos[0][0], blobPos[0][1]-0.01, blobPos[0][2]), 0.1, blobColor[0]), // Torso
				new Sphere(point(blobPos[0][0]+0.02, blobPos[0][1]+0.08, blobPos[0][2]-0.01), 0.06, blobColor[0]), // Head
				new Sphere(point(blobPos[0][0]+0.04, blobPos[0][1]+0.11, blobPos[0][2]+0.032), 0.01, blobColor[1]), // Right Eye
				new Sphere(point(blobPos[0][0]-0.005, blobPos[0][1]+0.12, blobPos[0][2]+0.02), 0.01, blobColor[1]), // Left Eye
				new Sphere(point(blobPos[0][0]+0.009, blobPos[0][1]+0.075, blobPos[0][2]+0.025), 0.0375, blobColor[1]), // Mouth
				// Blob 1
				new Sphere(point(blobPos[1][0], blobPos[1][1], blobPos[1][2]), 0.1, blobColor[0]), // Torso
				new Sphere(point(blobPos[1][0]-0.01, blobPos[1][1]+0.07, blobPos[1][2]+0.05), 0.06, blobColor[0]), // Head
				// Blob 2
				new Sphere(point(blobPos[2][0], blobPos[2][1], blobPos[2][2]), 0.1, blobColor[0]), // Torso
				new Sphere(point(blobPos[2][0]-0.01, blobPos[2][1]+0.07, blobPos[2][2]+0.05), 0.06, blobColor[0]), // Head
				// Blob 3
				new Sphere(point(blobPos[3][0], blobPos[3][1], blobPos[3][2]), 0.1, blobColor[0]), // Torso
				new Sphere(point(blobPos[3][0]+0.02, blobPos[3][1]+0.07, blobPos[3][2]), 0.06, blobColor[0]), // Head
				new Sphere(point(blobPos[3][0]+0.04, blobPos[3][1]+0.11, blobPos[3][2]+0.032), 0.01, blobColor[1]), // Right Eye
				new Sphere(point(blobPos[3][0]-0.005, blobPos[3][1]+0.12, blobPos[3][2]+0.02), 0.01, blobColor[1]), // Left Eye
				new Sphere(point(blobPos[3][0]+0.009, blobPos[3][1]+0.075, blobPos[3][2]+0.025), 0.0375, blobColor[1]), // Mouth
				// Blob 4
				new Sphere(point(blobPos[4][0], blobPos[4][1], blobPos[4][2]), 0.1, blobColor[0]), // Torso
				new Sphere(point(blobPos[4][0], blobPos[4][1]+0.09, blobPos[4][2]), 0.06, blobColor[0]), // Head
				new Sphere(point(blobPos[4][0]+0.025, blobPos[4][1]+0.12, blobPos[4][2]+0.04), 0.01, blobColor[1]), // Right Eye
				new Sphere(point(blobPos[4][0]-0.025, blobPos[4][1]+0.12, blobPos[4][2]+0.04), 0.01, blobColor[1]), // Left Eye
				new Sphere(point(blobPos[4][0], blobPos[4][1]+0.09, blobPos[4][2]+0.025), 0.0375, blobColor[1]), // Mouth
				
				// Sea
				new Plane(point(0, -3, 0), direction(0, 1, 0), 50, new Color(0, 0.05, 0.5)),
				// Beach
				new Sphere(point(7, -51, -15), 50, new Color(0.35, 0.3, 0.01)),
				// Grass
				new Sphere(point(20, -46.02, 4), 50, new Color(0.08, 0.28, 0)),
				
				new Background(new Color(0, 0.01, 0.1))
				);
		
		image.sample(new Raytracer(camera, group), samples);
		image.write("doc/a04-scene.png");
	}
}