package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.Plate;

import java.util.Objects;

/**
 * x {@link Integer#MIN_VALUE} if on the left, {@link Integer#MAX_VALUE} if on the right
 * <br>y {@link Integer#MIN_VALUE} if on the top, {@link Integer#MAX_VALUE} if on the bottom
 */
public final class PlacedArchitectCoordinates {
	private final int x;
	private final int y;

	PlacedArchitectCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {return x;}

	public int y() {return y;}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (PlacedArchitectCoordinates) obj;
		return this.x == that.x &&
				this.y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "PlacedArchitectCoordinates[" +
				"x=" + x + ", " +
				"y=" + y + ']';
	}

	public static PlacedArchitectCoordinates fromTop(int x) {
		return new PlacedArchitectCoordinates(x, Integer.MIN_VALUE);
	}

	public static PlacedArchitectCoordinates fromBottom(int x) {
		return new PlacedArchitectCoordinates(x, Integer.MAX_VALUE);
	}

	public static PlacedArchitectCoordinates fromLeft(int y) {
		return new PlacedArchitectCoordinates(Integer.MIN_VALUE, y);
	}

	public static PlacedArchitectCoordinates fromRight(int y) {
		return new PlacedArchitectCoordinates(Integer.MAX_VALUE, y);
	}

	public boolean isTop() {
		return y == Integer.MIN_VALUE;
	}

	public boolean isBottom() {
		return y == Integer.MAX_VALUE;
	}

	public boolean isLeft() {
		return x == Integer.MIN_VALUE;
	}

	public boolean isRight() {
		return x == Integer.MAX_VALUE;
	}

	public TileCoordinates toTileCoordinates(Plate plate, Architect architect) {
		if (isLeft()) {
			return new TileCoordinates(architect.getReach(), y);
		} else if (isRight()) {
			return new TileCoordinates(plate.getWidth() - 1 - architect.getReach(), y);
		} else if (isTop()) {
			return new TileCoordinates(x, architect.getReach());
		} else if (isBottom()) {
			return new TileCoordinates(x, plate.getHeight() - 1 - architect.getReach());
		} else {
			throw new IllegalStateException(String.format("Invalid architect coordinates: %d, %d", x, y));
		}
	}
}
