package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.PlayerPlate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private static final ObservableList<Architect> sharedArchitects = FXCollections.observableArrayList();

	private final Resources resources = new Resources();

	private final IntegerProperty score = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<Architect> architects;

	private final PlayerPlate plate;
	private final GameMode gameMode;
	private final int playerNum;

	private final IntegerProperty turn = new SimpleIntegerProperty(1);

	public Player(GameMode gameMode, int playerNum) {
		this.gameMode = gameMode;
		this.playerNum = playerNum;
		this.plate = new PlayerPlate(gameMode);

		if (gameMode == GameMode.CLASSIC) {
			this.architects = FXCollections.observableArrayList(retrieveArchitects());
		} else {
			sharedArchitects.setAll(retrieveArchitects());

			this.architects = sharedArchitects;
		}
	}

	private List<Architect> retrieveArchitects() {
		final List<Architect> list = new ArrayList<>();

		if (gameMode == GameMode.CLASSIC) {
			for (int reach = 0; reach < gameMode.getMaxArchitects(); reach++) {
				list.add(new Architect(this, gameMode, reach));
			}
		} else {
			for (int reach = 0; reach < gameMode.getMaxArchitects(); reach++) {
				for (int player = 0; player < Quadropolis.getInstance().getMaxPlayers(); player++) {
					list.add(new Architect(this, gameMode, reach));
				}
			}
		}

		return list;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public int getScore() {
		return score.get();
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObservableList<Architect> getArchitects() {
		return architects;
	}

	public Resources getResources() {
		return resources;
	}

	public PlayerPlate getPlate() {
		return plate;
	}

	public int getTurn() {
		return turn.get();
	}

	public IntegerProperty turnProperty() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn.set(turn);
	}

	@Override
	public String toString() {
		return "Player{playerNum=%d}".formatted(playerNum);
	}

	public void regenArchitects() {
		architects.clear();
		architects.addAll(retrieveArchitects());
	}
}
