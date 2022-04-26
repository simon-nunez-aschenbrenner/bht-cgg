package cgg;

import static cgtools.Matrix.multiply;
import static cgtools.Matrix.scaling;
import static cgtools.Matrix.translation;
import static cgtools.Random.random;
import static cgtools.Vector.direction;
import static cgtools.Vector.point;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cgtools.Color;
import cgtools.Matrix;
import cgtools.Vector;

public class A09 {
	
	public static void main(String[] args) {
		
		int width = 750;
		int height = 750;
		int samples = 5;
		int depth = 5;

		Group scene = new Group(new Background(new BackgroundMaterial(Color.white)));
		addGroups(scene, 0);
		
		Group flat = new Group();
		scene.flattenBVH(flat, Matrix.identity);

		Matrix v = translation(0, 0, 5);
		Camera c = new Camera(Math.PI/4, width, height, v);
		
		PrintWriter txt = null;
		try { txt = new PrintWriter(new FileWriter("./result.txt")); }
		catch (IOException e) { e.printStackTrace(); }
		
		if (txt != null) {
			int cores = Runtime.getRuntime().availableProcessors();
			String out = "";
			
			for (int i = cores*2; i > 0; i--) {
				Image image = new Image(width, height, i);
				
				out = String.format("%2d Threads  mit BVH: ", i);
				System.out.print(out);
				txt.print(out);
				
				long startTime = System.currentTimeMillis();
				image.sample(new Raytracer(c, scene, depth), samples);
				image.write(String.format("doc/a09-%d-bvh.png", i));
				
				out = (System.currentTimeMillis() - startTime)/1000.0 + " Sekunden";
				System.out.println(out);
				txt.println(out);
				
				out = String.format("%2d Threads ohne BVH: ", i);
				System.out.print(out);
				txt.print(out);
				
				startTime = System.currentTimeMillis();
				image.sample(new Raytracer(c, flat, depth), samples);
				image.write(String.format("doc/a09-%d-flat.png", i));
				
				out = (System.currentTimeMillis() - startTime)/1000.0 + " Sekunden";
				System.out.println(out);
				txt.println(out);
				
				System.out.println();
				txt.println();
				txt.flush();
			}
			txt.close();
		}
	}
	
	public static void addGroups(Group group, int iteration) {
		if (iteration > 3) {
			group.addShape(new Sphere(point(-random(), -random(), 0), 0.5, new Color(random(), random(), random())));
			group.addShape(new Sphere(point(random(), -random(), 0), 0.5, new Color(random(), random(), random())));
			group.addShape(new Sphere(point(-random(), random(), 0), 0.5, new Color(random(), random(), random())));
			group.addShape(new Sphere(point(random(), random(), 0), 0.5, new Color(random(), random(), random())));
		
		} else {
			Vector factor = direction(0.5, 0.5, 0.5);
			iteration++;
			
			Group upLeft = new Group(multiply(translation(-1, -1, 0), scaling(factor)));
			group.addShape(upLeft);
			addGroups(upLeft, iteration);

			Group upRight = new Group(multiply(translation(1, -1, 0), scaling(factor)));
			group.addShape(upRight);
			addGroups(upRight, iteration);

			Group downLeft = new Group(multiply(translation(-1, 1, 0), scaling(factor)));
			group.addShape(downLeft);
			addGroups(downLeft, iteration);

			Group downRight = new Group(multiply(translation(1, 1, 0), scaling(factor)));
			group.addShape(downRight);
			addGroups(downRight, iteration);
		}
	}
}
