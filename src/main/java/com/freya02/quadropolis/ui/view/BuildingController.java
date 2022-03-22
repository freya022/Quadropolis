package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.plate.Building;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BuildingController {
	private final Building building;

	@FXML private ImageView tileIconView, tileTypeIconView;
	@FXML private HBox tileCostBox, tileRevenueBox;

	public BuildingController(Building building) {
		this.building = building;
	}

	@FXML
	private void initialize() {
		for (int i = 0; i < building.getActivationCost().getBarrels(); i++) {
			tileCostBox.getChildren().add(new Label("B"));
		}

		for (int i = 0; i < building.getActivationCost().getHouses(); i++) {
			tileCostBox.getChildren().add(new Label("H"));
		}

		for (int i = 0; i < building.getRevenue().getBarrels(); i++) {
			tileRevenueBox.getChildren().add(new Label("B"));
		}

		for (int i = 0; i < building.getRevenue().getHouses(); i++) {
			tileRevenueBox.getChildren().add(new Label("H"));
		}
	}
}
