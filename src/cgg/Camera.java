package cgg;

import static cgtools.Vector.*;
import static cgtools.Matrix.multiply;

import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;

public class Camera {

	public final double angle;
	public final int width;
	public final int height;
	public final Point position;
	public final Matrix matrix;
	
	public Camera(double angle, int width, int height, Point position, Matrix matrix) {
		this.angle = angle;
		this.width = width;
		this.height = height;
		this.position = position;
		this.matrix = matrix;
	}
	
	public Camera(double angle, int width, int height, Matrix matrix) {
		this(angle, width, height, zero, matrix);
	}
	
	public Camera(double angle, int width, int height) {
		this(angle, width, height, zero, null);
	}
	
	public Ray generateRay(double x, double y) {
		if(matrix != null) {
			return new Ray(multiply(matrix, position), normalize(multiply(matrix, calculateD(x, y))), 0, Double.POSITIVE_INFINITY);
		} else {
			return new Ray(position, normalize(calculateD(x, y)), 0, Double.POSITIVE_INFINITY);
		}
	}
	
	private Direction calculateD(double x, double y) {
		double dx = x - width/2.0;
		double dy = -(y - height/2.0);
		double dz = -((width/2.0)/Math.tan(angle/2.0));
		return direction(dx, dy, dz);
	}
}
