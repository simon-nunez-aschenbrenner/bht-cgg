package cgg;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cgtools.Color;
import cgtools.ImageWriter;
import cgtools.Random;
import cgtools.Sampler;

public class Image {
	
	protected int width;
	protected int height;
	protected int components;
	protected double gamma;
	protected int threads;
	private double[] array;
	
	public Image(int width, int height, int components, double gamma, int threads) {
		this.width = width;
		this.height = height;
		this.components = components;
		this.gamma = gamma;
		this.array = new double[components * width * height];
		this.threads = threads;
	}
	
	public Image(int width, int height, int threads) {
		this(width, height, 3, 2.2, threads);
	}
	
	public Image(int width, int height) {
		this(width, height, 3, 2.2, Runtime.getRuntime().availableProcessors());
	}

	public void setPixel(int x, int y, Color color) {
		int i_r = components * (y * width + x) + 0;
		int i_g = components * (y * width + x) + 1;
		int i_b = components * (y * width + x) + 2;	
		array[i_r] = Math.pow(color.r, 1.0/gamma);
		array[i_g] = Math.pow(color.g, 1.0/gamma);
		array[i_b] = Math.pow(color.b, 1.0/gamma);
	}
	
	public void sample(Sampler sampler, int samples) {
		
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		HashMap<Integer, Future<Color>> pixels = new HashMap<Integer, Future<Color>>(width*height);
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				pixels.put(Integer.valueOf(y * width + x), pool.submit(new Pixel(sampler, samples, x, y)));
			}
		}
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				try {
					Future<Color> pixel = pixels.get(y * width + x);
					while (!pixel.isDone()) {}
					Color color = pixel.get();
					setPixel(x, y, color);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			if (width > 200 && x != 0 && x%(width/200) == 0) System.out.print(".");
			if (x != 0 && x%(width/20) == 0) System.out.printf(" %2.0f %%%n", (100.0*x) / (1.0*width));
		}
		pool.shutdown();
		System.out.println(" 100 %\n");
	}
	
	public void write(String filename) {
		ImageWriter.write(filename, array, width, height);
		System.out.println("Wrote image: " + filename);
	}
	
	public void sampleOld(Sampler sampler, int samples) {
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {	
				Color color = new Color(0, 0, 0);
				for(int xi = 0; xi != samples; xi++) {
					for(int yi = 0; yi != samples; yi++) {
						double rx = Random.random();
						double ry = Random.random();
						double xs = x + (xi+rx)/samples;
						double ys = y + (yi+ry)/samples;
						color = Color.add(color, sampler.getColor(xs, ys));
					}
				}	
				this.setPixel(x, y, Color.divide(color, samples * samples));
			}
			if (width > 200 && x != 0 && x%(width/200) == 0) System.out.print(".");
			if (x != 0 && x%(width/20) == 0) System.out.printf(" %2.0f %%%n", (100.0*x) / (1.0*width));
		}
		System.out.println(" 100 %\n");
	}
}
