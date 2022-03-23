package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.Player;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.PlayerPlateController;
import com.freya02.quadropolis.ui.model.GameModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerPlateView {
	private final GameModel gameModel;

	private final Map<Player, PlayerPlateScene> playerMap = new HashMap<>();

	private final Stage stage;

	public PlayerPlateView(GameModel gameModel) throws IOException {
		this.gameModel = gameModel;

		stage = Utils.newStage();

		stage.show();
	}

	public void setPlayer(Player player) throws IOException {
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
