package cgg;

import cgtools.Color;
import cgtools.Sampler;

public class Dot implements Sampler {
	
	public final Sampler a;
	public final Sampler b;
	public final double r;

	public Dot(Sampler a, Sampler b, double d) {
		this.a = a;
		this.b = b;
		this.r = (d - Math.floor(d))/2;
	}

	@Override
	public Color getColor(double u, double v) {
		u = (u % 1) - 0.5;
		v = (v % 1) - 0.5;
		if (Math.sqrt(u*u + v*v) < r) return a.getColor(u, v);
		else return b.getColor(u, v);
	}
	
	@Override
	public String toString() {
		return String.format("(Dot %s, %s)", a.toString(), b.toString());
	}

}
