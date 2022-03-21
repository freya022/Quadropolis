package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.ui.view.PreGameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
	private final Stage stage;

	public MainMenuController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void onExpertModeAction(ActionEvent event) {

	}

	@FXML
	private void onNormalModeAction(ActionEvent event) {
		try {
			new PreGameView();

			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onRulesAction(ActionEvent event) {

	}
}
