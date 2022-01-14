package com.freya02.quadropolis;

import com.freya02.quadropolis.exceptions.NotEnoughResources;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Resources {
	private final IntegerProperty houses = new SimpleIntegerProperty();
	private final IntegerProperty barrels = new SimpleIntegerProperty();

	public int getHouses() {
		return houses.get();
	}

	public IntegerProperty housesProperty() {
		return houses;
	}

	public void setHouses(int houses) {
		this.houses.set(houses);
	}

	public int getBarrels() {
		return barrels.get();
	}

	public IntegerProperty barrelsProperty() {
		return barrels;
	}

	public void setBarrels(int barrels) {
		this.barrels.set(barrels);
	}

	public void moveTo(Resources resources) {
		resources.setBarrels(resources.getBarrels() + getBarrels());
		resources.setHouses(resources.getHouses() + getHouses());

		setHouses(0);
		setBarrels(0);
	}

	public void copyTo(Resources resources) {
		resources.setBarrels(resources.getBarrels() + getBarrels());
		resources.setHouses(resources.getHouses() + getHouses());
	}

	public void substract(Resources resources) {
		if (getBarrels() < resources.getBarrels())
			throw new NotEnoughResources();

		if (getHouses() < resources.getHouses())
			throw new NotEnoughResources();

		setBarrels(getBarrels() - resources.getBarrels());
		setHouses(getHouses() - resources.getHouses());
	}
}
