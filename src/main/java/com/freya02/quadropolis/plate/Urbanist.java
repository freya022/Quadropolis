package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.TileCoordinates;
import com.freya02.quadropolis.ui.Utils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		final ImageView view = new ImageView(new Image(Utils.getResourceAsStream("/com/freya02/quadropolis/ui/media/urbanisteLogo.PNG")));
		view.setFitHeight(100);
		view.setFitWidth(100);
		view.setPreserveRatio(true);

		return view;
	}
}
