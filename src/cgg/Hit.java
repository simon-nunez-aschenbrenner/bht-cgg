package cgg;

import cgtools.Direction;
import cgtools.Point;

public class Hit {
	
	public final double t;
	public final Point p;
	public final Direction n;
	public final double u;
	public final double v;
	public final Material m;
	
	public Hit(double t, Point p, Direction n, double u, double v, Material m) {
		this.t = t;
		this.p = p;
		this.n = n;
		this.u = u - Math.floor(u);
		this.v = v - Math.floor(v);
		this.m = m;
	}
	
//	public Hit(double t, Point p, Direction n, Material m) {
//		this(t, p, n, 0, 0, m);
//	}
}
