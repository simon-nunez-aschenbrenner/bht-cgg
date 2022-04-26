package cgg;

import static cgtools.Matrix.multiply;
import static cgtools.Matrix.rotation;
import static cgtools.Matrix.scaling;
import static cgtools.Matrix.translation;
import static cgtools.Vector.direction;
import static cgtools.Vector.point;

import java.io.IOException;

import cgtools.Color;
import cgtools.Matrix;
import cgtools.Sampler;

public class A11 {

	public static void main(String[] args) {
		
		int width = 1500;
		int height = 1000;
		int samples = 10;
		int depth = samples;
		
		Image i = new Image(width, height);

		Matrix v1 = multiply(translation(0, 0.25, 0), rotation(1, 0, 0, -15), translation(0, 0, 5));
		Camera c1 = new Camera(Math.PI/2, width, height, v1);
		
		Matrix v2 = multiply(rotation(0, 1, 0, 60), translation(0, -1, 0), rotation(1, 0, 0, 45), translation(0, 0, 5));
		Camera c2 = new Camera(Math.PI/3, width, height, v2);
		
		Matrix l = multiply(rotation(0, 1, 0, -144), rotation(1, 0, 0, 43));
		Light l1 = new DirectionalLight(multiply(l, direction(0, 0, 1)), Color.white);
		Light l2 = new PointLight(point(0, -1, 0), Color.multiply(new Color(0.6, 0.7, 1), 2.5));

		Sampler sky, beach, snow, rock;
		
		try {
			sky = new TextureCorrected("./tex/sky.png");
			beach = new TextureTransform(new TextureCorrected("./tex/beach.png"), scaling(1, 1, 0));
			snow = new TextureTransform(new TextureCorrected("./tex/snow.png", 1.5), scaling(2, 2, 0));
			rock = new TextureCorrected("./tex/rock.png");

			Group g = new Group(new Background(new BackgroundMaterial(sky)));
			
			double size = 1;
			Group torso = new Group(translation(0, size/2.65, 0), new Sphere(size/2.65, new DiffusedMaterial(snow)));
			Group head = new Group(translation(0, size/1.2, 0), new Sphere(size/5.5, new DiffusedMaterial(snow)));
			Group eyes = new Group(translation(0, size/(5.5*10), size/5.5 - size/40),
					new Sphere(point(-size/14, 0, 0), size/40, new DiffusedMaterial(rock)),
					new Sphere(point(size/14, 0, 0), size/40, new DiffusedMaterial(rock)));
			head.addShape(eyes);
			Group blob = new Group(torso, head);
			
			g.addShape(new Group(multiply(translation(-3, 0.5, 0), rotation(0, 1, 0, 60), rotation(1, 0, 0, 30)), blob));
			g.addShape(new Group(multiply(translation(3, 0.5, 0), rotation(0, 1, 0, -80)), blob));
			g.addShape(new Group(translation(0, -1, 0), new Hyperboloid(new DiffusedMaterial(beach), 1.25, 3, -2, 0.25)));
			g.addShape(new Cuboid(new MetalMaterial(0.5), point(0, -2.5, 0), direction(0.4, 1, 0.4)));
			
			g.initBVH();
						
			World w = new World(g, l1, l2);
			
			i.sample(new Raytracer(c1, w, depth), samples);
			i.write("doc/a11-1.png");
			
			i.sample(new Raytracer(c2, w, depth), samples);
			i.write("doc/a11-2.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
