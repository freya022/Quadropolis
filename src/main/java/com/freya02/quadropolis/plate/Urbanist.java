package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.TileCoordinates;

public class Urbanist extends Tile {
	private TileCoordinates coords = new TileCoordinates(0, 0);

	public void setCoords(TileCoordinates coords) {
		this.coords = coords;
	}

	public TileCoordinates getCoords() {
		return coords;
	}
}
