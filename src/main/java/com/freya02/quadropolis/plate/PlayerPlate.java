package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.GameMode;
import com.freya02.quadropolis.TileCoordinates;

public class PlayerPlate extends Plate {
	public PlayerPlate(GameMode gameMode) {
		super(gameMode.getPlayerPlateWidth(), gameMode.getPlayerPlateHeight());
	}

	public void addBuilding(TileCoordinates targetCoordinates, Building building) {
		final Tile currentTile = get(targetCoordinates);

		//On vérifie si leur joueur à déjà un bâtiment a ces coordonnées
		if (currentTile == null) {
			set(targetCoordinates, building);
		} else {
			if (currentTile instanceof Building) {
				final Building currentBuilding = (Building) currentTile;

				currentBuilding.stack();
			} else {
				throw new IllegalStateException(String.format("Tried to stack a building at %dx%d but it was an %s", targetCoordinates.x(), targetCoordinates.y(), currentTile));
			}
		}
	}
}
