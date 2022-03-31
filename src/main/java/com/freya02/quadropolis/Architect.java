package com.freya02.quadropolis;

import com.freya02.quadropolis.ui.Utils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Architect {
	private final Player player;
	private final GameMode gameMode;
	private final int reach;

	public Architect(Player player, GameMode gameMode, int reach) {
		this.player = player;
		this.gameMode = gameMode;
		this.reach = reach;
	}

	public int getReach() {
		return reach;
	}

	public int getVisualReach() {
		return reach + 1;
	}

	@NotNull
	public Image asImage() {
		final String url;

		if (gameMode == GameMode.CLASSIC) {
			url = "/com/freya02/quadropolis/ui/media/architects/architecte_%d_%d.png".formatted(player.getPlayerNum(), reach + 1); //Reach starts at 0, should start at 1 for the resource
		} else {
			url = "/com/freya02/quadropolis/ui/media/architects/architecte_expert_%d.png".formatted(reach + 1);
		}

		return new Image(Utils.getResourceAsStream(url));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Architect architect = (Architect) o;

		return reach == architect.reach;
	}

	@Override
	public int hashCode() {
		return reach;
	}
}
