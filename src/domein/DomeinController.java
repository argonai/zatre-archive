package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import exceptions.OnvoldoendeKredietException;
import persistentie.SpelbordMapper;
import persistentie.SpelerMapper;

/**
 * Deze klasse is de domeincontroller en is de connectie tussen onze cui/gui en het domein
 * @author G48
 * @version 1.0
 *
 */
public class DomeinController {
    private final SpelerRepository SpelerRepo = new SpelerRepository();
    private List<Speler> spelers = SpelerRepo.geefSpelers();

    private List<Speler> selectedSpelers = new ArrayList<Speler>();
    private final SpelerMapper sm = new SpelerMapper();
    private final SpelbordMapper sbm = new SpelbordMapper();
    private Spel spel;
    
    /**
     * Deze methode geeft spel van de domeincontroller terug
     * @return het huidige spel in de domeincontroller
     */
    public Spel getSpel() {
        return spel;
    }
    /**
     * Deze methode zet de geselecteerde speler in domeincontroller gelijk aan de parameter
     * @param selectedSpelers
     */
    public void setSelectedSpelers(List<Speler> selectedSpelers ){
        this.selectedSpelers = selectedSpelers;
    }
    /**
     * Deze methode geeft de geselecteerde speler in domeincontroller terug
     * @return de geselecteerde speler in domeincontroller
     */
    public List<Speler> getSelectedSpelers() {
        return selectedSpelers;
    }
    /**
     * Deze methode start het spel en doet de nodige stappen om dit te kunnen uitvoeren
     */
    public void startSpel(String difficulty){
        if (selectedSpelers.size() <= 4 && selectedSpelers.size() >= 2) {
            this.spel = new Spel(selectedSpelers, sm, sbm, difficulty); //stap 2 UC3
            this.updateKansen(); //stap 3 UC3
//            spel.bepaalBeurtrol()
//            Collections.shuffle(selectedSpelers); //stap 4 UC3
        } else {
            throw new IndexOutOfBoundsException("Gelieve 2 tot 4 spelers te selecteren.");
        }
    }
    /**
     * Deze methode steekt een steentje in de pot aan de hand van de index
     * @param geselecteerd
     */
    public void steekSteentjeInPot(int geselecteerd){
        this.spel.steekSteentjeInPot( geselecteerd);
    }
    /**
     * Deze methode geeft de speler steentjes voor de juiste hand
     * @param eerstHand
     */
    public void geefSteentjes(boolean eerstHand) {
        this.spel.geefSpelerSteentjes(eerstHand);
    }
    /**
     * Deze methode selecteert een speler om mee te doen aan het spel
     * @param naam
     * @param geboortedatum
     */
    public void selecteerSpeler(String naam, int geboortedatum) {
        List<Speler> spelers = getSpelers();
        int index = 0;
        for (Speler s : spelers) {
            if (selectedSpelers.size() < 4) {
                if (s.getNaam() == naam && s.getGeboorteDatum() == geboortedatum) {
                    if (!selectedSpelers.contains(s)) {
                        if (s.getAantalSpeelbeurten() != 0) {

                            selectedSpelers.add(s);
                        } else {
                            throw new OnvoldoendeKredietException("Deze speler heeft niet genoeg krediet.");
                        }

                    } else {
                        throw new IllegalArgumentException("Deze speler is al geselecteerd");
                    }

                } else {
                    index += 1;
                }
            }else {
                throw new IndexOutOfBoundsException("Er mogen maar maximum 4 spelers geselecteerd zijn.");
            }
        }
    }
    /**
     * Deze methode geeft een lijst van spelers terug van domeincontroller
     * @return een lijst van spelers
     */
    public List<Speler> getSpelers() {
        return spelers;
    }
    /**
     * Deze methode maakt een nieuwe gebruiker aan, voegt die toe aan de database en geeft hem dan terug
     * @param naam
     * @param geboorteJaar
     * @return de nieuwe speler
     */
    public Speler registreer(String naam, int geboorteJaar) {
        Speler s = null;

        try {
            s = new Speler(naam, geboorteJaar);
            if (spelers.stream().anyMatch(o -> Objects.equals(o.getNaam(), naam) && o.getGeboorteDatum() == geboorteJaar)) {

                s = null;
                throw new IllegalArgumentException("Deze speler is al geregistreerd");
            } else {
                SpelerRepo.addSpeler(s);
                spelers = SpelerRepo.geefSpelers();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    /**
     * Deze methode legt een steentje
     * @param x
     * @param y
     * @param handIndex
     */
    public void legSteentje(int x, int y, int handIndex){
        spel.legSteentje(x,y,handIndex);
    }
    /**
     * Deze methode legt een steentje in de eerste ronde
     * @param selectie
     */
    public void legSteentjeEersteRonde(int x, int y,int selectie){
        spel.legSteentjeEersteRonde(x,y,selectie);
    }
    /**
     * Deze methode legt een steentje in de eerste beurt
     * @param x
     * @param y
     * @param selectie
     */
    public void legSteentjeEersteBeurt(int x, int y,int selectie) {
        spel.legSteentjeEersteBeurt(x,y,selectie);
    }
    /**
     * Deze methode update de kansen van de spelers die het spel starten
     */
    public void updateKansen(){
        spel.updateSpeelKansen(selectedSpelers);
    }
    /**
     * Deze methode bepaalt of het het einde is van een speler zijn beurt
     * @return of de speler zijn beurt gedaan is of niet (True/False)
     */
    public boolean isEindeBeurt(){
        if (spel.getSpelerhand().isEmpty()){
            return false; //beurt is geindigt
        }else {
            return true; //beurt is nog niet geeindigt
        }
    }
    /** 
     * Deze methode verwijdert een steentje uit de hand van de huidige speler
     * @param gekozen
     */
    public void verwijderSteentje(int gekozen){
        spel.verwijderSteentje(gekozen);


    }
    /**
     * Deze methode slaagt de hand van de huidige speler op
     * @param spelerIndex
     */
    public void slaHandOp(int spelerIndex){
        selectedSpelers.get(spelerIndex).setSpelerhand(spel.getSpelerhand());
    }
    /**
     * Deze methode zet de score van de geselecteerde speler
     * @param spelerIndex
     * @param score
     * @param ronde
     */
    public void setScore(int spelerIndex, int score, int ronde){
        selectedSpelers.get(spelerIndex).setScore(score);

    }
    /**
     * Deze methode reset het juist gelegde steentje
     */
    public void resetGelegd(){
        spel.resetJuistGelegd();
    }
    /**
     * Deze methode update het scoreblad
     * @param spelerIndex
     * @param selectedSteentje
     * @param x
     * @param y
     * @param ronde
     */
    public void updateScoreBlad(int spelerIndex, int selectedSteentje, int x, int y, int ronde){
        Integer[] scores = spel.checkScore(spel.getSpelerhand().get(selectedSteentje), x, y);


        for (int i = 0; i < scores.length; i++) {//kruisjes zetten
            selectedSpelers.get(spelerIndex).getScoreBlad().setKruisje(scores[i]);
        }
        selectedSpelers.get(spelerIndex).getScoreBlad().setBonus();
        if (ronde > 1){
            selectedSpelers.get(spelerIndex).getScoreBlad().setDubbel(spel.getSpelBord()[y][x].getKleur());
        }
        selectedSpelers.get(spelerIndex).getScoreBlad().setRecentsteRow(ronde);

    }

}
			