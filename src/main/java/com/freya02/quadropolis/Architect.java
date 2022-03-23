package com.freya02.quadropolis;

public class Architect {
	private final int reach;

	public Architect(int reach) {
		this.reach = reach;
	}

	public int getReach() {
		return reach;
	}

	public int getVisualReach() {
		return reach + 1;
	}
}
