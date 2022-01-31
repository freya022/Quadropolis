package com.freya02.quadropolis;

public class TestConsole {
	public static void main(String[] args) {
		final Quadropolis quadropolis = Quadropolis.getInstance();
		quadropolis.initGame(4);

		/*
		 * Jeu de 4 joueurs
		 * Le joueur 1 prend la case 1x1
		 *
		 */

		final Player player = quadropolis.getPlayers().get(0);

		quadropolis.getGlobalPlate().claimBuilding(player.getArchitects().filtered(a -> a.getReach() == 1).get(0), 1, 1);

		System.out.println();
	}
}
