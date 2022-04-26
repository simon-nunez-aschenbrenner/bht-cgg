package cgg;

import static cgtools.Color.add;
import static cgtools.Color.multiply;

import cgtools.Color;
import cgtools.Sampler;

public class Raytracer implements Sampler {
	
	public final Camera camera;
	public final World world;
	public final int depth;
	
	public Raytracer(Camera camera, World world, int depth) {
		this.camera = camera;
		this.world = world;
		this.depth = depth;
	}

	public Raytracer(Camera camera, Group scene, int depth) {
		this(camera, new World(scene), depth);
	}
	
	public Raytracer(Camera camera, Group scene) {
		this(camera, new World(scene), 10);
	}
	
	public Color getColor(double x, double y) {
		Ray ray = camera.generateRay(x, y);
		return calculateRadiance(ray, world, depth);
	}
	
	private Color calculateRadiance(Ray ray, World world, int depth) {
		if (depth == 0) return new Color(0,0,0);
		
		Hit hit = world.scene.intersect(ray);
		Properties properties = hit.m.properties(ray, hit);
		
		if (properties.scatteredRay != null) {
			
			Color radiance = calculateRadiance(properties.scatteredRay, world, --depth);
			
			if(hit.m instanceof DiffusedMaterial) {
				for (Light l : world.lights) {
					Color luminance = l.incomingIntensity(hit.p, hit.n, world.scene);
					if (luminance != null) radiance = add(radiance, luminance);
				}
			}
			return add(properties.emission, multiply(properties.albedo, radiance));
			
		} else {
			return properties.emission;
		}
	}
}
