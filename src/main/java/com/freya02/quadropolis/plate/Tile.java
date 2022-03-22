package com.freya02.quadropolis.plate;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class Tile {
	@NotNull
	public abstract Node asGraphic() throws IOException;
}
