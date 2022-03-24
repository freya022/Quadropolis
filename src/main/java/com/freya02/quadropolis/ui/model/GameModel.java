package com.freya02.quadropolis.ui.model;

import com.freya02.quadropolis.*;
import javafx.beans.property.*;
import org.slf4j.Logger;

//Sélectionner architecte
//Sélectionner côté et index du côté sur le plateau principal
//Sélectionner la case cible de son propre plateau
public class GameModel {
	private static final Logger LOGGER = Logging.getLogger();

	private final ObjectProperty<Architect> selectedArchitect = new SimpleObjectProperty<>();
	private final ObjectProperty<PlacedArchitectCoordinates> selectedArchitectCoordinates = new SimpleObjectProperty<>();
	private final ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>();
	private final IntegerProperty round = new SimpleIntegerProperty(1);

	private final BooleanProperty canSelectArchitect = new SimpleBooleanProperty();
	private final BooleanProperty canSelectArchitectCoordinates = new SimpleBooleanProperty();
	private final BooleanProperty canSelectTargetTile = new SimpleBooleanProperty();

	private final int maxRounds;

	public GameModel(int maxRounds, int maxPlayers) {
		this.maxRounds = maxRounds;

		Quadropolis.getInstance().initGame(maxPlayers);

		canSelectArchitect.bind(selectedArchitect.isNull()); //Si l'architecte n'est pas sélectionné alors on peut le faire
		canSelectArchitectCoordinates.bind(selectedArchitect.isNotNull().and(selectedArchitectCoordinates.isNull())); //Si l'architecte est sélectionné et que les coordonnées n'ont pas été sélectionnées
		canSelectTargetTile.bind(selectedArchitect.isNotNull().and(selectedArchitectCoordinates.isNotNull())); //Si l'architecte et les coordonnées sont sélectionnées
	}

	public int getRound() {
		return round.get();
	}

	public IntegerProperty roundProperty() {
		return round;
	}

	public BooleanProperty canSelectArchitectProperty() {
		return canSelectArchitect;
	}

	public BooleanProperty canSelectArchitectCoordinatesProperty() {
		return canSelectArchitectCoordinates;
	}

	public BooleanProperty canSelectTargetTileProperty() {
		return canSelectTargetTile;
	}

	public Architect getSelectedArchitect() {
		return selectedArchitect.get();
	}

	public void setSelectedArchitect(Architect selectedArchitect) {
		this.selectedArchitect.set(selectedArchitect);
	}

	public ObjectProperty<Architect> selectedArchitectProperty() {
		return selectedArchitect;
	}

	public Player getCurrentPlayer() {
		return currentPlayer.get();
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer.set(currentPlayer);
	}

	public ObjectProperty<Player> currentPlayerProperty() {
		return currentPlayer;
	}

	public PlacedArchitectCoordinates getSelectedArchitectCoordinates() {
		return selectedArchitectCoordinates.get();
	}

	public void setSelectedArchitectCoordinates(PlacedArchitectCoordinates selectedArchitectCoordinates) {
		this.selectedArchitectCoordinates.set(selectedArchitectCoordinates);
	}

	public ObjectProperty<PlacedArchitectCoordinates> selectedArchitectCoordinatesProperty() {
		return selectedArchitectCoordinates;
	}

	public void nextPlayer() {
		LOGGER.debug("Next player");

		selectedArchitect.set(null);
		selectedArchitectCoordinates.set(null);

		final Quadropolis quadropolis = Quadropolis.getInstance();
		final int currentPlayerNum = getCurrentPlayer().getPlayerNum();
		if (currentPlayerNum == quadropolis.getMaxPlayers()) {
			if (round.get() == maxRounds) {
				LOGGER.info("Jeu terminé");

				System.exit(0);
			}

			round.set(round.get() + 1);

			setCurrentPlayer(quadropolis.getPlayers().get(0));
		} else {
			setCurrentPlayer(quadropolis.getPlayers().get(currentPlayerNum)); //Le numéro du joueur est situé entre 1 et 4 ce qui veut dire qu'il pointe automatiquement vers le prochain
		}
	}
}
