package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.ui.Utils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

public class BuildingController {
	private final Building building;

	@FXML private ImageView tileIconView;
	@FXML private HBox tileCostBox, tileRevenueBox;

	public BuildingController(Building building) {
		this.building = building;
	}

	@FXML
	private void initialize() {
		tileIconView.setImage(new Image(Utils.getResourceAsStream(building.getBuildingType().getResourcePath())));

		for (int i = 0; i < building.getActivationCost().getBarrels(); i++) {
			final ImageView view = getImageView("/com/freya02/quadropolis/ui/media/activationBaril.png");

			tileCostBox.getChildren().add(view);
		}

		for (int i = 0; i < building.getActivationCost().getHouses(); i++) {
			final ImageView view = getImageView("/com/freya02/quadropolis/ui/media/activationVillageois.png");

			tileCostBox.getChildren().add(view);
		}

		for (int i = 0; i < building.getRevenue().getBarrels(); i++) {
			final ImageView view = getImageView("/com/freya02/quadropolis/ui/media/gainBaril.png");

			tileRevenueBox.getChildren().add(view);
		}

		for (int i = 0; i < building.getRevenue().getHouses(); i++) {
			final ImageView view = getImageView("/com/freya02/quadropolis/ui/media/gainVillageois.png");

			tileRevenueBox.getChildren().add(view);
		}
	}

	@NotNull
	private ImageView getImageView(String url) {
		final ImageView view = new ImageView();
		view.setFitWidth(15);
		view.setFitHeight(15);
		view.setPreserveRatio(true);
		view.setImage(new Image(Utils.getResourceAsStream(url)));

		return view;
	}
}
