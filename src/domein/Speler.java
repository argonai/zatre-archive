package domein;

import java.util.Calendar;
import java.util.List;
/**
 * Deze klasse bevat alle elementen die een speler object nodig heeft 
 * @author G48
 * @version 1.0
 */
public class Speler implements Comparable<Speler> {
 	private int userID;
	private String naam;
	private int geboorteDatum;
	private int aantalspeelbeurten;
	private int aantalGescoord = 0; //default heeft geen enkele speler gescoord
	private List<Steentje> spelerhand;
	private ScoreBlad scoreBlad = new ScoreBlad();

	/**
	 * Een constructor voor het object Speler
	 * @param naam
	 * @param geboorteDatum
	 */
	public Speler(String naam, int geboorteDatum) {
//		this.naam = naam;
//		this.geboorteDatum = geboorteDatum;
		this.setNaam(naam);
		this.setGeboorteDatum(geboorteDatum);
		this.setAantalSpeelbeurten(5);

	}
	/**
	 * Een constructor voor het object Speler
	 * @param userID
	 * @param naam
	 * @param geboorteDatum
	 * @param credits
	 */
	public Speler(int userID, String naam, int geboorteDatum, int credits){
		this.setUserID(userID);
		this.setNaam(naam);
		this.setGeboorteDatum(geboorteDatum);
		this.setAantalSpeelbeurten(credits);
	}
	/**
	 * Deze methode geeft de UserID
	 * @return het UserID van een speler
	 */
	public int getUserID(){
		return userID;
	}
	/**
	 * Deze methode zet de userID van een speler gelijk aan de parameter
	 * @param userID
	 */
	private void setUserID(int userID){
		this.userID = userID;
	}
	/**
	 * Deze methode geeft de naam 
	 * @return naam van een speler
	 */
	public String getNaam() {
		return naam;
	}
	/**
	 * Deze methode zet de naam van speler gelijk aan de parameter met controle
	 * @param naam
	 * @throws IllegalArgumentException
	 */
	private void setNaam(String naam) throws IllegalArgumentException {
		if(naam.length() < 5) {
			throw new IllegalArgumentException("De naam moet minstens 5 karakters lang zijn!");
		}else {
			this.naam = naam;
		}	
	}
	/**
	 * Deze methode geeft de geboortedatum van een speler
	 * @return de geboortedatum van een speler
	 */
	public int getGeboorteDatum() {
		return geboorteDatum;
	}
	/**
	 * Deze methode zet de geboortedatum van een speler gelijk aan de parameter
	 * @param geboorteDatum
	 * @throws IllegalArgumentException
	 */
	private void setGeboorteDatum(int geboorteDatum) throws IllegalArgumentException {
		if ((Calendar.getInstance().get(Calendar.YEAR) - geboorteDatum) >= 6){
			this.geboorteDatum = geboorteDatum;
		}else{
			throw new IllegalArgumentException("Je moet minstens 6 jaar zijn of 6 jaar worden in het lopende jaar");
		}
		
	}
	/**
	 * Deze methode geeft het aantal speelbeurten van een speler 
	 * @return de aantal speelbeurten van een speler
	 */
	public int getAantalSpeelbeurten(){
		return this.aantalspeelbeurten;
	}
	/**
	 * Deze methode zet de aantal speelbeurten van een speler gelijk aan de parameter
	 * @param speelbeurten
	 */
	public void setAantalSpeelbeurten(int speelbeurten){
		this.aantalspeelbeurten = speelbeurten;
	}
	/**
	 * Deze methode geeft het scoreblad van een speler
	 * @return het scoreblad van een speler
	 */
	public ScoreBlad getScoreBlad() {
		return scoreBlad;
	}
	/***
	 * Deze methode zet het scoreblad van een speler gelijk aan de parameter
	 * @param scoreBlad
	 */
	public void setScoreBlad(ScoreBlad scoreBlad) {
		this.scoreBlad = scoreBlad;
	}
	/**
	 * Deze methode reset het scoreblad van een speler
	 */
	public void resetScoreBlad(){
		this.scoreBlad = new ScoreBlad();
	}

	/**
	 * Deze methode zet de hand van de speler gelijk aan de parameter
	 * @param spelerhand
	 */
	public void setSpelerhand(List<Steentje> spelerhand) {
		this.spelerhand = spelerhand;
	}
	/**
	 * Deze methode zet de score van een speler gelijk aan de parameter
	 * @param score
	 */
	public void setScore(int score){
		scoreBlad.setKruisje(score);
	}

	@Override
	public int compareTo(Speler o) {
		return this.geboorteDatum - o.geboorteDatum;
	}
}

