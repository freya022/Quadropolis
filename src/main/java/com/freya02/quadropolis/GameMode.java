package com.freya02.quadropolis;

public enum GameMode {
	CLASSIC(4, 4, 4, 4),
	EXPERT(5, 5, 5, 5);

	private final int playerPlateWidth;
	private final int playerPlateHeight;
	private final int maxRounds;
	private final int maxArchitects;

	GameMode(int playerPlateWidth, int playerPlateHeight, int maxRounds, int maxArchitects) {
		this.playerPlateWidth = playerPlateWidth;
		this.playerPlateHeight = playerPlateHeight;
		this.maxRounds = maxRounds;
		this.maxArchitects = maxArchitects;
	}

	public int getPlayerPlateWidth() {
		return playerPlateWidth;
	}

	public int getPlayerPlateHeight() {
		return playerPlateHeight;
	}

	public int getMaxRounds() {
		return maxRounds;
	}

	public int getMaxArchitects() {
		return maxArchitects;
	}
}
