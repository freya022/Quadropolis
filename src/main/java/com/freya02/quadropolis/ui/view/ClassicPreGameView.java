package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.LoadedStage;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.ClassicPreGameController;

import java.io.IOException;

public class ClassicPreGameView {
	public ClassicPreGameView() throws IOException {
		final LoadedStage<ClassicPreGameController> loadedStage = Utils.loadAndShow("ClassicPreGame.fxml", ClassicPreGameController::new);
	}
}
