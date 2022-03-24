package com.freya02.quadropolis.plate;

public enum BuildingType {
	HOUSE("house.PNG"),
	GARDEN("garden.PNG"),
	TOWN_HALL("townhall.PNG"),
	BUSINESS("business.PNG"),
	PORT("port.PNG"),
	FACTORY("factory.PNG");

	private final String fileName;

	BuildingType(String fileName) {
		this.fileName = fileName;
	}

	public String getResourcePath() {
		return "/com/freya02/quadropolis/ui/media/tiles/" + fileName;
	}
}
