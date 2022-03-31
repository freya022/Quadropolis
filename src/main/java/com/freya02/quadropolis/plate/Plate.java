package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.TileCoordinates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public abstract class Plate {
	//2D flat array
	private final ObservableList<Tile> tiles;

	private final int width;
	private final int height;

	protected Plate(int width, int height) {
		this.width = width;
		this.height = height;

		tiles = FXCollections.observableArrayList();

		for (int i = 0; i < width * height; i++) {
			tiles.add(null);
		}
	}

	public ObservableList<Tile> getTiles() {
		return tiles;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Nullable
	public Tile get(int x, int y) {
		return tiles.get(getCoordinates(x, y));
	}

	@Nullable
	public Tile tryGet(int x, int y) {
		final int coordinatesOrNegative = getCoordinatesOrNegative(x, y);
		if (coordinatesOrNegative == -1) return null;

		return tiles.get(coordinatesOrNegative);
	}

	/**
	 * May return the replaced Tile
	 */
	@Nullable
	public Tile set(int x, int y, @Nullable Tile tile) {
		final Tile oldTile = tiles.get(getCoordinates(x, y));

		tiles.set(getCoordinates(x, y), tile);

		return oldTile;
	}

	private int getCoordinates(int x, int y) {
		if (x < 0) throw new IllegalArgumentException(String.format("X < 0 : %d < 0", x));
		if (y < 0) throw new IllegalArgumentException(String.format("Y < 0 : %d < 0", y));
		if (x >= width) throw new IllegalArgumentException(String.format("X >= width : %d >= %d", x, width));
		if (y >= height) throw new IllegalArgumentException(String.format("Y >= height : %d >= %d", y, height));

		return x + y * width;
	}

	@Range(from = -1, to = Integer.MAX_VALUE)
	private int getCoordinatesOrNegative(int x, int y) {
		if (x < 0) return -1;
		if (y < 0) return -1;
		if (x >= width) return -1;
		if (y >= height) return -1;

		return x + y * width;
	}

	@Nullable
	public Tile get(@NotNull TileCoordinates tileCoordinates) {
		return get(tileCoordinates.x(), tileCoordinates.y());
	}

	/**
	 * May return the replaced Tile
	 */
	@Nullable
	public Tile set(@NotNull TileCoordinates tileCoordinates, @Nullable Tile tile) {
		return set(tileCoordinates.x(), tileCoordinates.y(), tile);
	}
}