package cgg;

import static cgtools.Vector.direction;

import cgtools.Color;

public class Background implements Shape {
	
	public final BackgroundMaterial material;
	
	public Background(BackgroundMaterial material) {
		this.material = material;
	}
	
	public Background(Color color) {
		this(new BackgroundMaterial(color));
	}
	
	public Background() {
		this(new BackgroundMaterial(Color.black));
	}
	
	@Override
	public BoundingBox bound() {
		return BoundingBox.everything;
	}

	@Override
	public Hit intersect(Ray ray) {
		Double infinity = Double.POSITIVE_INFINITY;
		if (ray.t_max == infinity) {
			double[] uv = Utils.sphereTextureCoordinates(ray.d);
			return new Hit(infinity, ray.pointAt(infinity), direction(0,0,0), uv[0], uv[1], material);
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "Background " + material.toString();
	}
}
