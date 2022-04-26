package cgg;

import static cgtools.Vector.*;

import cgtools.Direction;
import cgtools.Random;

public final class Utils {
	
	public static final double[] sphereTextureCoordinates(Direction n) {
		double azimuth = Math.PI + Math.atan2(n.x, n.z);
		double inclination = Math.acos(n.y);
		double u = azimuth / (2 * Math.PI);
		double v = inclination / Math.PI;
		return new double[] {u, v};
	}
	
	public static final Direction randomDir() {
		Direction random;
		do
			random = direction(Random.random()*2-1, Random.random()*2-1, Random.random()*2-1);
		while (length(random ) > 1);
		return random;
	}
	
	public static final Direction reflect(Direction d, Direction n) {
		return subtract(d, multiply(2 * dotProduct(n, d), n));
	}
	
	public static final Direction reflectRough(Direction d, Direction n, double roughness) {
		Direction r = reflect(d, n);
		if (roughness <= 0) {
			return r;
		} else {
			Direction s = add(r, multiply(roughness, randomDir()));
			if (dotProduct(s, n) < 0) {
				return null;
			} else {
				return s;
			}
		}
	}
	
	public static final Direction refract(Direction d, Direction n, double n1, double n2) {
		double r = n1/n2;
		double c = - dotProduct(n, d);
		double discriminant = 1-(r*r)*(1-(c*c));
		if (discriminant < 0) {
			return null;
		} else {
			return add(multiply(r, d), multiply(r*c - Math.sqrt(discriminant), n));
		}
	}
	
	public static final double schlick(Direction d, Direction n, double n1, double n2) {
		double r0 = Math.pow((n1-n2)/(n1+n2), 2);
		return r0 + (1 - r0) * Math.pow(1 + dotProduct(n, d), 5);
	}
}
