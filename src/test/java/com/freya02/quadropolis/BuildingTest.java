package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.plate.BuildingType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuildingTest {
	private static Player player;
	private static Building building;

	@BeforeAll
	static void setUp() {
		player = new Player(4, 4);
		building = new Building(BuildingType.PORT, new Resources(1, 0), true, new Resources(0, 1));
	}

	@Test
	public void test() {
		assertThrows(IllegalStateException.class, () -> building.activate());
		assertThrows(IllegalStateException.class, () -> building.stack());

		building.setOwner(player);

		for (int i = 0; i < 7; i++) {
			assertDoesNotThrow(() -> building.stack());
		}
	}
}