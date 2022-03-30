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
	private int maxPlayers;

	private Quadropolis() {}

	public void initGame(GameMode gameMode, int maxPlayers) {
		this.maxPlayers = maxPlayers;

		gameResources = new Resources(Integer.MAX_VALUE, Integer.MAX_VALUE);
		globalPlate = new GlobalPlate();
		players = FXCollections.observableArrayList(
				IntStream.rangeClosed(1, maxPlayers)
						.mapToObj(playerNum -> new Player(gameMode, playerNum))
						.toList()
		);
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public static Quadropolis getInstance() {
		return INSTANCE;
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
