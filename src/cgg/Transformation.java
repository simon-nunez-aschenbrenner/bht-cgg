package cgg;

import static cgtools.Matrix.invert;
import static cgtools.Matrix.multiply;
import static cgtools.Matrix.transpose;

import cgtools.Matrix;

public class Transformation {
	
	public final Matrix matrix;
	public final Matrix inverse;
	public final Matrix inverseTranspose;
	
	public Transformation(Matrix matrix) {
		this.matrix = matrix;
		this.inverse = invert(matrix);
		this.inverseTranspose = transpose(inverse);
	}
	
	public Ray transformRay(Ray ray) {
		return new Ray(multiply(inverse, ray.x0), multiply(inverse, ray.d), ray.t_min, ray.t_max);
	}
	
	public Hit transformHit(Hit hit) {
		if (hit != null) {
			return new Hit(hit.t, multiply(matrix, hit.p), multiply(inverseTranspose, hit.n), hit.u, hit.v, hit.m);
		} else {
			return null;
		}
	}

}
