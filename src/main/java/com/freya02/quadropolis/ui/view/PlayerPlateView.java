package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.Logging;
import com.freya02.quadropolis.Player;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.PlayerPlateController;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerPlateView {
	private static final Logger LOGGER = Logging.getLogger();

	private final GameModel gameModel;

	private final Map<Player, PlayerPlateScene> playerMap = new HashMap<>();

	private final Stage stage;

	public PlayerPlateView(GameModel gameModel) {
		this.gameModel = gameModel;

		gameModel.currentPlayerProperty().addListener((a, b, newPlayer) -> onPlayerChange(newPlayer));

		stage = Utils.newStage();

		stage.show();
	}

	private void onPlayerChange(Player player) {
		LOGGER.debug("New player: {}", player);

		final PlayerPlateScene playerPlateScene = playerMap.computeIfAbsent(player, x -> {
			try {
				final PlayerPlateController controller = new PlayerPlateController(gameModel, player);
				final Scene scene = Utils.loadScene("PlayerPlate.fxml", controller);

				return new PlayerPlateScene(scene, controller);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		stage.setScene(playerPlateScene.scene());
	}

	private record PlayerPlateScene(Scene scene, PlayerPlateController controller) {}
}
