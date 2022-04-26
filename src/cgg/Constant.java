package cgg;

import cgtools.Color;
import cgtools.Sampler;

public class Constant implements Sampler {
	
	public final Color color;
	
	public Constant(Color color) {
		this.color = color;
	}

	@Override
	public Color getColor(double u, double v) {
		return color;
	}
	
	@Override
	public String toString() {
		return color.toString();
	}
	
}
