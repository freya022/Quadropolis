package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.GlobalPlateController;
import com.freya02.quadropolis.ui.model.GameModel;

import java.io.IOException;

public class GlobalPlateView {
	private final GameModel gameModel;

	public GlobalPlateView(GameModel gameModel) throws IOException {
		this.gameModel = gameModel;

		Utils.loadAndShow("GlobalPlate.fxml", stage -> new GlobalPlateController(gameModel, stage));
	}
}
