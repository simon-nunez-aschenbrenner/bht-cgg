package cgg;

import cgtools.Color;

public class Properties {
	
	public final Color emission;
	public final Color albedo;
	public final Ray scatteredRay;
	
	public Properties(Color emission, Color albedo, Ray scatteredRay) {
		this.emission = emission;
		this.albedo = albedo;
		this.scatteredRay = scatteredRay;
	}
}
