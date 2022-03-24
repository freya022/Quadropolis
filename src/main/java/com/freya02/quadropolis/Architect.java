package com.freya02.quadropolis;

import com.freya02.quadropolis.ui.Utils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Architect {
	private final Player player;
	private final int reach;

	public Architect(Player player, int reach) {
		this.player = player;
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
		final String url = "/com/freya02/quadropolis/ui/media/architects/architecte_%d_%d.PNG".formatted(player.getPlayerNum(), reach + 1); //Reach starts at 0, should start at 1 for the resource

		return new Image(Utils.getResourceAsStream(url));
	}
}
