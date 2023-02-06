package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Deze klasse wordt aangemaakt voor het scoreblad element in het spel Zatre
 * @author G48
 * @version 1.0
 *
 */
public class ScoreBlad {
	private List<Integer[]> scoreblad = new ArrayList<Integer[]>();	//we gebruiken een arraylist voor het aantal rijen aangezien we
	//array gaat zijn: [dubbel][tien][elf][twaalf][bonus]		//niet weten hoe lang het scoreblad gaat zijn


	private int totaal = 0;

	public void setRecentsteRow(int recentsteRow) {
		this.recentsteRow = recentsteRow;
	}

	private int recentsteRow = 0; //voor recentste rij bij te houden, doen we omhoog als beurt eindigt en er is gescoord
	private boolean heeftGescoord = false; //recentsteRow word geupdate met funcite adhv deze variabele


	/**
	 * Deze methode geeft het scoreblad
	 * @return het scoreblad als lijst van Integers
	 */
	public List<Integer[]> getScoreblad() {
		return scoreblad;
	}
	public void updateRecentsteRow(){
		if (heeftGescoord){
			recentsteRow += 1;
			heeftGescoord = false;
		}
	}
	/**
	 * Deze methode zet in welke ronde er een dubbele score is
	 * @param dubbel
	 *
	 */
	public void setDubbel(int dubbel){ //2 is dubbel, 1 is normaal, makkelijker voor toaalscore te berekenen in 1 keer


		Integer[] tempArray = new Integer[5];
		if(!scoreblad.isEmpty()){
			tempArray = scoreblad.get(recentsteRow);
		}
		if(dubbel ==2){
			tempArray[0] = 2;
		}
		if(!scoreblad.isEmpty()){
			scoreblad.add(tempArray);
		}else {
			scoreblad.set(recentsteRow,tempArray);
		}

	}
	/**
	 * Deze methode zet in welke ronde er een bonus is
	 * @param ronde
	 */
	public void setBonus(){
		Integer[] tempArray = new Integer[5];
		tempArray =scoreblad.get(recentsteRow);
		tempArray[4] = Math.floorDiv(recentsteRow,4) + 3;
		scoreblad.set(recentsteRow,tempArray);
	}
	/**
	 * Deze methode zet een kruisje op het scoreblad waar nodig
	 * @param score
	 *
	 */
	//int voor recentsteRow bij te houden, recentsteRow komt overeen met de rij

	public void setKruisje(int score){ //int voor ronde bij te houden, ronde komt overeen met de rij
		Integer[] tempArray = new Integer[5];
		if(!scoreblad.isEmpty()){
			tempArray = scoreblad.get(recentsteRow);
		}

		if(tempArray[1] == null || tempArray[2] == null || tempArray[3] == null){
			if(score == 10){
				tempArray[1] = 1; //waarde staat voor het aantal kruisjes
				heeftGescoord = true;
			} else if (score ==11){
				tempArray[2] = 1;
				heeftGescoord = true;

			} else if (score == 12){
				tempArray[3] = 1;
				heeftGescoord = true;
			}
		}else {
			if (score == 10) {
				tempArray[1] += 1; //waarde staat voor het aantal kruisjes
				heeftGescoord = true;
			} else if (score == 11) {
				tempArray[2] += 1;
				heeftGescoord = true;

			} else if (score == 12) {
				tempArray[3] += 1;
				heeftGescoord = true;
			}
		}
		if (scoreblad.isEmpty()){
			scoreblad.add(tempArray);
		}else {
			scoreblad.set(recentsteRow,tempArray);
		}
	}
	/**
	 * deze methode geeft de score van de gevraagde ronde
	 * @param row
	 * @return de score van de ronde
	 */
	public int geefScoreRow(int row){
		int bonus = 0;
		int score = 0;
		int dubbel = 1;
		int totaalscore;
		Integer[] tempArray = new Integer[5];
		tempArray = scoreblad.get(row);
		for (int i = 0; i < 5; i++) {
			if(!(tempArray[i] == null)){
				if(i == 0){
					dubbel = tempArray[i];
				}else if (i == 1) {
					score += tempArray[i]; //aantal kruisjes bij 10 staan
				} else if (i == 2) {
					score += tempArray[i] * 2; //aantal kruisjes dat bij 11 staat
				}else if (i == 3){
					score += tempArray[i] * 4;//aantal kruisjes bij 12
				} else if (i == 4) {
					bonus = tempArray[i];
				}
			}

		}
		if(tempArray[1] == null || tempArray[2] == null || tempArray[3] == null){
			totaalscore = score;
		}else {
			totaalscore = (score + bonus) * dubbel;
		}
		return totaalscore;

	}
	/**
	 * Deze methode berekent de totale score van het scoreblad
	 * @return de totale score van het scoreblad
	 */
	public int berekenTotaalScore(){
		int totaal = 0;
		for (int i = 0; i < scoreblad.size(); i++) {
			totaal += geefScoreRow(i);
		}
		return totaal;
	}
}

