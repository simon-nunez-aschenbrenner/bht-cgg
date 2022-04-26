package cgg;

import java.io.IOException;

import cgtools.Color;
import cgtools.Sampler;

public class TextureCorrected extends Texture implements Sampler {
	
	public final double gamma;
	
	public TextureCorrected(String filename, double gamma) throws IOException {
		super(filename);
		this.gamma = gamma;
	}

	public TextureCorrected(String filename) throws IOException {
		this(filename, 2.2);
	}

	@Override
	public Color getColor(double u, double v) {
		Color source = super.getColor(u, v);
		double r = Math.pow(source.r, gamma);
		double g = Math.pow(source.g, gamma);
		double b = Math.pow(source.b, gamma);
		return new Color(r, g, b);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
