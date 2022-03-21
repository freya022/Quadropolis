package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.Quadropolis;
import com.freya02.quadropolis.plate.Tile;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GlobalPlateController {
	@FXML private GridPane grid;

	private final Stage stage;

	public GlobalPlateController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void initialize() {
		final ObservableList<Tile> tiles = Quadropolis.getInstance().getGlobalPlate().getTiles();
		tiles.addListener((InvalidationListener) c -> {

		});
	}
}
