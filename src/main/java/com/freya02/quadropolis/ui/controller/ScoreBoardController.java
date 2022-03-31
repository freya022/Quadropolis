package com.freya02.quadropolis.ui.controller;

import com.freya02.quadropolis.Player;
import com.freya02.quadropolis.Quadropolis;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

public class ScoreBoardController {
	@FXML
	private VBox businessBox, factoryBox, gardenBox, houseBox, playerBox, portBox, remainingGasBox, remainingPeopleBox, scoreBox, townhallBox;

	@FXML
	private void initialize() {
		for (Player player : Quadropolis.getInstance().getPlayers()) {
			playerBox.getChildren().add(getLabel(player.getPlayerNum()));

			businessBox.getChildren().add(getLabel(player.getBusinessScore()));
			factoryBox.getChildren().add(getLabel(player.getFactoryScore()));
			gardenBox.getChildren().add(getLabel(player.getGardenScore()));
			houseBox.getChildren().add(getLabel(player.getHouseScore()));
			portBox.getChildren().add(getLabel(player.getPortScore()));

			remainingGasBox.getChildren().add(getLabel(-player.getResources().getBarrels()));
			remainingPeopleBox.getChildren().add(getLabel(-player.getResources().getHouses()));

			townhallBox.getChildren().add(getLabel(player.getTownHallScore()));
		}
	}

	@NotNull
	private Label getLabel(int number) {
		final Label label = new Label(String.valueOf(number));
		label.setAlignment(Pos.CENTER);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setMinHeight(50);
		label.setFont(Font.font(36));

		return label;
	}
}
