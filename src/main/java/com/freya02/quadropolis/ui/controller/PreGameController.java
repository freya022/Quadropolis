package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;


public class PreGameController {
	@FXML private Spinner<Integer> roundSpinner;
	@FXML private Spinner<Integer> playersSpinner;

	private final Stage stage;

	public PreGameController(Stage stage) {
		this.stage = stage;
	}

	@FXML private void initialize() {
		roundSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 1));
		playersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 1));
	}

	@FXML
	private void onPlayAction(ActionEvent event) {
		try {
			new GameManager(roundSpinner.getValue(), playersSpinner.getValue());

			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}