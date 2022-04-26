/** @author henrik.tramberend@beuth-hochschule.de */
package cgtools;

import static cgtools.Vector.*;

public final class Color {
  public final double r, g, b;

  public Color(double r, double g, double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public static Color add(Color a, Color b, Color... vs) {
    Color r = color(a.r + b.r, a.g + b.g, a.b + b.b);
    for (Color v : vs) {
      r = color(r.r + v.r, r.g + v.g, r.b + v.b);
    }
    return r;
  }

  public static Color subtract(Color a, Color b, Color... vs) {
    Color r = color(a.r - b.r, a.g - b.g, a.b - b.b);
    for (Color v : vs) {
      r = color(r.r - v.r, r.g - v.g, r.b - v.b);
    }
    return r;
  }

  public static Color multiply(double s, Color a) {
    return color(s * a.r, s * a.g, s * a.b);
  }

  public static Color multiply(Color a, double s) {
    return multiply(s, a);
  }

  public static Color multiply(Color a, Color b) {
    return color(a.r * b.r, a.g * b.g, a.b * b.b);
  }

  public static Color divide(Color a, double s) {
    return color(a.r / s, a.g / s, a.b / s);
  }

  public static Color clamp(Color v) {
    return color(
        Math.min(1, Math.max(v.r, 0)),
        Math.min(1, Math.max(v.g, 0)),
        Math.min(1, Math.max(v.b, 0)));
  }

  public static Color hsvToRgb(Color hsv) {
    return multiply(hsv.b, add(multiply(hsv.g, subtract(hue(hsv.r), white)), white));
  }

  @Override
  public String toString() {
    return String.format("(Col: %.2f %.2f %.2f)", r, g, b);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Color)) return false;
    if (o == this) return true;
    Color v = (Color) o;
    return v.r == r && v.g == g && v.b == b;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Double.valueOf(r).hashCode();
    result = prime * result + Double.valueOf(g).hashCode();
    result = prime * result + Double.valueOf(b).hashCode();
    return result;
  }

  public static final Color black = color(0, 0, 0);
  public static final Color gray = color(0.5, 0.5, 0.5);
  public static final Color white = color(1, 1, 1);
  public static final Color red = color(1, 0, 0);
  public static final Color green = color(0, 1, 0);
  public static final Color blue = color(0, 0, 1);

  private static Color hue(double h) {
    double r = Math.abs(h * 6 - 3) - 1;
    double g = 2 - Math.abs(h * 6 - 2);
    double b = 2 - Math.abs(h * 6 - 4);
    return clamp(color(r, g, b));
  }
}
