package cgg;

import cgtools.Color;
import cgtools.Sampler;

public class CheckerBoard implements Sampler {
	
	public final Sampler a;
	public final Sampler b;
	public final int n;
	
	public CheckerBoard(Sampler a, Sampler b, int n) {
		this.a = a;
		this.b = b;
		this.n = n;
	}

	@Override
	public Color getColor(double u, double v) {
		double ui = (int) ((u % 1) * n);
		double vi = (int) ((v % 1) * n);
		if ((ui + vi) % 2 == 0) return a.getColor(u, v);
		else return b.getColor(u, v);
	}
	
	@Override
	public String toString() {
		return String.format("(Checkerboard %s, %s)", a.toString(), b.toString());
	}

}
