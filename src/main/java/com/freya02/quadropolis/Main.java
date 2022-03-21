package com.freya02.quadropolis;

import com.freya02.quadropolis.ui.view.MainMenuView;
import javafx.application.Platform;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Platform.startup(() -> {
			try {
				new MainMenuView();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
