package cgg;

import java.util.concurrent.Callable;

import cgtools.Color;
import cgtools.Random;
import cgtools.Sampler;

public class Pixel implements Callable<Color>{
			
	public final Sampler sampler;
	public final int samples;
	public final int x;
	public final int y;
	
	public Pixel(Sampler sampler, int samples, int x, int y) {
		this.sampler = sampler;
		this.samples = samples;
		this.x = x;
		this.y = y;
	}
	
	public Color call() {
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
		return Color.divide(color, samples * samples);
	}
}
