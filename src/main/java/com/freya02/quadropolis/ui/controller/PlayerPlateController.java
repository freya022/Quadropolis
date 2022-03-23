package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.Architect;
import com.freya02.quadropolis.Player;
import com.freya02.quadropolis.Quadropolis;
import com.freya02.quadropolis.TileCoordinates;
import com.freya02.quadropolis.plate.GlobalPlate;
import com.freya02.quadropolis.plate.PlayerPlate;
import com.freya02.quadropolis.plate.Tile;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerPlateController {
	private final GlobalPlate globalPlate = Quadropolis.getInstance().getGlobalPlate();

	private final GameModel gameModel;
	private final Player player;

	@FXML private VBox vbox;
	@FXML private HBox architectsBox;

	public PlayerPlateController(GameModel gameModel, Player player) {
		this.gameModel = gameModel;
		this.player = player;
	}

	@FXML
	private void initialize() {
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
				stackPane.setOnMouseClicked(e -> onTileClick(finalX, finalY));
			}
		}
	}

	private void onTileClick(int x, int y) {
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

			view.setOnMouseClicked(e -> useArchitect(architect));

			nodes.add(view);
		}

		architectsBox.getChildren().setAll(nodes);
	}

	private void useArchitect(Architect architect) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StackPane getStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y + 1)).getChildren().get(x + 1);
	}
}
