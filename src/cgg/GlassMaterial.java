package cgg;

import static cgtools.Vector.dotProduct;
import static cgtools.Vector.negate;
import static cgtools.Vector.normalize;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Random;
import cgtools.Sampler;
import cgtools.Util;

public class GlassMaterial implements Material {
	
	public final Sampler albedo;
	public final double outRefracIndex;
	public final double inRefracIndex;
	
	public GlassMaterial(Sampler texture, double outRefracIndex, double inRefracIndex) {
		this.albedo = texture;
		this.outRefracIndex = outRefracIndex;
		this.inRefracIndex = inRefracIndex;
	}
	
	public GlassMaterial(Sampler texture) {
		this(texture, 1.0, 1.5);
	}
	
	public GlassMaterial(Color color) {
		this(new Constant(color), 1.0, 1.5);
	}
	
	public GlassMaterial() {
		this(new Constant(Color.white), 1.0, 1.5);
	}

	@Override
	public Properties properties(Ray ray, Hit hit) {
		double n1, n2;
		Direction n = normalize(hit.n);
		Direction d = normalize(ray.d);
		if (dotProduct(n, d) > 0) {
			n = negate(n);
			n1 = inRefracIndex;
			n2 = outRefracIndex;
		} else {
			n1 = outRefracIndex;
			n2 = inRefracIndex;
		}
		Direction dt = Utils.refract(d, n, n1, n2);
		if (dt != null && (Random.random() > Utils.schlick(d, n, n1, n2))) { // refract
			return new Properties(
					new Color(0,0,0), albedo.getColor(hit.u, hit.v),
					new Ray(hit.p, dt, Util.EPSILON, Double.POSITIVE_INFINITY));
		} else { // reflect
			return new Properties(
					new Color(0,0,0), albedo.getColor(hit.u, hit.v),
					new Ray(hit.p, Utils.reflect(d, n), Util.EPSILON, Double.POSITIVE_INFINITY));
		}
	}
	
	@Override
	public String toString() {
		return "(Glass " + albedo.toString() + ")";
	}
}
