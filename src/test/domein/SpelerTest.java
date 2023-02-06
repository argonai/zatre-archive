package test.domein;

import domein.Speler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
//Domeinregels:
//DR_Nieuwe_Spelers:
// Gebruikersnaam en geboortejaar zijn verplicht
//Combinatie moet uniek zijn
//Gebruiker moet minstens 6 jaar zijn of 6 jaar worden in het lopende jaar
//Gebruikersnaam moet 5 karakters lang zijn
//DR_Registratie:
//Een speler krijgt automatisch 5 kansen
class SpelerTest {
    private Speler speler1;

    @BeforeEach
    public void before() {

        try {
            speler1 = new Speler("Gebruiker", 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void enkel_naam_werptException(){
//        Assertions.assertThrows(NullPointerException.class, () -> new Speler("EchteNaam", ""));
//    }
    @Test
    public void unieke_gebruiker(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler("testAdmin", 2000));
    }


//    @Test
//    public void enkel_geboorteDatum_werptException(){
//        Assertions.assertThrows(NullPointerException.class, () -> new Speler("", 2000));
//    }
    @Test
    public void leeftijd_teJong(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler("EchteNaam", (int) (Calendar.getInstance().get(Calendar.YEAR) - (Math.random() * (5 - 1)) + 1)));
    }
    @Test
    public void leeftijd_zes_lopendeJaar(){
        Assertions.assertDoesNotThrow(() -> new Speler("Echtenaam", Calendar.getInstance().get(Calendar.YEAR) -6 ));
    }
    @Test
    public void naam_domeinregel_werptException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler("naam", 2000));
    }
    @Test
    public void aantal_kansen_correct(){
        Assertions.assertEquals(5, speler1.getAantalSpeelbeurten());
    }


}