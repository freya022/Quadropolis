package com.freya02.quadropolis.plate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Plate {
	//2D flat array
	private final Tile[] tiles;

	private final int width;

	protected Plate(int width, int height) {
		this.width = width;

		tiles = new Tile[width * height];
	}

	@NotNull
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
		return x + y * width;
	}
}