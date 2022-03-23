package com.freya02.quadropolis;

import com.freya02.quadropolis.ui.view.MainMenuView;
import javafx.application.Platform;
import org.slf4j.Logger;

public class Main {
	private static final Logger LOGGER = Logging.getLogger();

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOGGER.error("An uncaught exception occurred", e));

		Platform.startup(() -> {
			try {
				new MainMenuView();
			} catch (Exception e) {
				LOGGER.error("An error occurred while starting up", e);
			}
		});
	}
}
