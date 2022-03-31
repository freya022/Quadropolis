package com.freya02.quadropolis.ui.model;

import com.freya02.quadropolis.*;
import com.freya02.quadropolis.ui.view.ScoreBoardView;
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

	private final BooleanProperty waitingNextTurn = new SimpleBooleanProperty();

	private final BooleanProperty canSelectArchitect = new SimpleBooleanProperty();
	private final BooleanProperty canSelectArchitectCoordinates = new SimpleBooleanProperty();
	private final BooleanProperty canSelectTargetTile = new SimpleBooleanProperty();

	private final BooleanProperty finished = new SimpleBooleanProperty();

	private final GameMode gameMode;

	public GameModel(GameMode gameMode, int maxPlayers) {
		this.gameMode = gameMode;

		Quadropolis.getInstance().initGame(gameMode, maxPlayers);

		canSelectArchitect.bind(waitingNextTurn.not().and(selectedArchitectCoordinates.isNull())); //Si l'architecte n'est pas encore placé alors on peut le faire
		canSelectArchitectCoordinates.bind(waitingNextTurn.not().and(selectedArchitect.isNotNull().and(selectedArchitectCoordinates.isNull()))); //Si l'architecte est sélectionné et que les coordonnées n'ont pas été sélectionnées
		canSelectTargetTile.bind(waitingNextTurn.not().and(selectedArchitect.isNotNull().and(selectedArchitectCoordinates.isNotNull()))); //Si l'architecte et les coordonnées sont sélectionnées
	}

	public BooleanProperty finishedProperty() {
		return finished;
	}

	public GameMode getGameMode() {
		return gameMode;
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

	public boolean isWaitingNextTurn() {
		return waitingNextTurn.get();
	}

	public BooleanProperty waitingNextTurnProperty() {
		return waitingNextTurn;
	}

	public void nextPlayer() {
		LOGGER.debug("Next player");

		getCurrentPlayer().setTurn(getCurrentPlayer().getTurn() + 1);

		final Quadropolis quadropolis = Quadropolis.getInstance();
		final int currentPlayerNum = getCurrentPlayer().getPlayerNum();

		if (currentPlayerNum == quadropolis.getMaxPlayers()) {
			if (getCurrentPlayer().getTurn() == 5) { //Si c'est le dernier joueur qui à joué et que c'est son dernier tour alors on fait le round suivant
				if (round.get() == gameMode.getMaxRounds()) {
					LOGGER.info("Jeu terminé");

					finished.set(true);

					new ScoreBoardView();
				}

				round.set(round.get() + 1);

				for (Player player : quadropolis.getPlayers()) {
					player.regenArchitects();

					player.setTurn(1);

					quadropolis.getGlobalPlate().getPlacedArchitects().clear();
				}
			}

			setCurrentPlayer(quadropolis.getPlayers().get(0));
		} else {
			setCurrentPlayer(quadropolis.getPlayers().get(currentPlayerNum)); //Le numéro du joueur est situé entre 1 et 4 ce qui veut dire qu'il pointe automatiquement vers le prochain
		}

		waitingNextTurn.set(false);
	}

	public void prepareNextTurn() {
		selectedArchitect.set(null);
		selectedArchitectCoordinates.set(null);

		waitingNextTurn.set(true);
	}
}
