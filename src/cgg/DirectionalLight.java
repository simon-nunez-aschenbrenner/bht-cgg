package cgg;

import static cgtools.Color.multiply;
import static cgtools.Vector.dotProduct;
import static cgtools.Vector.negate;
import static cgtools.Vector.normalize;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Util;

public class DirectionalLight implements Light {
	
	public final Direction d;
	public final Color c;

	public DirectionalLight(Direction d, Color c) {
		this.d = normalize(d);
		this.c = c;
	}

	@Override
	public Color incomingIntensity(Point x, Direction n, Shape s) {
		
		double cos = dotProduct(negate(d), n);
		if (cos > 0) {
			Hit hit = s.intersect(new Ray(x, negate(d), Util.EPSILON, Double.MAX_VALUE));
			if(hit == null) return multiply(c, cos);
		}
		return null;
		
	}
}
