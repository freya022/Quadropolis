package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.TileCoordinates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
		if (x < 0) throw new IllegalArgumentException("X < 0 : %d < 0".formatted(x));
		if (y < 0) throw new IllegalArgumentException("Y < 0 : %d < 0".formatted(y));
		if (x >= width) throw new IllegalArgumentException("X >= width : %d >= %d".formatted(x, width));
		if (y >= height) throw new IllegalArgumentException("Y >= height : %d >= %d".formatted(y, height));

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