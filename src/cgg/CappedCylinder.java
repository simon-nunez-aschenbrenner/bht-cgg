package cgg;

import static cgtools.Matrix.identity;
import static cgtools.Vector.add;
import static cgtools.Vector.multiply;
import static cgtools.Vector.negate;

import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;

public class CappedCylinder extends Group implements Shape {
	
	public static final Point p = Cylinder.p;
	public static final Direction v = Cylinder.v;
	
	public CappedCylinder(Matrix o, double r, double l, Material m) {		
		super(o, new Plane(p, negate(v), r, m), new Plane(add(p, multiply(v, l)), v, r, m), new Cylinder(l, r, m));
	}
	
	public CappedCylinder(Matrix o, Material m) {		
		this(o, 1, 1, m);
	}

	public CappedCylinder(Material m) {		
		this(identity, 1, 1, m);
	}
	
	@Override
	public String toString() {
		return "Capped Cylinder " + super.toString();
	}
}
