package cgg;

import java.util.ArrayList;
import cgtools.Color;
import cgtools.Random;

public class A02 {
	
	public static void main(String[] args) {
		
		final int width = 1000;
		final int height = 1000;
		final Color background = Color.black;
		final int samples = 10; // Anzahl der tatsächlichen Samples ist Quadrat dieses Wertes
		String filename;
		
		class Disc {
			
			double x;
			double y;
			double radius;
			Color color;
			
			public Disc(double x, double y, double radius, Color color) {
				this.x = x;
				this.y = y;
				this.radius = radius;
				this.color = color;	
			}
			
			public boolean isPointInDisc(double x, double y) {
				x = this.x - x;
				y = this.y - y;
				return (Math.sqrt(x*x + y*y) <= radius);
			}
			
			public Color getColor() {
				return color;
			}
		}
		
		class ColoredDiscs {

			ArrayList<Disc> discs;

			/**
			 * @param amount - Anzahl versch. Discs - Sollte ein Vielfaches von variants sein
			 * @param variants - Anzahl versch. Radien - Muss <= (amount/2) oder = amount sein
			 * @param factor - Radius der größten Disc(s) - Muss > 0 und <= 1 sein
			 */
			public ColoredDiscs(int amount, int variants, double factor) {
				
				discs = new ArrayList<Disc>(amount);
				factor = amount/((variants/factor)*amount);
				int step = amount/variants;
				int counter = variants + 1;
				
				for(int i = 0; i < amount; i++) {
					
					if (i % step == 0) { counter--; }
					Color color = new Color(Random.random(), Random.random(), Random.random());
					discs.add(i, new Disc(Random.random(), Random.random(), counter*factor, color));
				}
			}
			
			Color getColor(double x, double y) {
				
				x = x/Math.max(width, height);
				y = y/Math.max(width, height);
				Color color = background;
				
				for(Disc d : discs) {
					if(d.isPointInDisc(x, y)) { color = d.getColor(); }
				}
				return color;
			}
		}
		
		Image image = new Image(width, height);
		ColoredDiscs discs = new ColoredDiscs(100, 10, 0.25);

		filename = "doc/a02-discs.png";
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				
				image.setPixel(x, y, discs.getColor(x, y));
			}
		}
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		filename = "doc/a02-discs-supersampling.png";
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				
				Color color = new Color(0, 0, 0);
				
				for(int xi = 0; xi != samples; xi++) {
					for(int yi = 0; yi != samples; yi++) {
						
						double rx = Random.random();
						double ry = Random.random();
						double xs = x + (xi+rx)/samples;
						double ys = y + (yi+ry)/samples;
						color = Color.add(color, discs.getColor(xs, ys));
					}
				}	
				image.setPixel(x, y, Color.divide(color, samples * samples));
			}
		}
		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}
}