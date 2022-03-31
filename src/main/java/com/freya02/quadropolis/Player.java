package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.PlayerPlate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.IntStream;

public class Player {
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

		this.architects = FXCollections.observableArrayList(retrieveArchitects());
	}

	private List<Architect> retrieveArchitects() {
		return IntStream.range(0, gameMode.getMaxArchitects())
				.mapToObj(reach -> new Architect(this, gameMode, reach))
				.toList();
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
		architects.setAll(retrieveArchitects());
	}
}
