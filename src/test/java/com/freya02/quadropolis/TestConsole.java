package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.Building;

public class TestConsole {
	public static void main(String[] args) {
		final Quadropolis quadropolis = Quadropolis.getInstance();
		quadropolis.initGame(4);

		/*
		 * Jeu de 4 joueurs
		 * Le joueur 1 prend la case 1x1, donc place son architecte avec un reach de 1, en haut en X = 1, ou à gauche en Y = 1, et la place dans sa grille à 0x0
		 * On retire alors l'architecte du joueur
		 *
		 * Le joueur active son batiment dans la foulée et reçoit normalement 2 maisons ainsi que 2 barils
		 */

		final Player player = quadropolis.getPlayers().get(0);

		final Architect architect = player.getArchitects().filtered(a -> a.getReach() == 1).get(0);
		final Building building = quadropolis.getGlobalPlate().claimBuilding(player, architect, PlacedArchitectCoordinates.fromTop(1), new TileCoordinates(0, 0));

		player.getArchitects().remove(architect);

		building.activate();

		System.out.println();
	}
}
