package com.freya02.quadropolis.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public record LoadedStage<T>(Stage stage, Scene scene, T controller) {}
