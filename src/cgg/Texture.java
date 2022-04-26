package cgg;

import java.io.IOException;

import cgtools.Color;
import cgtools.ImageTexture;
import cgtools.Sampler;

public class Texture implements Sampler {
	
	public final ImageTexture texture;
	private String filename;
	
	public Texture(String filename) throws IOException {
		texture = new ImageTexture(filename);
		this.filename = filename;
	}

	@Override
	public Color getColor(double u, double v) {
		return texture.getColor(u, v);
	}
	
	@Override
	public String toString() {
		return "(Tex: " + filename + ")";
	}

}
