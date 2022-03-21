package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.LoadedStage;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.PreGameController;

import java.io.IOException;

public class PreGameView {
	public PreGameView() throws IOException {
		final LoadedStage<PreGameController> loadedStage = Utils.loadAndShow("PreGame.fxml", PreGameController::new);
	}
}
