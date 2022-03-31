package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.plate.BuildingType;
import com.freya02.quadropolis.plate.PlayerPlate;
import com.freya02.quadropolis.plate.Tile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private static final ObservableList<Architect> sharedArchitects = FXCollections.observableArrayList();

	private final Resources resources = new Resources();

	private final IntegerProperty score = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<Architect> architects;

	private final PlayerPlate plate;
	private final GameMode gameMode;
	private final int playerNum;

	private final IntegerProperty turn = new SimpleIntegerProperty(1);

	public Player(GameMode gameMode, int playerNum) {
		this.gameMode = gameMode;
		this.playerNum = playerNum;
		this.plate = new PlayerPlate(gameMode);

		if (gameMode == GameMode.CLASSIC) {
			this.architects = FXCollections.observableArrayList(retrieveArchitects());
		} else {
			sharedArchitects.setAll(retrieveArchitects());

			this.architects = sharedArchitects;
		}
	}

	private List<Architect> retrieveArchitects() {
		final List<Architect> list = new ArrayList<>();

		if (gameMode == GameMode.CLASSIC) {
			for (int reach = 0; reach < gameMode.getMaxArchitects(); reach++) {
				list.add(new Architect(this, gameMode, reach));
			}
		} else {
			for (int reach = 0; reach < gameMode.getMaxArchitects(); reach++) {
				for (int player = 0; player < Quadropolis.getInstance().getMaxPlayers(); player++) {
					list.add(new Architect(this, gameMode, reach));
				}
			}
		}

		return list;
	}

	public void calculScore() {
		int score = 0;

		score += getHouseScore();
		score += getPortScore();
		score += getGardenScore();
		score += getFactoryScore();
		score += getTownHallScore();
		score += getBusinessScore();
		score -= getResources().getBarrels();
		score -= getResources().getHouses();

		this.setScore(score);
	}

	public int getTownHallScore() {
		int score = 0;
		int cptTownHall = 0;
		if (isQuartier(0, 0)) {
			cptTownHall += 1;
		}
		if (isQuartier(0, 2)) {
			cptTownHall += 1;
		}
		if (isQuartier(2, 0)) {
			cptTownHall += 1;
		}
		if (isQuartier(2, 2)) {
			cptTownHall += 1;
		}
		if (cptTownHall == 1) {
			score += 2;
		}
		if (cptTownHall == 2) {
			score += 5;
		}
		if (cptTownHall == 3) {
			score += 9;
		}
		if (cptTownHall == 4) {
			score += 14;
		}
		return score;
	}

	private boolean isQuartier(int x, int y) {
		final List<Tile> list = new ArrayList<>();
		list.add(plate.get(x, y));
		list.add(plate.get(x + 1, y));
		list.add(plate.get(x, y + 1));
		list.add(plate.get(x + 1, y + 1));

		for (Tile tile : list) {
			if (tile instanceof Building) {
				final Building building = (Building) tile;

				if (building.getBuildingType() == BuildingType.TOWN_HALL && building.getActivationCount() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public int getFactoryScore() {
		int score = 0;
		for (int baseX = 0; baseX < plate.getWidth(); baseX++) {
			for (int baseY = 0; baseY < plate.getHeight(); baseY++) {
				int cptFactoryMarket = 0;
				int cptFactoryPort = 0;

				final Building building = (Building) plate.tryGet(baseX, baseY);
				final Building buildingUp = (Building) plate.tryGet(baseX, baseY + 1);
				final Building buildingDown = (Building) plate.tryGet(baseX, baseY - 1);
				final Building buildingRight = (Building) plate.tryGet(baseX + 1, baseY);
				final Building buildingLeft = (Building) plate.tryGet(baseX - 1, baseY);

				if (building == null) continue;
				if (buildingUp == null) continue;
				if (buildingDown == null) continue;
				if (buildingRight == null) continue;
				if (buildingLeft == null) continue;

				if (building.getBuildingType() == BuildingType.GARDEN && building.getActivationCount() > 0) {
					if (buildingUp.getBuildingType() == BuildingType.BUSINESS && buildingUp.getActivationCount() > 0) {
						cptFactoryMarket++;
					}
					if (buildingDown.getBuildingType() == BuildingType.BUSINESS && buildingDown.getActivationCount() > 0) {
						cptFactoryMarket++;
					}
					if (buildingRight.getBuildingType() == BuildingType.BUSINESS && buildingRight.getActivationCount() > 0) {
						cptFactoryMarket++;
					}
					if (buildingLeft.getBuildingType() == BuildingType.BUSINESS && buildingLeft.getActivationCount() > 0) {
						cptFactoryMarket++;
					}
					if (buildingUp.getBuildingType() == BuildingType.PORT && buildingUp.getActivationCount() > 0) {
						cptFactoryPort++;
					}
					if (buildingDown.getBuildingType() == BuildingType.PORT && buildingDown.getActivationCount() > 0) {
						cptFactoryPort++;
					}
					if (buildingRight.getBuildingType() == BuildingType.PORT && buildingRight.getActivationCount() > 0) {
						cptFactoryPort++;
					}
					if (buildingLeft.getBuildingType() == BuildingType.PORT && buildingLeft.getActivationCount() > 0) {
						cptFactoryPort++;
					}
					score += cptFactoryMarket * 2;
					score += cptFactoryPort * 3;
				}
			}
		}

		return score;
	}


	public int getGardenScore() {
		int score = 0;
		for (int baseX = 0; baseX < plate.getWidth(); baseX++) {
			for (int baseY = 0; baseY < plate.getHeight(); baseY++) {
				final Building building = (Building) plate.tryGet(baseX, baseY);
				final Building buildingUp = (Building) plate.tryGet(baseX, baseY + 1);
				final Building buildingDown = (Building) plate.tryGet(baseX, baseY - 1);
				final Building buildingRight = (Building) plate.tryGet(baseX + 1, baseY);
				final Building buildingLeft = (Building) plate.tryGet(baseX - 1, baseY);

				if (building == null) continue;
				if (buildingUp == null) continue;
				if (buildingDown == null) continue;
				if (buildingRight == null) continue;
				if (buildingLeft == null) continue;

				int cptGarden = 0;
				if (building.getBuildingType() == BuildingType.GARDEN) {
					if (buildingUp.getBuildingType() == BuildingType.HOUSE && buildingUp.getActivationCount() > 0) {
						cptGarden++;
					}
					if (buildingDown.getBuildingType() == BuildingType.HOUSE && buildingDown.getActivationCount() > 0) {
						cptGarden++;
					}
					if (buildingRight.getBuildingType() == BuildingType.HOUSE && buildingRight.getActivationCount() > 0) {
						cptGarden++;
					}
					if (buildingLeft.getBuildingType() == BuildingType.HOUSE && buildingLeft.getActivationCount() > 0) {
						cptGarden++;
					}
					if (cptGarden == 1) {
						score += 2;
					}
					if (cptGarden == 2) {
						score += 4;
					}
					if (cptGarden == 3) {
						score += 7;
					}
					if (cptGarden == 4) {
						score += 11;
					}
				}
			}
		}
		return score;
	}

	public int getBusinessScore() {
		int score = 0;

		for (Tile tile : plate.getTiles()) { //Pas besoin de savoir le x ou le y ici, on veut juste savoir le nombre de stack de chaque maison
			if (tile instanceof Building) {
				final Building building = (Building) tile;

				if (building.getBuildingType() == BuildingType.BUSINESS) { //Si c'est un commerce
					//Voir la fiche de score pour les commerces, page 5
					switch (building.getActivationCount()) {
						case 0:
							score += 0;
							break;
						case 1:
							score += 1;
							break;
						case 2:
							score += 2;
							break;
						case 3:
							score += 4;
							break;
						case 4:
							score += 7;
							break;
						default:
							throw new IllegalArgumentException("A business has more than 4 people"); //Au cas où
					}
				}
			}
		}

		return score;
	}

	public int getHouseScore() {
		int score = 0;
		for (Tile tile : plate.getTiles()) { //Pas besoin de savoir le x ou le y ici, on veut juste savoir le nombre de stack de chaque maison
			if (tile instanceof Building) {
				final Building building = (Building) tile;

				if (building.getBuildingType() == BuildingType.HOUSE) { //Si c'est une maison
					//Voir la fiche de score pour les habitations, page 5

					if (building.getStackCount() == 1) {
						score += 1;
					} else if (building.getStackCount() == 2) {
						score += 3;
					} else if (building.getStackCount() == 3) {
						score += 6;
					} else if (building.getStackCount() == 4) {
						score += 10;
					} else {
						throw new IllegalArgumentException("A house has more than 4 stacks"); //Au cas où
					}
				}
			}
		}
		return score;
	}

	public int getPortScore() {
		//On recherche la succession de port la plus longue, en ligne ou colonne

		int longest = 0;

		//On regarde chaque case, si on trouve un port alors on cherche une ligne / colonne
		for (int baseX = 0; baseX < plate.getWidth(); baseX++) {
			for (int baseY = 0; baseY < plate.getHeight(); baseY++) {
				final Building building = (Building) plate.get(baseX, baseY);

				if (building == null) continue;

				if (building.getBuildingType() == BuildingType.PORT) {
					{
						//Regardons les colonnes en premier
						int length = 0;
						for (int y = baseY; y < plate.getHeight(); y++) {
							final Tile otherTile = plate.get(baseX, y);

							if (otherTile instanceof Building) {
								final Building otherBuilding = (Building) otherTile;

								if (otherBuilding.getBuildingType() == BuildingType.PORT) {
									length++;
								} else {
									break;
								}
							} else {
								break;
							}
						}

						longest = Math.max(longest, length);
					}

					{
						//Regardons les lignes après
						int length = 0;
						for (int x = baseX; x < plate.getWidth(); x++) {
							final Tile otherTile = plate.get(x, baseY);

							if (otherTile instanceof Building) {
								final Building otherBuilding = (Building) otherTile;

								if (otherBuilding.getBuildingType() == BuildingType.PORT) {
									length++;
								} else {
									break;
								}
							} else {
								break;
							}
						}

						longest = Math.max(longest, length);
					}
				}
			}
		}

		switch (longest) {
			case 1:
				return 0;
			case 2:
				return 3;
			case 3:
				return 7;
			case 4:
				return 12;
			default:
				throw new IllegalStateException("Unexpected value: " + longest);
		}
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public int getScore() {
		return score.get();
	}

	public void setScore(int score) {
		this.score.set(score);
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObservableList<Architect> getArchitects() {
		return architects;
	}

	public Resources getResources() {
		return resources;
	}

	public PlayerPlate getPlate() {
		return plate;
	}

	public int getTurn() {
		return turn.get();
	}

	public void setTurn(int turn) {
		this.turn.set(turn);
	}

	public IntegerProperty turnProperty() {
		return turn;
	}

	@Override
	public String toString() {
		return String.format("Player{playerNum=%d}", playerNum);
	}

	public void regenArchitects() {
		architects.clear();
		architects.addAll(retrieveArchitects());
	}
}
