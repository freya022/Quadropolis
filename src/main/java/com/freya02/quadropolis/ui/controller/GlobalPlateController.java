package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.Logging;
import com.freya02.quadropolis.PlacedArchitectCoordinates;
import com.freya02.quadropolis.Quadropolis;
import com.freya02.quadropolis.plate.GlobalPlate;
import com.freya02.quadropolis.plate.Tile;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class GlobalPlateController {
	private static final Logger LOGGER = Logging.getLogger();
	private static final GlobalPlate globalPlate = Quadropolis.getInstance().getGlobalPlate();

	private final GameModel gameModel;
	private final Stage stage;

	@FXML private Label tourLabel;
	@FXML private VBox vbox;

	public GlobalPlateController(GameModel gameModel, Stage stage) {
		this.gameModel = gameModel;
		this.stage = stage;
	}

	@FXML
	private void initialize() {
		gameModel.currentPlayerProperty().addListener(x -> render());

		tourLabel.textProperty().bind(new SimpleStringProperty("Round ").concat(gameModel.roundProperty().asString()));

		for (int x = 0; x < globalPlate.getWidth(); x++) {
			for (int y = 0; y < globalPlate.getHeight(); y++) {
				final StackPane stackPane = getStackPane(x, y);

				int finalX = x;
				int finalY = y;

				gameModel.selectedArchitectProperty().addListener((a, b, newArchitect) -> {
					if (newArchitect == null) {
						stackPane.setDisable(true); //Pas d'architecte sélectionné = pas de choix possible

						return;
					}

					final boolean canClaimTile = globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromBottom(finalX))
							|| globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromTop(finalX))
							|| globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromLeft(finalY))
							|| globalPlate.canClaimBuilding(newArchitect, PlacedArchitectCoordinates.fromRight(finalY));

//					stackPane.setOpacity(canClaimTile ? 1 : 0.2);
					stackPane.setDisable(!canClaimTile);
				});
			}
		}

		//Pour pouvoir sélectionner un endroit ou poser un architecte
		// Case x = 0 avec 1 <= y <= height - 1
		// Case y = 0 avec 1 <= x <= width - 1
		final int maxWidth = globalPlate.getWidth() + 2;
		final int maxHeight = globalPlate.getHeight() + 2;

		for (int largeX = 0; largeX < maxWidth; largeX++) {
			for (int largeY = 0; largeY < maxHeight; largeY++) {
				final StackPane stackPane = getOuterStackPane(largeX, largeY);

				if (stackPane.getStyleClass().contains("architectSelectableTile")) {
					final PlacedArchitectCoordinates architectCoordinates = getArchitectCoordinates(largeX, largeY);

					final BooleanProperty canClaim = new SimpleBooleanProperty();

					stackPane.setCursor(Cursor.HAND);
					stackPane.disableProperty().bind(gameModel.canSelectArchitectCoordinatesProperty().not().or(canClaim.not()));
					stackPane.hoverProperty().addListener((a, b, isHovering) -> {
						if (isHovering && gameModel.getSelectedArchitect() != null) {
							final ImageView view = new ImageView(gameModel.getSelectedArchitect().asImage());
							view.setPreserveRatio(true);
							view.setFitWidth(100);
							view.setFitHeight(100);

							stackPane.getChildren().setAll(view);
						} else {
							stackPane.getChildren().clear();
						}
					});

					gameModel.selectedArchitectProperty().addListener((a, b, newArchitect) -> {
						if (newArchitect == null) {
							canClaim.set(false); //Pas d'architecte sélectionné = pas de choix possible

							return;
						}

						final boolean canClaimTile = globalPlate.canClaimBuilding(newArchitect, architectCoordinates);

						canClaim.set(canClaimTile);
					});

					stackPane.setOnMouseClicked(e -> onArchitectTileClick(architectCoordinates));
				}
			}
		}

		render();
	}

	private void onArchitectTileClick(PlacedArchitectCoordinates architectCoordinates) {
		LOGGER.debug("Setting placed architected coordinates: {}", architectCoordinates);
		gameModel.setSelectedArchitectCoordinates(architectCoordinates);
	}

	//TODO fonctions séparées pour calculer sur le plateau 5x5 et corriger les cases du plateau global
	@NotNull
	private PlacedArchitectCoordinates getArchitectCoordinates(int x, int y) {
		//Ici on reçoit les coordonnées entre 0x0 et 7x7 (plateau + cases architectes), on doit réduire la coordonnée clé qu'à la transformation
		// Donc on transforme soit le x ou le y, mais pas les deux en même temps
		if (x == 0) {
			return PlacedArchitectCoordinates.fromLeft(Math.max(0, y - 1));
		} else if (x == globalPlate.getWidth() + 1) {
			return PlacedArchitectCoordinates.fromRight(Math.max(0, y - 1));
		} else if (y == 0) {
			return PlacedArchitectCoordinates.fromTop(Math.max(0, x - 1));
		} else if (y == globalPlate.getHeight() + 1) {
			return PlacedArchitectCoordinates.fromBottom(Math.max(0, x - 1));
		} else {
			throw new IllegalArgumentException("x = %d, y = %d".formatted(x, y));
		}
	}

	public void render() {
		try {
			for (int x = 0; x < globalPlate.getWidth(); x++) {
				for (int y = 0; y < globalPlate.getHeight(); y++) {
					final Tile tile = globalPlate.get(x, y);
					final StackPane stackPane = getStackPane(x, y);

					if (tile == null) { //Intended behavior, tile got replace by urbanist and urbanist was moved
						stackPane.getChildren().clear();

						continue;
					}

					stackPane.getChildren().setAll(tile.asGraphic());
				}
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while rendering global plate", e);
		}
	}

	private StackPane getOuterStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y)).getChildren().get(x);
	}

	private StackPane getStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y + 1)).getChildren().get(x + 1);
	}
}
