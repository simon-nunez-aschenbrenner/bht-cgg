package cgg;

import cgtools.Color;

public class A01 {

	public static void main(String[] args) {
		
		final int width = 1500;
		final int height = 1000;
		final Color background = Color.black;
		String filename;

		class ConstantColor {
			
			Color color;

			ConstantColor(Color color) {
				this.color = color;
			}

			Color getColor(double x, double y) {
				return color;
			}
		}
		
		class ColoredDisc {
			
			Color color;
			int radius;
			
			ColoredDisc(Color color, int radius) {
				this.color = color;
				this.radius = radius;
			}
			
			Color getColor(double x, double y) {
				
				// Disk center in the image center
				x = width/2 - x;
				y = height/2 - y;
				double p = Math.sqrt(x*x + y*y);
				
				if(p <= radius) { return color; }
				else { return background; }
			}
		}
		
		class CheckeredDisc {
			
			ColoredDisc disc;
			Color discColor;
			Color checkColor;
			int squareSize;
			
			CheckeredDisc(Color discColor, int radius, Color checkColor, int squareSize) {
				this.disc = new ColoredDisc(discColor, radius);
				this.discColor = discColor;
				this.checkColor = checkColor;
				this.squareSize = squareSize;	
			}
			
			Color getColor(double x, double y) {
				
				Color d = disc.getColor(x, y);
				
				if(d == discColor) { return discColor; }
				else {
					boolean checkX = (int) (x/squareSize) % 2 == 0;
					boolean checkY = (int) (y/squareSize) % 2 == 0;
					
					if(checkX ^ checkY) { return checkColor; }
					else { return background; }
				}
			}
		}

		Image image = new Image(width, height);

		filename = "doc/a01-image.png";	
		ConstantColor allGray = new ConstantColor(Color.gray);
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
			image.setPixel(x, y, allGray.getColor(x, y));
			}
		}
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		filename = "doc/a01-disc.png";
		ColoredDisc disc = new ColoredDisc(Color.red, height/3);
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
			image.setPixel(x, y, disc.getColor(x, y));
			}
		}
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		filename = "doc/a01-disc-checkered.png";
		CheckeredDisc checkDisc = new CheckeredDisc(Color.red, height/3, Color.blue, height/10);
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
			image.setPixel(x, y, checkDisc.getColor(x, y));
			}
		}
		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}
}