package com.freya02.quadropolis.plate;

import com.freya02.quadropolis.Player;
import com.freya02.quadropolis.Resources;

public class Building extends Tile {
	public static final int MAX_STACK = 7;

	private final BuildingType buildingType;
	private final Resources activationCost;
	private final boolean canBeActivated;
	private final Resources revenue; //TODO see when to transfer

	private Player owner;

	private int stackCount = 0;
	private int activationCount = 0;

	public Building(BuildingType buildingType, Resources activationCost, boolean canBeActivated, Resources revenue) {
		this.buildingType = buildingType;
		this.activationCost = activationCost;
		this.canBeActivated = canBeActivated;
		this.revenue = revenue;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

	public void setOwner(Player owner) {
		if (this.owner != null)
			throw new IllegalStateException();

		this.owner = owner;
	}

	public void checkOwner() {
		if (owner == null)
			throw new IllegalStateException();
	}

	public void stack() {
		checkOwner();

		if (stackCount + 1 > MAX_STACK)
			throw new IllegalStateException();

		stackCount++;

		//TODO
	}

	public void activate() {
		checkOwner();

		if (!canBeActivated)
			throw new IllegalStateException();

		if (activationCount + 1 > stackCount)
			throw new IllegalStateException();

		activationCount++;

		owner.getResources().substract(activationCost);
	}
}
