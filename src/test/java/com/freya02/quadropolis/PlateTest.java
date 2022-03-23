package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.PlayerPlate;
import com.freya02.quadropolis.plate.Urbanist;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlateTest {
	private static PlayerPlate plate;
	private static Urbanist tile;

	@BeforeAll
	static void setUp() {
		plate = new PlayerPlate();
		tile = new Urbanist();
	}

	@Test
	public void testGetValid() {
		assertNull(plate.get(0, 0));
		assertNull(plate.get(2, 2));
		assertThrows(IllegalArgumentException.class, () -> plate.get(9, 9));
	}

	@Test
	public void testGetInvalid() {
		assertThrows(IllegalArgumentException.class, () -> plate.get(-1, 0));
		assertThrows(IllegalArgumentException.class, () -> plate.get(0, -1));
		assertThrows(IllegalArgumentException.class, () -> plate.get(10, 9));
		assertThrows(IllegalArgumentException.class, () -> plate.get(9, 10));
	}

	@Test
	public void testSetValid() {
		assertNull(plate.set(0, 0, tile));
		assertNull(plate.set(2, 2, tile));
		assertThrows(IllegalArgumentException.class, () -> plate.set(9, 9, tile));
	}

	@Test
	public void testSetInvalid() {
		assertThrows(IllegalArgumentException.class, () -> plate.set(-1, 0, tile));
		assertThrows(IllegalArgumentException.class, () -> plate.set(0, -1, tile));
		assertThrows(IllegalArgumentException.class, () -> plate.set(10, 9, tile));
		assertThrows(IllegalArgumentException.class, () -> plate.set(9, 10, tile));
	}
}
