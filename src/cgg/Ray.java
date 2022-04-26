package cgg;

import static cgtools.Vector.*;
import cgtools.Direction;
import cgtools.Point;

public class Ray {
		
	public final Point x0;
	public final Direction d;
	public final double t_min;
	public final double t_max;
	
	public Ray(Point x0, Direction d, double t_min, double t_max) throws IllegalArgumentException {
		this.x0 = x0;
		this.d = d;
		if (t_min >= 0) {
			this.t_min = t_min;
		} else {
			throw new IllegalArgumentException("t_max muss >= 0 sein");
		}
		if (t_max >= t_min) {
			this.t_max = t_max;
		} else {
			throw new IllegalArgumentException("t_max muss >= t_min sein");
		}
	}
	
	public Point pointAt(double t) {
		if(t >= t_min && t <= t_max) {
			return add(x0, multiply(t,d));
		} else {
			return null;
		}
	}
}
