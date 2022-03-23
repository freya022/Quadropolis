package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.Logging;
import com.freya02.quadropolis.PlacedArchitectCoordinates;
import com.freya02.quadropolis.Quadropolis;
import com.freya02.quadropolis.plate.GlobalPlate;
import com.freya02.quadropolis.plate.Tile;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

public class GlobalPlateController {
	private static final Logger LOGGER = Logging.getLogger();
	private static final GlobalPlate globalPlate = Quadropolis.getInstance().getGlobalPlate();

	private final GameModel gameModel;
	private final Stage stage;

	@FXML private VBox vbox;

	public GlobalPlateController(GameModel gameModel, Stage stage) {
		this.gameModel = gameModel;
		this.stage = stage;
	}

	@FXML
	private void initialize() {
		for (int x = 0; x < globalPlate.getWidth(); x++) {
			for (int y = 0; y < globalPlate.getHeight(); y++) {
				final StackPane stackPane = getStackPane(x, y);

				int finalX = x;
				int finalY = y;

				gameModel.selectedArchitectProperty().addListener((a, b, newArchitect) -> {
					final boolean canClaimTile = globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromBottom(finalX))
							|| globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromTop(finalX))
							|| globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromLeft(finalY))
							|| globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromRight(finalY));

					stackPane.setDisable(!canClaimTile);
				});
			}
		}

		//Pour pouvoir sélectionner un endroit ou poser un architecte
		// Case x = 0 avec 1 <= y <= height - 1
		// Case y = 0 avec 1 <= x <= width - 1
		final int maxWidth = globalPlate.getWidth() + 2;
		final int maxHeight = globalPlate.getHeight() + 2;

		for (int x = 0; x < maxWidth; x++) {
			for (int y = 0; y < maxHeight; y++) {
				final StackPane stackPane = getOuterStackPane(x, y);

				if (stackPane.getStyleClass().contains("architectSelectableTile")) {
					stackPane.setCursor(Cursor.HAND);
					stackPane.disableProperty().bind(gameModel.canSelectArchitectCoordinatesProperty().not());

					int finalX = x;
					int finalY = y;
					stackPane.setOnMouseClicked(e -> onArchitectTileClick(finalX, finalY));
				}
			}
		}

		render();
	}

	private void onArchitectTileClick(int x, int y) {
		LOGGER.debug("Click architect side tile");

		PlacedArchitectCoordinates architectCoordinates;

		if (x == 0) {
			architectCoordinates = PlacedArchitectCoordinates.fromLeft(y);
		} else if (x == globalPlate.getWidth() + 1) {
			architectCoordinates = PlacedArchitectCoordinates.fromRight(y);
		} else if (y == 0) {
			architectCoordinates = PlacedArchitectCoordinates.fromTop(x);
		} else if (y == globalPlate.getHeight() + 1) {
			architectCoordinates = PlacedArchitectCoordinates.fromBottom(x);
		} else {
			throw new IllegalArgumentException("x = %d, y = %d".formatted(x, y));
		}

		gameModel.setSelectedArchitectCoordinates(architectCoordinates);
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
		} catch (Exception e) {
			LOGGER.error("An error occurred while rendering global plate", e);
		}
	}

	private StackPane getOuterStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y )).getChildren().get(x);
	}

	private StackPane getStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y + 1)).getChildren().get(x + 1);
	}
}
