package com.freya02.quadropolis;

import java.util.Objects;

public final class TileCoordinates {
	private final int x;
	private final int y;

	public TileCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {return x;}

	public int y() {return y;}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (TileCoordinates) obj;
		return this.x == that.x &&
				this.y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "TileCoordinates[" +
				"x=" + x + ", " +
				"y=" + y + ']';
	}

}
