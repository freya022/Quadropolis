package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.plate.Tile;
import com.freya02.quadropolis.plate.Urbanist;
import javafx.collections.transformation.FilteredList;
import org.slf4j.Logger;

import java.util.Scanner;

public class TestConsole {
	private static final Logger LOGGER = Logging.getLogger();

	public static void main(String[] args) {
		final Quadropolis quadropolis = Quadropolis.getInstance();
		quadropolis.initGame(GameMode.CLASSIC, 4); //Jeu de 4 joueurs

//		tour1(quadropolis);
//		tour2(quadropolis);

		final Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				for (int x = 0; x < quadropolis.getGlobalPlate().getWidth(); x++) {
					for (int y = 0; y < quadropolis.getGlobalPlate().getHeight(); y++) {
						final Tile tile = quadropolis.getGlobalPlate().get(x, y);

						final String str = switch (tile) {
							case Building ignored -> "B";
							case null -> " ";
							case Urbanist ignored -> "U";
							default -> throw new IllegalArgumentException("Tile: " + tile);
						};

						System.out.print(str + " ");
					}

					System.out.println();
				}

				System.out.print("Nb Player (0-3): ");
				final int nbPlayer = Integer.parseInt(scanner.nextLine());

				final Player player = quadropolis.getPlayers().get(nbPlayer);

				System.out.print("Architect reach (1-4): ");
				final int reach = Integer.parseInt(scanner.nextLine());
				final FilteredList<Architect> architects = player.getArchitects().filtered(a -> a.getReach() == reach);

				if (architects.size() == 0) continue;

				final Architect architect = architects.get(0);

				System.out.print("Placement (top, bottom, left, right): ");
				final String placement = scanner.nextLine();

				final PlacedArchitectCoordinates architectCoordinates = switch (placement) {
					case "top" -> PlacedArchitectCoordinates.fromTop(reach);
					case "bottom" -> PlacedArchitectCoordinates.fromBottom(reach);
					case "left" -> PlacedArchitectCoordinates.fromLeft(reach);
					case "right" -> PlacedArchitectCoordinates.fromRight(reach);
					default -> null;
				};

				if (architectCoordinates == null) continue;

				System.out.print("Target X: ");
				final int targetX = Integer.parseInt(scanner.nextLine());
				System.out.print("Target Y: ");
				final int targetY = Integer.parseInt(scanner.nextLine());
				final TileCoordinates targetCoordinates = new TileCoordinates(targetX, targetY);

				final Building building = quadropolis.getGlobalPlate().claimBuilding(player,
						architect,
						architectCoordinates,
						targetCoordinates);

				building.activate();
			} catch (Exception e) {
				LOGGER.error("An error occurred while testing", e);
			}
		}
	}

	private static void tour1(Quadropolis quadropolis) {
		/*
		 * Le joueur 1 prend la case 1x1, donc place son architecte avec un reach de 1, en haut en X = 1, ou à gauche en Y = 1, et la place dans sa grille à 0x0
		 * On retire alors l'architecte du joueur
		 *
		 * Le joueur active son batiment dans la foulée et reçoit normalement 2 maisons ainsi que 2 barils
		 */
		final Player player = quadropolis.getPlayers().get(0);

		final Architect architect = player.getArchitects().filtered(a -> a.getReach() == 1).get(0);
		final Building building = quadropolis.getGlobalPlate().claimBuilding(player,
				architect,
				PlacedArchitectCoordinates.fromTop(1),
				new TileCoordinates(0, 0));

		building.activate();
	}

	private static void tour2(Quadropolis quadropolis) {
		/*
		 * Le joueur 2 prend la case 2x1, donc place son architecte avec un reach de 2, à gauche en Y = 1, et la place dans sa grille à 0x0
		 * On retire alors l'architecte du joueur, et on place l'urbaniste
		 *
		 * Le joueur active son batiment dans la foulée et reçoit normalement 2 maisons ainsi que 2 barils
		 */
		final Player player = quadropolis.getPlayers().get(1);

		final Architect architect = player.getArchitects().filtered(a -> a.getReach() == 2).get(0);
		final Building building = quadropolis.getGlobalPlate().claimBuilding(player,
				architect,
				PlacedArchitectCoordinates.fromLeft(2),
				new TileCoordinates(0, 0));

		building.activate();
	}
}
