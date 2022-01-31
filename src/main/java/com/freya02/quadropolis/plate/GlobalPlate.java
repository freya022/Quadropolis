package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.Architect;
import com.freya02.quadropolis.PlacedArchitects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalPlate extends Plate {
	private final ObservableList<PlacedArchitects> placedArchitects = FXCollections.observableArrayList();

	public GlobalPlate(int width, int height) {
		super(width, height);
	}

	public ObservableList<PlacedArchitects> getPlacedArchitects() {
		return placedArchitects;
	}

	public boolean canClaimBuilding(Architect architect, int x, int y) {
		//TODO

		return true;
	}

	public void claimBuilding(Architect architect, int x, int y) {
		if (!canClaimBuilding(architect, x, y)) {
			throw new IllegalStateException("Cannot claim building at %d, %d".formatted(x, y));
		}

		placedArchitects.add(new PlacedArchitects(architect, x, y));

		//TODO donner les resources, retirer la tuile
	}
}
