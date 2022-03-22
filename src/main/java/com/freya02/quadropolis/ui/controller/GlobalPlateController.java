package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.Quadropolis;
import com.freya02.quadropolis.plate.GlobalPlate;
import com.freya02.quadropolis.plate.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GlobalPlateController {
	private static final GlobalPlate globalPlate = Quadropolis.getInstance().getGlobalPlate();

	@FXML private VBox vbox;

	private final Stage stage;

	public GlobalPlateController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void initialize() {
		for (int x = 0; x < globalPlate.getWidth(); x++) {
			for (int y = 0; y < globalPlate.getHeight(); y++) {
				final StackPane stackPane = getStackPane(x, y);

				int finalX = x;
				int finalY = y;
				stackPane.setOnMouseClicked(e -> onTileClick(finalX, finalY));
			}
		}

		render();
	}

	public void updatePanes() {

	}

	private void onTileClick(int x, int y) {
		System.out.println("x = " + x);
		System.out.println("y = " + y);
	}

	public void render() {
		try {
			for (int x = 0; x < globalPlate.getWidth(); x++) {
				for (int y = 0; y < globalPlate.getHeight(); y++) {
					final Tile tile = globalPlate.get(x, y);
					if (tile == null) throw new IllegalStateException("Global plate not initialized");

					final StackPane stackPane = getStackPane(x, y);

					stackPane.getChildren().setAll(tile.asGraphic());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StackPane getStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y + 1)).getChildren().get(x + 1);
	}
}
