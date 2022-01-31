package com.freya02.quadropolis;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.IntStream;

public class Player {
	private final Resources resources = new Resources();
	private final IntegerProperty score = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final ObservableList<Architect> architects = FXCollections.observableArrayList(
			IntStream.rangeClosed(1, 4)
					.mapToObj(Architect::new)
					.toList()
	);

	public int getScore() {
		return score.get();
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObservableList<Architect> getArchitects() {
		return architects;
	}

	public Resources getResources() {
		return resources;
	}
}
