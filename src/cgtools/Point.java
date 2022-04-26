/** @author henrik.tramberend@beuth-hochschule.de */
package cgtools;

public final class Point extends Vector {

  protected Point(double x, double y, double z) {
    super(x, y, z);
  }

  @Override
  public String toString() {
    return String.format("(Pnt: %.2f %.2f %.2f)", x, y, z);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Point)) return false;
    if (o == this) return true;
    Point v = (Point) o;
    return Util.isZero(length(subtract(this, v)));
  }
}
