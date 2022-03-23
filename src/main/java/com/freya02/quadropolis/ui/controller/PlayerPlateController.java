package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.*;
import com.freya02.quadropolis.plate.GlobalPlate;
import com.freya02.quadropolis.plate.PlayerPlate;
import com.freya02.quadropolis.plate.Tile;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerPlateController {
	private static final Logger LOGGER = Logging.getLogger();

	private final GlobalPlate globalPlate = Quadropolis.getInstance().getGlobalPlate();

	private final GameModel gameModel;
	private final Player player;

	@FXML private Label titleLabel;
	@FXML private VBox vbox;
	@FXML private HBox architectsBox;

	public PlayerPlateController(GameModel gameModel, Player player) {
		this.gameModel = gameModel;
		this.player = player;
	}

	@FXML
	private void initialize() {
		updateTitle();

		gameModel.canSelectArchitectProperty().addListener(e -> updateTitle());
		gameModel.canSelectArchitectCoordinatesProperty().addListener(e -> updateTitle());
		gameModel.canSelectTargetTileProperty().addListener(e -> updateTitle());

		player.getArchitects().addListener((InvalidationListener) observable -> updateArchitects());
		player.getPlate().getTiles().addListener((InvalidationListener) observable -> updatePlate());

		updateArchitects();
		updatePlate();

		final PlayerPlate playerPlate = player.getPlate();
		for (int x = 0; x < playerPlate.getWidth(); x++) {
			for (int y = 0; y < playerPlate.getHeight(); y++) {
				final StackPane stackPane = getStackPane(x, y);

				int finalX = x;
				int finalY = y;

				stackPane.disableProperty().bind(gameModel.canSelectTargetTileProperty().not());
				stackPane.setOnMouseClicked(e -> onTileClick(finalX, finalY));
			}
		}
	}

	private void updateTitle() {
		final String step;
		if (gameModel.canSelectArchitectProperty().get()) {
			step = "Sélectionnez un architecte";
		} else if (gameModel.canSelectArchitectCoordinatesProperty().get()) {
			step = "Positionnez l'architecte";
		} else if (gameModel.canSelectTargetTileProperty().get()) {
			step = "Sélectionnez votre case cible";
		} else {
			return;
		}

		titleLabel.setText("Plateau du joueur " + player.getPlayerNum() + " - " + step);
	}

	private void onTileClick(int x, int y) {
		LOGGER.debug("Click player tile");

		globalPlate.claimBuilding(gameModel.getCurrentPlayer(),
				gameModel.getSelectedArchitect(),
				gameModel.getSelectedArchitectCoordinates(),
				new TileCoordinates(x, y));
	}

	private void updateArchitects() {
		List<Node> nodes = new ArrayList<>();

		for (Architect architect : player.getArchitects()) {
			final ImageView view = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Square_-_black_simple.svg/800px-Square_-_black_simple.svg.png");
			view.setFitHeight(100);
			view.setFitWidth(100);
			view.setCursor(Cursor.HAND);

			view.disableProperty().bind(gameModel.canSelectArchitectProperty().not());
			view.setOnMouseClicked(e -> useArchitect(architect));

			nodes.add(view);
		}

		architectsBox.getChildren().setAll(nodes);
	}

	private void useArchitect(Architect architect) {
		LOGGER.debug("Chose player architect");

		gameModel.setSelectedArchitect(architect);
	}

	public void updatePlate() {
		try {
			final PlayerPlate plate = player.getPlate();

			for (int x = 0; x < plate.getWidth(); x++) {
				for (int y = 0; y < plate.getHeight(); y++) {
					final Tile tile = plate.get(x, y);
					if (tile == null) continue;

					final StackPane stackPane = getStackPane(x, y);

					stackPane.getChildren().setAll(tile.asGraphic());
				}
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while rendering player plate", e);
		}
	}

	private StackPane getStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y + 1)).getChildren().get(x + 1);
	}
}
