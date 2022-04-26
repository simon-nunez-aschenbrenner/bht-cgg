package cgg;

import static cgtools.Vector.normalize;
import static cgtools.Color.divide;

import cgtools.Color;
import cgtools.Sampler;
import cgtools.Util;

public class FlameMaterial implements Material {

	public final Sampler emission;
	public final int transparency;
	
	public FlameMaterial(Sampler texture, int transparency) {
		this.emission = texture;
		this.transparency = transparency;
	}
	
	public FlameMaterial(Sampler texture) {
		this(texture, 0);
	}
	
	public FlameMaterial(Color color, int transparency) {
		this(new Constant(color), transparency);
	}
	
	public FlameMaterial(Color color) {
		this(new Constant(color), 0);
	}

	@Override
	public Properties properties(Ray ray, Hit hit) {
		return new Properties(
				divide(emission.getColor(hit.u, hit.v), transparency), new Color(1,1,1),
				new Ray(hit.p, normalize(ray.d), Util.EPSILON, Double.POSITIVE_INFINITY));
	}
	
	@Override
	public String toString() {
		return "(Flame " + emission.toString() + ")";
	}
}
