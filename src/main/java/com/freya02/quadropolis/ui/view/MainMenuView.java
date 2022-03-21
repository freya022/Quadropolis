package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.MainMenuController;

import java.io.IOException;

public class MainMenuView {
	public MainMenuView() throws IOException {
		Utils.loadAndShow("MainMenu.fxml", MainMenuController::new);
	}
}
