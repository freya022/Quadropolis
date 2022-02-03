package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.ThreadLocalRandom;

public class GlobalPlate extends Plate {
	private final ObservableList<PlacedArchitect> placedArchitects = FXCollections.observableArrayList();

	public GlobalPlate(int width, int height) {
		super(width, height);

		final ThreadLocalRandom random = ThreadLocalRandom.current();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final BuildingType[] buildingTypes = BuildingType.values();

				set(x, y, new Building(buildingTypes[random.nextInt(buildingTypes.length)],
						new Resources(1, 1),
						true,
						new Resources(2, 2)));
			}
		}
	}

	public ObservableList<PlacedArchitect> getPlacedArchitects() {
		return placedArchitects;
	}

	public boolean canClaimBuilding(Architect architect, PlacedArchitectCoordinates architectCoordinates) {
		// Si un architect existe sur ces coordonnées
		if (placedArchitects.stream().anyMatch(p -> p.getCoordinates().equals(architectCoordinates))) {
			return false;
		} else {
			// On cherche à savoir si un urbaniste nous empêche de claim une case
			// L'urbaniste représente juste la coordonnée X et la coordonnée Y à éviter
			// On calcule la coordonnée de la case ciblée par l'architecte et on vérifie si elle partage une coordonnée avec l'urbaniste

			final TileCoordinates tileCoordinates = architectCoordinates.toTileCoordinates(this, architect);

			for (int x = 0; x < getWidth(); x++) {
				for (int y = 0; y < getHeight(); y++) {
					final Tile tile = get(x, y);

					if (tile instanceof Urbanist) {
						if (x == tileCoordinates.x() || y == tileCoordinates.y()) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	public Building claimBuilding(Player player, Architect architect, PlacedArchitectCoordinates architectCoordinates, TileCoordinates targetCoordinates) {
		if (!canClaimBuilding(architect, architectCoordinates)) {
			throw new IllegalStateException("Cannot claim building by placing an architect of reach %d at **architect** coordinates: %d, %d".formatted(architect.getReach(),
					architectCoordinates.x(),
					architectCoordinates.y()));
		}

		placedArchitects.add(new PlacedArchitect(architect, architectCoordinates));

		final TileCoordinates tileCoordinates = architectCoordinates.toTileCoordinates(this, architect);

		final Tile tile = this.set(tileCoordinates, new Urbanist());

		if (tile instanceof Building building) {
			player.getPlate().addBuilding(targetCoordinates, building);

			building.setOwner(player);

			building.getRevenue().copyTo(player.getResources());

			return building;
		} else {
			throw new IllegalStateException("Claimed a tile that wasn't a Building");
		}
	}
}
