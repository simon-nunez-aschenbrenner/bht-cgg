package cgg;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Sampler;
import cgtools.Util;

public class MetalMaterial implements Material {
	
	public static final int ROUGHNESS_TRIES = 100;
	
	public final Sampler albedo;
	public final double roughness;
	
	public MetalMaterial(Sampler texture, double roughness) {
		this.albedo = texture;
		this.roughness = roughness;
	}
	
	public MetalMaterial(Sampler texture) {
		this(texture, 0);
	}
	
	public MetalMaterial(Color color, double roughness) {
		this(new Constant(color), roughness);
	}
	
	public MetalMaterial(double roughness) {
		this(new Constant(Color.white), roughness);
	}
	
	public MetalMaterial(Color color) {
		this(new Constant(color), 0);
	}
	
	public MetalMaterial() {
		this(new Constant(Color.white), 0);
	}

	@Override
	public Properties properties(Ray ray, Hit hit) {
		int c = 0;
		Direction d = null;
		do {
			d = Utils.reflectRough(ray.d, hit.n, roughness);
			c++;
		} while (d == null && c < ROUGHNESS_TRIES);
		if (d != null) {
			return new Properties(
					new Color(0,0,0), albedo.getColor(hit.u, hit.v),
					new Ray(hit.p, d, Util.EPSILON, Double.POSITIVE_INFINITY));
		} else {
			return new Properties(
					new Color(0,0,0), albedo.getColor(hit.u, hit.v), null);
		}
	}
	
	@Override
	public String toString() {
		return "(Metal " + albedo.toString() + ")";
	}
}
