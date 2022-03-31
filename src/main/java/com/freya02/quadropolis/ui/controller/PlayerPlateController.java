package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.*;
import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.plate.GlobalPlate;
import com.freya02.quadropolis.plate.PlayerPlate;
import com.freya02.quadropolis.plate.Tile;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerPlateController {
	private static final Logger LOGGER = Logging.getLogger();

	private final GlobalPlate globalPlate = Quadropolis.getInstance().getGlobalPlate();

	private final GameModel gameModel;
	private final Player player;

	@FXML private Label titleLabel, turnLabel, stepLabel, houseLabel, barrelLabel;
	@FXML private VBox vbox;
	@FXML private TilePane architectsBox;
	@FXML private Button nextPlayerButton;

	public PlayerPlateController(GameModel gameModel, Player player) {
		this.gameModel = gameModel;
		this.player = player;
	}

	@FXML
	private void initialize() {
		updateTitle();

		turnLabel.textProperty().bind(new SimpleStringProperty("Tour ").concat(player.turnProperty()));

		nextPlayerButton.disableProperty().bind(gameModel.waitingNextTurnProperty().not());

		gameModel.canSelectArchitectProperty().addListener(e -> updateTitle());
		gameModel.canSelectArchitectCoordinatesProperty().addListener(e -> updateTitle());
		gameModel.canSelectTargetTileProperty().addListener(e -> updateTitle());

		player.getArchitects().addListener((InvalidationListener) observable -> updateArchitects());
		player.getPlate().getTiles().addListener((InvalidationListener) observable -> updatePlate());

		houseLabel.textProperty().bind(player.getResources().housesProperty().asString());
		barrelLabel.textProperty().bind(player.getResources().barrelsProperty().asString());

		updateArchitects();
		updatePlate();

		vbox.getChildren().clear();

		final PlayerPlate playerPlate = player.getPlate();
		for (int y = 0; y < playerPlate.getHeight(); y++) {
			final HBox hbox = new HBox(10);

			for (int x = 0; x < playerPlate.getWidth(); x++) {
				final StackPane stackPane = new StackPane();

				stackPane.setCursor(Cursor.HAND);
				stackPane.setPrefSize(100, 100);
				stackPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				stackPane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				stackPane.getStyleClass().add("visibleTile");

				int finalX = x;
				int finalY = y;

				stackPane.setOnMouseClicked(e -> onTileClick(finalX, finalY));

				hbox.getChildren().add(stackPane);
			}

			vbox.getChildren().add(hbox);
		}
	}

	private void updateTitle() {
		final String step;
		if (gameModel.isWaitingNextTurn()) {
			step = "Attente du prochain tour";
		} else if (gameModel.canSelectTargetTileProperty().get()) {
			step = "Sélectionnez votre case cible";
		} else if (gameModel.canSelectArchitectCoordinatesProperty().get()) {
			step = "Positionnez l'architecte";
		} else if (gameModel.canSelectArchitectProperty().get()) {
			step = "Sélectionnez un architecte";
		} else {
			return;
		}

		titleLabel.setText("Plateau du joueur " + player.getPlayerNum());
		stepLabel.setText(step);
	}

	private void onTileClick(int x, int y) {
		if (gameModel.canSelectTargetTileProperty().get()) {
			LOGGER.debug("Click player tile (final step)");
			LOGGER.trace("Current player: {}", gameModel.getCurrentPlayer());
			LOGGER.trace("Selected architect reach: {}", gameModel.getSelectedArchitect().getReach());
			LOGGER.trace("Selected architect coordinates: {}", gameModel.getSelectedArchitectCoordinates());
			LOGGER.trace("Target coordinates: {}", new TileCoordinates(x, y));

			globalPlate.claimBuilding(gameModel.getCurrentPlayer(),
					gameModel.getSelectedArchitect(),
					gameModel.getSelectedArchitectCoordinates(),
					new TileCoordinates(x, y));

			gameModel.prepareNextTurn();
		}
	}

	private void onBuildingClick(Building building) {
		if (building.canBeActivated()) {
			building.activate();
		}
	}

	@FXML
	private void onNextPlayerAction(ActionEvent event) {
		//Next player
		gameModel.nextPlayer();
	}

	private void updateArchitects() {
		List<Node> nodes = new ArrayList<>();

		for (Architect architect : player.getArchitects().stream().distinct().collect(Collectors.toList())) { //En mode expert on peut utiliser des architectes plusieurs fois
			final ImageView view = new ImageView(architect.asImage());
			view.setFitHeight(100);
			view.setFitWidth(100);
			view.setPreserveRatio(true);
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

	private void updatePlate() {
		try {
			final PlayerPlate plate = player.getPlate();

			for (int x = 0; x < plate.getWidth(); x++) {
				for (int y = 0; y < plate.getHeight(); y++) {
					final Tile tile = plate.get(x, y);
					if (!(tile instanceof Building)) continue;

					final Building building = (Building) tile;

					final StackPane stackPane = getStackPane(x, y);

					final SimpleBooleanProperty canBeActivatedProp = new SimpleBooleanProperty(building.canBeActivated());
					player.getResources().housesProperty().addListener(o -> canBeActivatedProp.set(building.canBeActivated()));
					player.getResources().barrelsProperty().addListener(o -> canBeActivatedProp.set(building.canBeActivated()));

					stackPane.disableProperty().bind(gameModel.canSelectTargetTileProperty().not().and(canBeActivatedProp.not()));

					final Node node = tile.asGraphic();
					node.setOnMouseClicked(e -> onBuildingClick(building));

					stackPane.getChildren().setAll(node);
				}
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while rendering player plate", e);
		}
	}

	private StackPane getStackPane(int x, int y) {
		//Offset by 1 because of hidden tiles on the UI to accommodate architects

		return (StackPane) ((HBox) vbox.getChildren().get(y)).getChildren().get(x);
	}
}
