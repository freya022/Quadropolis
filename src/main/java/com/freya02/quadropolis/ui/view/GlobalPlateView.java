package com.freya02.quadropolis.ui.view;

import com.freya02.quadropolis.ui.Utils;
import com.freya02.quadropolis.ui.controller.GlobalPlateController;

import java.io.IOException;

public class GlobalPlateView {
	public GlobalPlateView() throws IOException {
		Utils.loadAndShow("GlobalPlate.fxml", GlobalPlateController::new);
	}
}
