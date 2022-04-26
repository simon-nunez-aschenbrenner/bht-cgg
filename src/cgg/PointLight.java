package cgg;

import static cgtools.Color.divide;
import static cgtools.Color.multiply;
import static cgtools.Util.EPSILON;
import static cgtools.Vector.dotProduct;
import static cgtools.Vector.length;
import static cgtools.Vector.normalize;
import static cgtools.Vector.subtract;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;

public class PointLight implements Light {
	
	public final Point p;
	public final Color c;

	public PointLight(Point p, Color c) {
		this.p = p;
		this.c = c;
	}

	@Override
	public Color incomingIntensity(Point x, Direction n, Shape s) {
		
		Direction toLight = subtract(p, x);
		Direction normal = normalize(toLight);
		double length = length(toLight);

		Hit hit = s.intersect(new Ray(x, normal, EPSILON, length));

		if(hit == null) return multiply(divide(c, length*length), dotProduct(normal, n));
		else return null;
		
	}
}
