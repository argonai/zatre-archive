package domein;

import persistentie.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Deze klasse houdt een lijst van spelers bij en is communiceert met de mapper
 * @author G48
 * @version 1.0
 *
 */
public class SpelerRepository {
	private SpelerMapper mapper = new SpelerMapper();
	private final List<Speler> spelers;
	
	/**
	 * Constructor voor SpelerRepository
	 */
	public SpelerRepository() {
		this.mapper = new SpelerMapper();
		this.spelers = mapper.geefSpelers();
	}
	/**
	 * Deze methode gaat een speler toevoegen aan de databank
	 * @param speler
	 */
	public void addSpeler(Speler speler) {
		mapper.addSpeler(speler);
	}
	/**
	 * Deze methode gaat een lijst met alle spelers uit de databank teruggeven
	 * @return lijst met alle spelers in de databank
	 */
	public List<Speler> geefSpelers() {
		return spelers;
	}
}

