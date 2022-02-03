package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.TileCoordinates;

public class PlayerPlate extends Plate {
	public PlayerPlate(int width, int height) {
		super(width, height);
	}

	public void addBuilding(TileCoordinates targetCoordinates, Building building) {
		final Tile currentTile = get(targetCoordinates);

		//On vérifie si leur joueur à déjà un bâtiment a ces coordonnées
		//TODO on doit vérifier si c'est le même type de batiment avec les mêmes resources ?
		if (currentTile == null) {
			set(targetCoordinates, building);
		} else {
			if (currentTile instanceof Building currentBuilding) {
				currentBuilding.stack();
			} else {
				throw new IllegalStateException("Tried to stack a building at %dx%d but it was an %s".formatted(targetCoordinates.x(), targetCoordinates.y(), currentTile));
			}
		}
	}
}
