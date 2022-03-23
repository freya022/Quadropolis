package com.freya02.quadropolis.ui.model;

import com.freya02.quadropolis.Architect;
import com.freya02.quadropolis.PlacedArchitectCoordinates;
import com.freya02.quadropolis.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GameModel {
	private final ObjectProperty<Architect> selectedArchitect = new SimpleObjectProperty<>();
	private final ObjectProperty<PlacedArchitectCoordinates> selectedArchitectCoordinates = new SimpleObjectProperty<>();
	private final ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>();

	public Architect getSelectedArchitect() {
		return selectedArchitect.get();
	}

	public ObjectProperty<Architect> selectedArchitectProperty() {
		return selectedArchitect;
	}

	public void setSelectedArchitect(Architect selectedArchitect) {
		this.selectedArchitect.set(selectedArchitect);
	}

	public Player getCurrentPlayer() {
		return currentPlayer.get();
	}

	public ObjectProperty<Player> currentPlayerProperty() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer.set(currentPlayer);
	}

	public PlacedArchitectCoordinates getSelectedArchitectCoordinates() {
		return selectedArchitectCoordinates.get();
	}

	public ObjectProperty<PlacedArchitectCoordinates> selectedArchitectCoordinatesProperty() {
		return selectedArchitectCoordinates;
	}

	public void setSelectedArchitectCoordinates(PlacedArchitectCoordinates selectedArchitectCoordinates) {
		this.selectedArchitectCoordinates.set(selectedArchitectCoordinates);
	}
}
