package com.freya02.quadropolis.plate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Plate {
	//2D flat array
	private final Tile[] tiles;

	private final int width;
	private final int height;

	protected Plate(int width, int height) {
		this.width = width;
		this.height = height;

		tiles = new Tile[width * height];
	}

	@Nullable
	public Tile get(int x, int y) {
		return tiles[getCoordinates(x, y)];
	}

	/**
	 * May return the replaced Tile
	 */
	@Nullable
	public Tile set(int x, int y, @NotNull Tile tile) {
		final Tile oldTile = tiles[getCoordinates(x, y)];

		tiles[getCoordinates(x, y)] = tile;

		return oldTile;
	}

	private int getCoordinates(int x, int y) {
		if (x < 0) throw new IllegalArgumentException("X < 0 : %d < 0".formatted(x));
		if (y < 0) throw new IllegalArgumentException("Y < 0 : %d < 0".formatted(y));
		if (x >= width) throw new IllegalArgumentException("X >= width : %d >= %d".formatted(x, width));
		if (y >= height) throw new IllegalArgumentException("Y >= height : %d >= %d".formatted(y, height));

		return x + y * width;
	}
}