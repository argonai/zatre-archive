package domein;

import java.util.List;
/**
 * Deze klasse is voor het object vakje in het spel Zatre
 * @author G48
 * @version 1.0
 *
 */
public class Vakje {
	private int x;
	private int y;
	private int kleur; // 0 = empty, 1 = White, 2 = grijs
	private Boolean vakjeBevatSteentje = false; //standaard zit er geen steentje in


	private Steentje steentje;
	/**
	 * De constructor voor het object vakje
	 * @param x
	 * @param y
	 * @param kleur
	 */
	public Vakje(int x, int y, int kleur) {
		this.x = x;
		this.y = y;
		this.kleur = kleur;
	}
	/**
	 * Deze methode geeft de X-coördinaat van het vakje terug
	 * @return de X-coördinaat van het vakje
	 */
	public int getX() {
		return x;
	}
	/**
	 * Deze methode zet de X-coördinaat van een vakje gelijk aan de parameter
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Deze methode geeft de Y-coördinaat van het vakje terug
	 * @return de Y-coördinaat van het vakje
	 */
	public int getY() {
		return y;
	}
	/**
	 * Deze methode zet de Y-coördinaat van een vakje gelijk aan de parameter
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Deze methode geeft de kleur van een vakje terug
	 * @return de kleur van een vakje
	 */
	public int getKleur() {
		return kleur;
	}
	/**
	 * Deze methode zet de kleur van een vakje gelijk aan de parameter
	 * @param kleur
	 */
	public void setKleur(int kleur) {
		this.kleur = kleur;
	}
	/**
	 * Deze methode zegt of een vakje een steentje bevat of niet
	 * @return of het vakje een steentje bevat of niet (True/False)
	 */
	public Boolean getVakjeBevatSteentje() {
		return vakjeBevatSteentje;
	}
	/**
	 * Deze methode zet vakjeBevatSteentje True als het een steentje bevat of False als het er geen bevat
	 * True of False wordt meegegeven als parameter.
	 * @param vakjeBevatSteentje
	 */
	public void setVakjeBevatSteentje(Boolean vakjeBevatSteentje) {
		this.vakjeBevatSteentje = vakjeBevatSteentje;
	}
	/**
	 * Deze methode geeft het steentje van een vakje terug als deze een steentje bevat
	 * @return het steentje van het vakje
	 */
	public Steentje getSteentje() {
		return steentje;
	}
	/**
	 * Deze methode zet het steentje van een vakje gelijk aan de parameter
	 * @param s
	 */
	public void setSteentje(Steentje s) {
		this.steentje = s;
		this.vakjeBevatSteentje = true;
	}
}
