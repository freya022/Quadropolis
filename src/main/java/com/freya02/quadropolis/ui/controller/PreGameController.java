package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.GameMode;
import com.freya02.quadropolis.Logging;
import com.freya02.quadropolis.Quadropolis;
import com.freya02.quadropolis.ui.model.GameModel;
import com.freya02.quadropolis.ui.view.GlobalPlateView;
import com.freya02.quadropolis.ui.view.PlayerPlateView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;


public class PreGameController {
	private static final Logger LOGGER = Logging.getLogger();

	@FXML private Spinner<Integer> playersSpinner;

	private final Stage stage;

	public PreGameController(Stage stage) {
		this.stage = stage;
	}

	@FXML private void initialize() {
		playersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 1));
	}

	@FXML
	private void onPlayAction(ActionEvent event) {
		try {
			final GameModel gameModel = new GameModel(GameMode.CLASSIC, 4, playersSpinner.getValue());

			new GlobalPlateView(gameModel);

			new PlayerPlateView(gameModel);

			gameModel.setCurrentPlayer(Quadropolis.getInstance().getPlayers().get(0));

			stage.close();
		} catch (Exception e) {
			LOGGER.error("An error occurred while starting a game", e);
		}
	}
}
