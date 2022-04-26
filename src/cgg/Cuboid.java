package cgg;

import static cgtools.Vector.add;
import static cgtools.Vector.direction;
import static cgtools.Vector.divide;
import static cgtools.Vector.normalize;
import static cgtools.Vector.point;
import static cgtools.Vector.subtract;
import static cgtools.Util.EPSILON;

import cgtools.Direction;
import cgtools.Point;

public class Cuboid implements Shape {
	
	public final Material m;
	public final Point min;
	public final Point max;

	public Cuboid(Material m, Point min, Point max) {
		this.m = m;
		this.min = min;
		this.max = max;
	}
	
	public Cuboid(Material m, Point center, Direction dimension) {
		this(m, subtract(center, dimension), add(center, dimension));
	}
	
	public Cuboid(Material m) {
		this(m, point(-0.5, -0.5, -0.5), point(0.5, 0.5, 0.5));
	}

	@Override
	public BoundingBox bound() {
		return new BoundingBox(min, max);
	}

	@Override
	public Hit intersect(Ray ray) {
		double dix = 1.0 / ray.d.x;
		double diy = 1.0 / ray.d.y;
		double diz = 1.0 / ray.d.z;

		double tx1 = (min.x - ray.x0.x) * dix;
		double tx2 = (max.x - ray.x0.x) * dix;

		double tmin = Math.min(tx1, tx2);
		double tmax = Math.max(tx1, tx2);

		double ty1 = (min.y - ray.x0.y) * diy;
		double ty2 = (max.y - ray.x0.y) * diy;

		tmin = Math.max(tmin, Math.min(ty1, ty2));
		tmax = Math.min(tmax, Math.max(ty1, ty2));

		double tz1 = (min.z - ray.x0.z) * diz;
		double tz2 = (max.z - ray.x0.z) * diz;

		tmin = Math.max(tmin, Math.min(tz1, tz2));
		tmax = Math.min(tmax, Math.max(tz1, tz2));
		
		if (tmax >= tmin) {
			Point x = ray.pointAt(tmin);
			
			if (x != null) {
				
				Point c = point((min.x+max.x)/2, (min.y+max.y)/2, (min.z+max.z)/2);
				Direction p = subtract(c, x);
				Direction d = subtract(min, max);
				double s = Math.max(Math.max(d.x, d.y), d.z);
				d = divide(d, 2);
				double e = 1 + EPSILON;
				
				double nx = (int) (p.x / Math.abs(d.x) * e);
				double ny = (int) (p.y / Math.abs(d.y) * e);
				double nz = (int) (p.z / Math.abs(d.z) * e);
				
				Direction n = normalize(direction(nx, ny, nz));
				
				return new Hit(tmin, x, n, x.x/(2*s)+0.5, x.z/(2*s)+0.5, m);
			}
		}
		return null;
	}

}
