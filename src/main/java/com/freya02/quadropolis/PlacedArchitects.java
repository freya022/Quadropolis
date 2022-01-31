package com.freya02.quadropolis;

public class PlacedArchitects {
	private final Architect architect;
	private final int x, y;

	public PlacedArchitects(Architect architect, int x, int y) {
		this.architect = architect;
		this.x = x;
		this.y = y;
	}

	public Architect getArchitect() {
		return architect;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
