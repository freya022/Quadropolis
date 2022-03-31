package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.Logging;
import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.ScoreBoardController;
import org.slf4j.Logger;

import java.io.IOException;

public class ScoreBoardView {
	private static final Logger LOGGER = Logging.getLogger();

	public ScoreBoardView() {
		try {
			Utils.loadAndShow("ScoreBoard.fxml", s -> new ScoreBoardController());
		} catch (IOException e) {
			LOGGER.error("An error occurred while showing scores", e);
		}
	}
}
