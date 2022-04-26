package cgg;

import cgtools.Color;
import cgtools.Sampler;

public class BackgroundMaterial implements Material {
	
	public final Sampler emission;
	
	public BackgroundMaterial(Sampler texture) {
		this.emission = texture;
	}

	
	public BackgroundMaterial(Color color) {
		this(new Constant(color));
	}

	@Override
	public Properties properties(Ray ray, Hit hit) {
		return new Properties(emission.getColor(hit.u, hit.v), new Color(0,0,0), null);
	}
	
	@Override
	public String toString() {
		return "(Background " + emission.toString() + ")";
	}
}
