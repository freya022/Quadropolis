package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.GlobalPlate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.IntStream;

public class Quadropolis {
	private static final Quadropolis INSTANCE = new Quadropolis();

	private Resources gameResources;
	private GlobalPlate globalPlate;
	private ObservableList<Player> players;
	private Player currentPlayer;

	private Quadropolis() {}

	public void initGame(int numPlayers) {
		gameResources = new Resources(Integer.MAX_VALUE, Integer.MAX_VALUE);
		globalPlate = new GlobalPlate();
		players = FXCollections.observableArrayList(
				IntStream.rangeClosed(1, numPlayers)
						.mapToObj(Player::new)
						.toList()
		);

		currentPlayer = players.get(0);
	}

	public static Quadropolis getInstance() {
		return INSTANCE;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public GlobalPlate getGlobalPlate() {
		return globalPlate;
	}

	public Resources getGameResources() {
		return gameResources;
	}

	public ObservableList<Player> getPlayers() {
		return players;
	}
}
