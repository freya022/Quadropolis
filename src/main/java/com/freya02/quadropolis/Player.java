package com.freya02.quadropolis;

import com.freya02.quadropolis.plate.Building;
import com.freya02.quadropolis.plate.BuildingType;
import com.freya02.quadropolis.plate.PlayerPlate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

public class Player {
	private final Resources resources = new Resources();

	private final IntegerProperty score = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<Architect> architects = FXCollections.observableArrayList(
			IntStream.rangeClosed(0, 3)
					.mapToObj(reach -> new Architect(this, reach))
					.toList()
	);

	private final PlayerPlate plate;
	private final int playerNum;

	public Player(GameMode gameMode, int playerNum) {
		this.playerNum = playerNum;
		this.plate = new PlayerPlate(gameMode);
	}

	public void calculScore() {
		int score = 0;
		PlayerPlate PP = Quadropolis.getInstance().getPlayers().get(1).getPlate();
		ArrayList<Building> quartier1= new ArrayList<Building>((Collection<? extends Building>) Arrays.asList(PP.get(0,0),PP.get(1,0),PP.get(0,1),PP.get(1,1)));
		ArrayList<Building> quartier2= new ArrayList<Building>((Collection<? extends Building>) Arrays.asList(PP.get(2,0),PP.get(3,0),PP.get(2,1),PP.get(3,1)));
		ArrayList<Building> quartier3= new ArrayList<Building>((Collection<? extends Building>) Arrays.asList(PP.get(0,2),PP.get(1,2),PP.get(1,3),PP.get(0,3)));
		ArrayList<Building> quartier4= new ArrayList<Building>((Collection<? extends Building>) Arrays.asList(PP.get(2,2),PP.get(2,3),PP.get(3,2),PP.get(3,3)));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Building b = (Building) PP.get(i, j);
				Building caseHaut = (Building) PP.tryGet(i - 1, j);
				Building caseBas = (Building) PP.tryGet(i + 1, j);
				Building caseDroite = (Building) PP.tryGet(i, j + 1);
				Building caseGauche = (Building) PP.tryGet(i, j - 1);
				if (b.getBuildingType().equals(BuildingType.HOUSE) && b.getActivationCount() > 0) {
					score++;
				}
				if (b.getBuildingType().equals(BuildingType.BUSINESS) && b.getActivationCount() > 0) {
					if (b.getActivationCount() == 1) {
						score++;
					}
					if (b.getActivationCount() == 2) {
						score += 2;
					}
					if (b.getActivationCount() == 3) {
						score += 4;
					}
					if (b.getActivationCount() == 4) {
						score += 7;
					}
				}
				int cptGarden = 0;
				if (b.getBuildingType().equals(BuildingType.GARDEN)) {
					if (PP.tryGet(i - 1, j) != null && caseHaut.getBuildingType().equals(BuildingType.HOUSE)) {
						cptGarden++;
					}
					if (PP.tryGet(i + 1, j) != null && caseBas.getBuildingType().equals(BuildingType.HOUSE)) {
						cptGarden++;
					}
					if (PP.tryGet(i, j + 1) != null && caseDroite.getBuildingType().equals(BuildingType.HOUSE)) {
						cptGarden++;
					}
					if (PP.tryGet(i, j - 1) != null && caseGauche.getBuildingType().equals(BuildingType.HOUSE)) {
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
				int cptFactoryMarket = 0;
				int cptFactoryPort = 0;
				if (b.getBuildingType().equals(BuildingType.FACTORY) && b.getActivationCount()>0) {
					if (PP.tryGet(i - 1, j) != null && caseHaut.getBuildingType().equals(BuildingType.BUSINESS) && caseHaut.getActivationCount()>0) {
						cptFactoryMarket++;
					}
					if (PP.tryGet(i + 1, j) != null && caseBas.getBuildingType().equals(BuildingType.BUSINESS) && caseBas.getActivationCount()>0) {
						cptFactoryMarket++;
					}
					if (PP.tryGet(i, j + 1) != null && caseDroite.getBuildingType().equals(BuildingType.BUSINESS) && caseDroite.getActivationCount()>0) {
						cptFactoryMarket++;
					}
					if (PP.tryGet(i, j - 1) != null && caseGauche.getBuildingType().equals(BuildingType.BUSINESS) && caseGauche.getActivationCount()>0) {
						cptFactoryMarket++;
					}
					score += cptFactoryMarket * 2;
					if (cptGarden == 2) {
						score += 4;
					}
					if (PP.tryGet(i - 1, j) != null && caseHaut.getBuildingType().equals(BuildingType.PORT) && caseHaut.getActivationCount()>0) {
						cptFactoryPort++;
					}
					if (PP.tryGet(i + 1, j) != null && caseBas.getBuildingType().equals(BuildingType.PORT) && caseBas.getActivationCount()>0) {
						cptFactoryPort++;
					}
					if (PP.tryGet(i, j + 1) != null && caseDroite.getBuildingType().equals(BuildingType.PORT) && caseDroite.getActivationCount()>0) {
						cptFactoryPort++;
					}
					if (PP.tryGet(i, j - 1) != null && caseGauche.getBuildingType().equals(BuildingType.PORT) && caseGauche.getActivationCount()>0) {
						cptFactoryPort++;
					}
					score += cptFactoryMarket * 2;
					score += cptFactoryPort * 3;

				}
				if (b.getBuildingType().equals(BuildingType.PORT) && caseGauche.getActivationCount()>0) {
					score+=3;
				}
			}
		}
		int cptSP = 0;
		if(quartier1.contains(BuildingType.TOWN_HALL)){
			cptSP++;
		}
		if(quartier2.contains(BuildingType.TOWN_HALL)){
			cptSP++;
		}
		if(quartier3.contains(BuildingType.TOWN_HALL)){
			cptSP++;
		}
		if(quartier4.contains(BuildingType.TOWN_HALL)){
			cptSP++;
		}
		if(cptSP==1){
			score+=2;
		}
		if(cptSP==2){
			score+=5;
		}
		if(cptSP==3) {
			score += 9;
		}
		if(cptSP==4){
			score+=14;
		}

		this.setScore(score);
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

	@Override
	public String toString() {
			return "Player{playerNum=%d}".formatted(playerNum);
	}
}
