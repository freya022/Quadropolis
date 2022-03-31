package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.LoadedStage;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.GlobalPlateController;
import com.freya02.quadropolis.ui.model.GameModel;

import java.io.IOException;

public class GlobalPlateView {
	public GlobalPlateView(GameModel gameModel) throws IOException {
		final LoadedStage<GlobalPlateController> loadedStage = Utils.loadAndShow("GlobalPlate.fxml", stage -> new GlobalPlateController(gameModel, stage));

		gameModel.finishedProperty().addListener((a, b, isFinished) -> {
			if (isFinished) loadedStage.stage().close();
		});
	}
}
