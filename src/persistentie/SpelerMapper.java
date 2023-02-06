package persistentie;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

/**
 * Deze klasse bevat functies om dingen op te vragen en/of aan te passen in de databank.
 * @author G48
 * @version 1.0
 *
 */
public class SpelerMapper {
	
	
    private static final String INSERT_Speler = "INSERT INTO ID374556_tblusers.tblusers (userNm, userBirthYear, userPlayCredits)"
            + "VALUES (?, ?, ?)";
    private static final String UPDATE_Speler = "UPDATE ID374556_tblusers.tblusers SET userPlayCredits = ? where userNm = ? and userBirthYear = ? ";
    
    /**
     * De constructor vraagt de instantie van de klasse Connectie op.
     */
	public SpelerMapper() {
		Connectie.getInstance();
	}
	/**
	 * Deze methode voegt een speler toe aan de databank
	 * @param speler
	 */
	public void addSpeler(Speler speler) {
		try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_Speler)) {
		query.setString(1, speler.getNaam());
		query.setInt(2, speler.getGeboorteDatum());
		query.setInt(3, 5);
		query.executeUpdate();      
		}
		 catch (SQLException ex) {
	            throw new RuntimeException(ex);
	    }
	}
	/**
	 * Deze methode geeft alle spelers in de databank.
	 * @return een lijst met alle spelers die in de databank zitten
	 */
	public List<Speler> geefSpelers(){
		List<Speler> spelers = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
			PreparedStatement query = conn.prepareStatement("SELECT * from ID374556_tblusers.tblusers");
			ResultSet rs = query.executeQuery()) {
			while (rs.next()){
				int userID = rs.getInt("userID");
				String naam = rs.getString("userNm");
				int geboorteDatum = rs.getInt("userBirthYear");
				int credits = rs.getInt("userPlayCredits");
				spelers.add(new Speler(userID, naam, geboorteDatum, credits));
			}
		}catch (SQLException ex){
			throw new RuntimeException(ex);
		}
		return spelers;
	}
	/**
	 * Deze methode update de speelkansen van een Speler in de databank.
	 * @param speler
	 */
	public void updateSpeelKansen(Speler speler) {
			try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(UPDATE_Speler)){
				query.setInt(1, speler.getAantalSpeelbeurten());
				query.setString(2, speler.getNaam());
				query.setInt(3,speler.getGeboorteDatum());
				query.executeUpdate();  							
			}catch (SQLException ex){
				throw new RuntimeException(ex);
			}
	}
//legacy code
//	public Speler geefSpeler(String naam, int geboorteJaar)  {
//
//
//		Speler speler = null;
//		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
//			 PreparedStatement query = conn.prepareStatement("SELECT * FROM ID374556_tblusers.tblusers WHERE userNm = ? and userBirthYear = ?")) {
//			query.setString(1, naam);
//			query.setInt(2, geboorteJaar);
//			try (ResultSet rs = query.executeQuery()) {
//				if (rs.next()) {
//					int userID = rs.getInt("userID");
//					String naamSpeler = rs.getString("userNm");
//					int geboorteDatum = rs.getInt("userBirthYear");
//					int credits = rs.getInt("userPlayCredits");
//					speler = new Speler(userID, naamSpeler, credits, credits);
//
//
//				}
//			}
//		} catch (SQLException ex) {
//			throw new RuntimeException(ex);
//		}
//
//		return speler;
//	}


}
