package com.freya02.quadropolis;

import com.freya02.quadropolis.ui.view.GlobalPlateView;

import java.io.IOException;

public class GameManager {
	public GameManager(int rounds, int players) throws IOException {
		new GlobalPlateView();

		Quadropolis.getInstance().initGame(players);
	}
}
