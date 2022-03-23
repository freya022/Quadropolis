package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.ui.view.PreGameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI("https://cdn.1j1ju.com/medias/84/80/76-quadropolis-regle.pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onSourceAction(ActionEvent event) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/freya022/Quadropolis/blob/master/Source"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

}
