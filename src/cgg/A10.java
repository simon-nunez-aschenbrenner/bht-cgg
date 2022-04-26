package cgg;

import static cgtools.Matrix.*;
import static cgtools.Vector.*;

import java.io.IOException;

import cgtools.Matrix;
import cgtools.Sampler;

/**
 * Image Texture Credits:
 * Sky (Background) by Sergej Majboroda, used under CCO license, obtained from hdrihaven.com
 * Beach, Snow, Rock (Ground, Blobs) by Rob Tuytel, used under CCO license, obtained from texturehaven.com
 * World, Clouds (Hats) by Reto Stöckli, NASA Earth Observatory, used according to the Visible Earth Image Use Policy (https://visibleearth.nasa.gov/image-use-policy), obtained from visibleearth.nasa.gov
 */

public class A10 {

	public static void main(String[] args) {
		
		int width = 1500;
		int height = 1000;
		int samples = 20;
		int depth = 20;
		
		Image i = new Image(width, height);
		
		Matrix v1 = multiply(translation(0.5, 4, 0), rotation(0, 1, 0, -90));
		Camera c1 = new Camera(Math.PI/2, width, height, v1);
		
		Matrix v2 = multiply(translation(0, 8, 0), rotation(1, 0, 0, -15), translation(0, 0, 30));
		Camera c2 = new Camera(Math.PI/2, width, height, v2);

		Sampler sky, beach, snow, clouds, rock, polkaDots, checkerBoard, world;
		
		try {
			sky = new TextureCorrected("./tex/sky.png");
			beach = new TextureTransform(new TextureCorrected("./tex/beach.png"), scaling(250, 250, 0));
			snow = new TextureTransform(new TextureCorrected("./tex/snow.png"), scaling(2, 2, 0));
			clouds = new TextureCorrected("./tex/clouds.png");
			rock = new TextureCorrected("./tex/rock.png");
			polkaDots = new TextureTransform(new Dot(new TextureTransform(clouds, multiply(scaling(0.02, 0.04, 0), translation(10, 10, 0))), snow, 0.75), scaling(15, 7.5, 0));
			checkerBoard = new TextureTransform(new CheckerBoard(snow, clouds, 10), scaling(2, 1, 0));
			world = new TextureCorrected("./tex/world.png");

			Group scene = new Group();
			scene.addShape(new Group(multiply(translation(0, 10, 0), rotation(-1, 1, 0, -30), scaling(5, 5, 5)), new Sphere(new BackgroundMaterial(world))));
			scene.addShape(new Group(scaling(5000, 1, 5000), new Plane(new DiffusedMaterial(beach))));
			scene.addShape(new Background(new BackgroundMaterial(sky)));
			
			double size = 9;
			Group torso = new Group(translation(0, size/2.65, 0), new Sphere(size/2.65, new DiffusedMaterial(snow)));
			Group head = new Group(translation(0, size/1.2, 0), new Sphere(size/5.5, new DiffusedMaterial(snow)));
			Group eyes = new Group(translation(0, size/(5.5*10), size/5.5 - size/40),
					new Sphere(point(-size/14, 0, 0), size/40, new DiffusedMaterial(rock)),
					new Sphere(point(size/14, 0, 0), size/40, new DiffusedMaterial(rock)));
			head.addShape(eyes);
			Group blob = new Group(torso, head);
			Group hat1 = new Group(multiply(translation(0, size/1.2+size/5.5, -size/5.5), scaling(2.5, 2.5, 2.5)), new Sphere(new BackgroundMaterial(polkaDots)));
			Group hat2 = new Group(multiply(translation(0, size/1.2+size/5.5, -size/5.5), scaling(2.5, 2.5, 2.5)), new Sphere(new BackgroundMaterial(checkerBoard)));
			
			scene.addShape(new Group(multiply(translation(-15, -0.5, 0), rotation(0, 1, 0, 90)), blob, hat1));
			scene.addShape(new Group(multiply(translation(15, -0.5, 0), rotation(0, 1, 0, -90)), blob, hat2));
			
			scene.initBVH();

			i.sample(new Raytracer(c1, scene, depth), samples);
			i.write("doc/a10-1.png");
			
			i.sample(new Raytracer(c2, scene, depth), samples);
			i.write("doc/a10-2.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
