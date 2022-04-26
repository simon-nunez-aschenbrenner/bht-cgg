package cgg;

import static cgtools.Matrix.identity;
import static cgtools.Matrix.multiply;

import cgtools.Matrix;
import java.util.ArrayList;

public class Group implements Shape {

	public final Transformation transformation;
	private ArrayList<Shape> shapes;
	private BoundingBox box;
	
	private Group(Matrix matrix, BoundingBox box) {
		transformation = new Transformation(matrix);
		shapes = new ArrayList<Shape>();
		this.box = box;
	}
	
	public Group(Matrix matrix) {
		this(matrix, BoundingBox.empty);
	}
	
	public Group(Matrix matrix, Shape ...shapes) {
		this(matrix, BoundingBox.empty);
		for (Shape s : shapes) {
			addShape(s);
		}
	}
	
	public Group(Shape ...shapes) {
		this(identity, shapes);
	}
	
	public Group() {
		this(identity, BoundingBox.empty);
	}
	
	public void addShape(Shape s) {
		shapes.add(s);
		BoundingBox sbb = s.bound();
		if (sbb.equals(BoundingBox.everything)) box = BoundingBox.everything;
		else box = box.extend(sbb.transform(transformation.matrix));
	}
	
	public Group initBVH() {
		Group bvh = new Group();
		flattenBVH(bvh, identity);
		bvh.constructBVH();
		return bvh;
	}
	
	public void flattenBVH(Group root, Matrix parent) {
		Matrix child = multiply(parent, transformation.matrix);
		for (Shape s : shapes) {
			if (s instanceof Group) {
				Group g = (Group) s;
				g.flattenBVH(root, child);
			} else {
				Group g = new Group(child, s);
				root.addShape(g);
			}
		}
	}
	
	private void constructBVH() {
		if (shapes.size() < 2) return;
		else {
			BoundingBox bb = bound();
			BoundingBox lbb = bb.splitLeft();
			BoundingBox rbb = bb.splitRight();
			Group lg = new Group();
			Group rg = new Group();
			Object[] groups = shapes.toArray();	
			shapes.clear();
			for (Object o : groups) {
				Group g = (Group) o;
				BoundingBox sbb = g.bound();
				if (lbb.contains(sbb)) lg.addShape(g);
				else if (rbb.contains(sbb)) rg.addShape(g);
				else shapes.add(g);
			}
			shapes.add(lg);
			shapes.add(rg);
			lg.constructBVH();
			rg.constructBVH();
		}
	}
	
	@Override
	public BoundingBox bound() {
		return box;
	}

	@Override
	public Hit intersect(Ray ray) {
		Hit closest = null;
		if (bound().intersect(ray)) {
			Ray rayTrans = transformation.transformRay(ray);
			double t = Double.POSITIVE_INFINITY;
			for (Shape s : shapes) {
				Hit current = s.intersect(rayTrans);
				if (current != null && current.t <= t) {
					t = current.t;
					closest = current;
				}
			}
		}
		if (closest == null) return null;
		else return transformation.transformHit(closest);
	}
	
	@Override
	public String toString() {
		String result = String.format("GROUP (%d Elem) [", shapes.size());
		for (Shape s : shapes) {
			result += s.toString();
			result += ", ";
		}
		return result.substring(0, result.length()-2) + "]";
	}
}
