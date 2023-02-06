package domein;
/**
 * Deze klasse is voor het object steentje in het spel Zatre
 * @author G48
 * @version 1.0
 *
 */
public class Steentje {
private int getal;
	/**
	 * Deze methode geeft het getal van een steentje terug
	 * @return het getal van een steentje
	 */
	public int getGetal() {
		return getal;
	}
	/**
	 * Deze methode zet het getal van een steentje gelijk aan de parameter
	 * @param getal
	 */
	public void setGetal(int getal) {
		this.getal = getal;
	}
	/**
	 * De constructor van steentje
	 * @param getal
	 */
	public Steentje(int getal) {
		setGetal(getal);
	}
}
