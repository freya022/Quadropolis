package com.freya02.quadropolis.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public final class LoadedStage<T> {
	private final Stage stage;
	private final Scene scene;
	private final T controller;

	LoadedStage(Stage stage, Scene scene, T controller) {
		this.stage = stage;
		this.scene = scene;
		this.controller = controller;
	}

	public Stage stage() {return stage;}

	public Scene scene() {return scene;}

	public T controller() {return controller;}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (LoadedStage<?>) obj;
		return Objects.equals(this.stage, that.stage) &&
				Objects.equals(this.scene, that.scene) &&
				Objects.equals(this.controller, that.controller);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stage, scene, controller);
	}

	@Override
	public String toString() {
		return "LoadedStage[" +
				"stage=" + stage + ", " +
				"scene=" + scene + ", " +
				"controller=" + controller + ']';
	}
}
