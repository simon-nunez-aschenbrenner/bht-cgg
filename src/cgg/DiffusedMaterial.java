package cgg;

import static cgtools.Vector.*;
import cgtools.Color;
import cgtools.Sampler;
import cgtools.Util;

public class DiffusedMaterial implements Material {
	
	public final Sampler albedo;
	
	public DiffusedMaterial(Sampler texture) {
		this.albedo = texture;
	}
	
	public DiffusedMaterial(Color color) {
		this(new Constant(color));
	}
	
	public DiffusedMaterial() {
		this(new Constant(Color.white));
	}

	@Override
	public Properties properties(Ray ray, Hit hit) {
		return new Properties(
				new Color(0,0,0), albedo.getColor(hit.u, hit.v),
				new Ray(hit.p, normalize(add(hit.n, Utils.randomDir())), Util.EPSILON, Double.POSITIVE_INFINITY));
	}
	
	@Override
	public String toString() {
		return "(Diffused " + albedo.toString() + ")";
	}
}
