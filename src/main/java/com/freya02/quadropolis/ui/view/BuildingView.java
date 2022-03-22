package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.ui.Utils;
import javafx.scene.Node;

import java.io.IOException;

public class BuildingView {
	private final Node root;

	public BuildingView(Building building) throws IOException {
		root = Utils.loadRoot("Building.fxml", new BuildingController(building));
	}

	public Node getRoot() {
		return root;
	}
}
