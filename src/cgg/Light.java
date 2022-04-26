package cgg;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;

public interface Light {
	
	public Color incomingIntensity(Point x, Direction n, Shape s);

}
