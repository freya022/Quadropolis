package com.freya02.quadropolis.ui.model;

import com.freya02.quadropolis.Architect;
import com.freya02.quadropolis.PlacedArchitectCoordinates;
import com.freya02.quadropolis.Player;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

//Sélectionner architecte
//Sélectionner côté et index du côté sur le plateau principal
//Sélectionner la case cible de son propre plateau
public class GameModel {
	private final ObjectProperty<Architect> selectedArchitect = new SimpleObjectProperty<>();
	private final ObjectProperty<PlacedArchitectCoordinates> selectedArchitectCoordinates = new SimpleObjectProperty<>();
	private final ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>();

	private final BooleanProperty canSelectArchitect = new SimpleBooleanProperty();
	private final BooleanProperty canSelectArchitectCoordinates = new SimpleBooleanProperty();
	private final BooleanProperty canSelectTargetTile = new SimpleBooleanProperty();

	public GameModel() {
		canSelectArchitect.bind(selectedArchitect.isNull()); //Si l'architecte n'est pas sélectionné alors on peut le faire
		canSelectArchitectCoordinates.bind(selectedArchitect.isNotNull().and(selectedArchitectCoordinates.isNull())); //Si l'architecte est sélectionné et que les coordonnées n'ont pas été sélectionnées
		canSelectTargetTile.bind(selectedArchitect.isNotNull().and(selectedArchitectCoordinates.isNotNull())); //Si l'architecte et les coordonnées sont sélectionnées
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
}
