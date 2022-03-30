package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.LoadedStage;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.ExpertPreGameController;

import java.io.IOException;

public class ExpertPreGameView {
	public ExpertPreGameView() throws IOException {
		final LoadedStage<ExpertPreGameController> loadedStage = Utils.loadAndShow("ExpertPreGame.fxml", ExpertPreGameController::new);
	}
}
