package com.freya02.quadropolis;

public class PlacedArchitect {
	private final Architect architect;
	private final PlacedArchitectCoordinates coordinates;

	public PlacedArchitect(Architect architect, PlacedArchitectCoordinates coordinates) {
		this.architect = architect;
		this.coordinates = coordinates;
	}

	public Architect getArchitect() {
		return architect;
	}

	public PlacedArchitectCoordinates getCoordinates() {
		return coordinates;
	}
}
