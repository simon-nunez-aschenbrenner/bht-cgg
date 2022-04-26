package cgg;

public interface Shape {

	public BoundingBox bound();
	public Hit intersect(Ray ray);

}
