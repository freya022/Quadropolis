package com.freya02.quadropolis.plate;

public enum BuildingType {
	HOUSE("house.png"),
	GARDEN("garden.png"),
	TOWN_HALL("townhall.png"),
	BUSINESS("business.png"),
	PORT("port.png"),
	FACTORY("factory.png");

	private final String fileName;

	BuildingType(String fileName) {
		this.fileName = fileName;
	}

	public String getResourcePath() {
		return "/com/freya02/quadropolis/ui/media/tiles/" + fileName;
	}
}
