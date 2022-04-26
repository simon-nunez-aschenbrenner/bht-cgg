package cgg;

import static cgtools.Matrix.multiply;
import static cgtools.Vector.point;

import cgtools.Color;
import cgtools.Matrix;
import cgtools.Point;
import cgtools.Sampler;

public class TextureTransform implements Sampler {
	
	public final Sampler s;
	public final Matrix m;

	public TextureTransform(Sampler s, Matrix m) {
		this.s = s;
		this.m = m;
	}

	@Override
	public Color getColor(double u, double v) {
		Point p = multiply(m, point(u, v, 0));
		return s.getColor(p.x, p.y);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
