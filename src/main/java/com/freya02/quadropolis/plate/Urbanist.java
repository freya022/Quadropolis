package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.TileCoordinates;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class Urbanist extends Tile {
	private TileCoordinates coords = new TileCoordinates(0, 0);

	public void setCoords(TileCoordinates coords) {
		this.coords = coords;
	}

	public TileCoordinates getCoords() {
		return coords;
	}

	@Override
	@NotNull
	public Node asGraphic() {
		return new Label("Urbanist");
	}
}
