package persistentie;
/**
 * Deze klassen is een singleton die de connectiestring van de databank bijhoudt.
 * @author G48
 * @version 1.0
 *
 */
public class Connectie {
	private static Connectie instance;

	public static String JDBC_URL;
	/**
	 * initialiseert de klassevariabele JDBC_URL
	 */
	private Connectie() {
		JDBC_URL = "jdbc:mysql://localhost:3306/id374556_tblusers?serverTimezone=UTC&useLegacyDatetimeCode=false&user=root&password=password";
	}
	
	/**
	 * Deze methode geeft de instantie van de klasse Connectie en als deze nog niet 
	 * bestond gaat hij deze aanmaken.
	 * @return de instantie van klasse Connectie
	 */
	public static Connectie getInstance()  
	{
	    if (instance == null)
	    {
	      instance = new Connectie();
	    }
	    return instance;
	}

}

