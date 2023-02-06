package domein;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


import persistentie.SpelbordMapper;
import persistentie.SpelerMapper;
/**
 * Deze klasse wordt aangemaakt om het spel Zatre te kunnen spelen met alle nodige spelelementen.
 * @author G48
 * @version 1.0
 *
 */
public class Spel {
    private List<String> score;
    private Speler spelerAandeBeurt;
    private Vakje vakjeSelected;
    private Steentje steentjeSelected;
    private List<Steentje> steentjes;
    private Vakje[][] spelBord;
    private List<Speler> spelers;
    private SpelerMapper mapper;
    private SpelbordMapper SBmapper;
    private List<Vakje> juistGelegd;
    private Speler huidigespeler;
    private List<Steentje> spelerhand;
    private boolean potLeeg;
    int Difficulty;
    
    /**
     * Deze methode zet de klassevariabele mapper naar de parameter.
     * @param mapper
     */
    public void setMapper(SpelerMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Deze constructor maakt een spel aan met een lijst van spelers en een doorgegeven mapper.
     *
     * @param spelers    de lijst met spelers
     * @param mapper     de doorgegeven mapper
     * @param sbm
     * @param difficulty
     */
    public Spel(List<Speler> spelers, SpelerMapper mapper, SpelbordMapper sbm, String difficulty) {
        this.setMapper(mapper);
        this.steentjes = generateSteentjes();
        this.SBmapper = sbm;
        this.setJuistGelegd(new ArrayList<>());
        try {
            this.setSpelBord(difficulty);
        } catch (Exception e){
            e.printStackTrace();
        }

        this.setSpelers(bepaalBeurtrol(spelers));

    }

    public boolean getPotLeeg() {
        return potLeeg;
    }

    public void setPotLeeg(boolean potLeeg) {
        this.potLeeg = potLeeg;
    }

    /**
     * Deze methode geeft het juist gelegde vakje.
     * @return de juist gelegde vakjes
     */
    public List<Vakje> getJuistGelegd() {
        return juistGelegd;
    }
    /**
     * Deze methode zet de klassevariabele juistGelegd gelijk aan de parameter.
     * @param juistGelegd
     */
    public void setJuistGelegd(List<Vakje> juistGelegd) {
        this.juistGelegd = juistGelegd;
    }
    /**
     * Deze methode geeft de spelers van het spel.
     * @return een lijst met alle spelers die het spel aan het spelen zijn
     */
    public List<Speler> getSpelers() {
        return spelers;
    }
    /**
     * Deze methode ze de spelers gelijk aan de parameter
     * @param spelers
     */
    public void setSpelers(List<Speler> spelers) {
        this.spelers = spelers;
    }
    /**
     * Deze methode geeft de score van het spel.
     * @return een lijst die de scores bevat
     */
    public List<String> getScore() {
        return score;
    }
    /**
     * Deze methode zet de klassevariabele score gelijk aan de parameter.
     * @param score
     */
    public void setScore(List<String> score) {
        this.score = score;
    }
    /**
     * Deze methode geeft de speler aan de beurt.
     * @return de huidige speler aan de beurt in het spel
     */
    public Speler getSpelerAandeBeurt() {
        return spelerAandeBeurt;
    }

    /**
     * Deze methode zet de klassevariabelen spelerAandeBeurt gelijk aan de parameter.
     * @param spelerAandeBeurt
     */
    public void setSpelerAandeBeurt(Speler spelerAandeBeurt) {
        this.spelerAandeBeurt = spelerAandeBeurt;
    }

    /**
     * Deze methode geeft het geselecteerde vakje.
     * @return het geselecteerde vakje
     */
    public Vakje getVakjeSelected() {
        return vakjeSelected;
    }

    /**
     * Deze methode zet de klassevariabele vakjeSelected gelijk aan de parameter.
     * @param vakjeSelected
     */
    public void setVakjeSelected(Vakje vakjeSelected) {
        this.vakjeSelected = vakjeSelected;
    }
    /**
     * Deze methode geeft het geselecteerde steentje.
     * @return het geselecteerde steentje
     */
    public Steentje getSteentjeSelected() {
        return steentjeSelected;
    }

    /**
     * Deze methode zet de klassevariabele steentjeSelected gelijk aan de parameter.
     * @param steentjeSelected
     */
    public void setSteentjeSelected(Steentje steentjeSelected) {
        this.steentjeSelected = steentjeSelected;
    }

    /**
     * Deze methode geeft de steentjes van het spel.
     * @return een lijst met alle nog niet gespeelde steentjes in het spel
     */
    public List<Steentje> getSteentjes() {
        return steentjes;
    }

    /**
     * Deze methode zet de klassevariabele steentjes gelijk aan de parameter.
     * @param steentjes
     */
    public void setSteentjes(List<Steentje> steentjes) {
        this.steentjes = steentjes;
    }

    /**
     * Deze methode geeft het spelbord.
     * @return een 2d array van vakjes die het spelbord voorstellen.
     */
    public Vakje[][] getSpelBord() {
        return spelBord;
    }
    /**
     * Deze methode geeft de huidige speler die aan de beurt is.
     * @return de huidige speler die aan de beurt is
     */
    public Speler getHuidigespeler() {
        return huidigespeler;
    }
    /**
     * Deze methode zet de klassevariabele huidigespeler gelijk aan de parameter.
     * @param huidigespeler
     */
    public void setHuidigespeler(Speler huidigespeler) {
        this.huidigespeler = huidigespeler;
    }
    /**
     * Deze methode geeft de hand van de speler die aan de beurt is.
     * @return de steentjes van de speler die aan de beurt is
     */
    public List<Steentje> getSpelerhand() {
        return spelerhand;
    }
    /**
     * Deze methode zet de hand van de speler die aan de beurt is gelijk aan de parameter.
     * @param spelerhand
     */
    public void setSpelerhand(List<Steentje> spelerhand) {
        this.spelerhand = spelerhand;
    }
    /**
     * Deze methode genereert een nieuw spelbord van 15 op 15 Vakjes met de juiste kleuren en stelt deze als klassevariabele spelbord.
     * @param spelBord
     */

    public void setSpelBord(String moeilijkheidsgraad) throws FileNotFoundException {
        String[] line;
        int counter = 0;
        //TODO: makkelijk (20x20 bord), moeilijk 10*10 bord
//
//        for (int y = 0; y < 15; y++) { //we iteraten coordinate in het spelbord en voegen een vakje toe gebaseerd op criteria
//            for (int x = 0; x < 15; x++) { //we gaan van minst voorkomend naar meest voorkomend, leeg -> grijs -> wit
//                if ((y == 0 || y == 14) && (x == 7 || x <= 3 || x >= 11)) { //check of in de eerste en laatste rij, en positie kolom, generate horizontaal leeg
//                    spelBord[y][x] = new Vakje(x, y, 0);
//                } else if ((y <= 3 || y >= 11 || y == 7) && (x == 0 || x == 14)) { //idem maar verticaal leeg
//                    spelBord[y][x] = new Vakje(x, y, 0);
//                } else if (y == x) { //grijs kruis, linksboven naar rechtsonder
//                    spelBord[y][y] = new Vakje(x, y, 2);
//                } else if (x == 14 - y) { //grijs kruis, rechtsboven naar rechtsonder
//                    spelBord[y][x] = new Vakje(x, y, 2);
//                } else if ((y == 0 || y == 14) && (x == 6 || x == 8)) { // grijze vakjes bovenaan
//                    spelBord[y][x] = new Vakje(x, y, 2);
//                } else if ((y == 6 || y == 8) && (x == 0 || x == 14)) { //grijze vakjes aan de zijkant
//                    spelBord[y][x] = new Vakje(x, y, 2);
//                } else { //Wit vakje
//                    spelBord[y][x] = new Vakje(x, y, 1);
//                }
//            }
//
//
//        }
        List<List<Vakje>> tempBord = new ArrayList<List<Vakje>>();
        Scanner sc = new Scanner(new BufferedReader(new FileReader(SBmapper.getRandomFile(moeilijkheidsgraad))));
        while (sc.hasNextLine()){

            line = sc.nextLine().trim().split(" ");
            tempBord.add(new ArrayList<Vakje>());
            for (int i = 0; i<line.length; i++) {
//                spelBord[counter][i] = new Vakje(counter,i, Integer.parseInt(line[i]));
                tempBord.get(counter).add(new Vakje(counter,i,Integer.parseInt(line[i])));
            }
            counter++;
        }
        this.spelBord = tempBord.stream().map(o -> o.toArray(new Vakje[0])).toArray(Vakje[][]::new);

    }
    /**
     * Deze methode geeft de scores terug zodat deze kunnen gebruikt worden in het scoreblad.
     * @param steentje
     * @param xStart
     * @param yStart
     * @return een lijst met de scores 
     */
    public Integer[] checkScore(Steentje steentje, int xStart, int yStart){
        Integer[] waardes = new Integer[2];

        int totalX = steentje.getGetal(); //totaalscore verticaal, begint met waarde origineel steentje
        int totalY = steentje.getGetal();
        for (int x = xStart-1; x >= 0 ; x--) { //links checken
            if (spelBord[yStart][x].getVakjeBevatSteentje()){
                totalX += spelBord[yStart][x].getSteentje().getGetal();
            }
        }
        for (int x = xStart +1; x <= 14 ; x++) { //Rechts checken
            if (spelBord[yStart][x].getVakjeBevatSteentje()){
                totalX += spelBord[yStart][x].getSteentje().getGetal();
            }
        }
        for (int y = yStart -1; y >= 0 ; y--) { //boven checken
            if (spelBord[y][xStart].getVakjeBevatSteentje()){
                totalY += spelBord[y][xStart].getSteentje().getGetal();
            }
        }
        for (int y = yStart +1; y <= 0 ; y++) { //Onder checken
            if (spelBord[y][xStart].getVakjeBevatSteentje()){
                totalY += spelBord[y][xStart].getSteentje().getGetal();
            }
        }

        waardes[0] = totalX;
        waardes[1] = totalY;
        return waardes;


    }
    /**
     * Deze methode gaat bepalen of de de waarde niet groter is dan 12 zowel horizontaal als verticaal omdat je
     * hoger dan 12 geen steentje mag leggen.
     * @param steentje
     * @param xStart
     * @param yStart
     * @return of de waarde groter is dan 12 of niet (True/False)
     */
    public boolean checkWaarde(Steentje steentje, int xStart, int yStart) {

        int totalX = steentje.getGetal(); //totaalscore verticaal, begint met waarde origineel steentje
        int totalY = steentje.getGetal();
        for (int x = xStart; x >= 0 ; x--) { //links checken
            if (spelBord[yStart][x].getVakjeBevatSteentje()){
                totalX += spelBord[yStart][x].getSteentje().getGetal();
            }
        }
        for (int x = xStart; x <= 14 ; x++) { //Rechts checken
            if (spelBord[yStart][x].getVakjeBevatSteentje()){
                totalX += spelBord[yStart][x].getSteentje().getGetal();
            }
        }
        for (int y = yStart; y >= 0 ; y--) { //boven checken
            if (spelBord[y][xStart].getVakjeBevatSteentje()){
                totalY += spelBord[y][xStart].getSteentje().getGetal();
            }
        }
        for (int y = yStart; y <= 0 ; y++) { //Onder checken
            if (spelBord[y][xStart].getVakjeBevatSteentje()){
                totalY += spelBord[y][xStart].getSteentje().getGetal();
            }
        }
        if(totalX >12 || totalY > 12){
            return false;
        }
        return true;


    }
    /**
     * Deze methode bepaalt of het steentje mag gelegd worden in de gegeven positie.
     * @param steentje
     * @param xStart
     * @param yStart
     * @return of de positie toegelaten is of niet (True/False)
     */
    public boolean checkPositie(Steentje steentje, int xStart, int yStart){
        if (!(xStart == spelBord.length)){ //we gebruiken if ipv else-if aangezien het programme ze allemaal moet controleren
            if (spelBord[yStart][xStart+1].getVakjeBevatSteentje() && !(juistGelegd.contains(spelBord[yStart][xStart+1]))){
                return true;
            }
        }
        if (!(xStart == 0)){
            if (spelBord[yStart][xStart-1].getVakjeBevatSteentje() && !(juistGelegd.contains(spelBord[yStart][xStart-1]))) {
                return true;
            }
        }
        if (!(yStart == spelBord.length)) {
            if (spelBord[yStart + 1][xStart].getVakjeBevatSteentje() && !(juistGelegd.contains(spelBord[yStart + 1][xStart]))) {
                return true;
            }
        }
        if (!(yStart ==0)){
            if (spelBord[yStart - 1][xStart].getVakjeBevatSteentje() && !(juistGelegd.contains(spelBord[yStart - 1][xStart]))){
                return true;
            }
        }
        return false;
    }
    /**
     * Geeft aan of het spel ten einde is of niet.
     * @return True als het spel ten einde is en anders False
     */

    /**
     * Geeft de de spelers van het spel een willekeurige beurtrol door de lijst de shufflen.
     * @param spelers
     * @return de geshuffelde lijst met spelers
     */
    public List<Speler> bepaalBeurtrol(List<Speler> spelers) {
        spelers.sort(Comparator.comparing(Speler::getGeboorteDatum));
        return spelers;
    }
    /**
     * Deze methode genereert de steentjes van het spel.
     * @return een lijst met alle steentjes
     */
    public List<Steentje> generateSteentjes() {
        List<Steentje> steentjes = new ArrayList<Steentje>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 20; j++) {
                steentjes.add(new Steentje(i + 1));
            }
        }
        steentjes.add(new Steentje(1));
        Collections.shuffle(steentjes);
        potLeeg = false;
        return steentjes;
    }
    /**
     * Bepaalt wie de winnaar is afhankelijk van de scores
     * @return de winnaar als Speler object
     */
    public Speler bepaalWinnaar(){
    	int kleinGetal = Integer.MIN_VALUE;
    	Speler winnaar = null;
    	for(Speler s : spelers) {
    		s.getScoreBlad().berekenTotaalScore();
    		if (s.getScoreBlad().berekenTotaalScore() > kleinGetal) {
    			kleinGetal = s.getScoreBlad().berekenTotaalScore();
    			winnaar = s;
    		}
    	}
    	verhoogSpeelkansenWinnaar(winnaar);
    	return winnaar;
    }
    /**
     * Deze methode verhoogt de speelkansen van de winnaar met 2
     * @param s
     */
    public void verhoogSpeelkansenWinnaar(Speler s) {
    	s.setAantalSpeelbeurten(s.getAantalSpeelbeurten() + 2);
    	mapper.updateSpeelKansen(s);
    }
    /**
     * Deze methode update de spelkansen van de spelers in de databank met -1 bij de start van het spel
     * @param spelers
     */
    public void updateSpeelKansen(List<Speler> spelers) {
        for (Speler s : spelers) {
            s.setAantalSpeelbeurten(s.getAantalSpeelbeurten() - 1);
        }
        for (Speler s : spelers) {
            mapper.updateSpeelKansen(s);
        }
    }


    /**
     * Deze methode geeft de huidige speler aan de beurt steentjes afhankelijk van of het de eerste ronde is.
     * er word ook gecontroleerd of de pot leeg is voor het einde van het spel
     * @param eersteHand
     */
    public void geefSpelerSteentjes(boolean eersteHand) { //deze functie geeft de speler steentjes, nog een andere functie nodig om
        Collections.shuffle(steentjes); //shuffle steentjes
        this.spelerhand = new ArrayList<Steentje>(); //initialise hand
        int aantalsteentjes;
        if (eersteHand){
            aantalsteentjes =3;
        }else{
            aantalsteentjes = 2;
        }
        for (int i = 0; i < aantalsteentjes; i++) { //als eerste ronde, geeft 3 steentjes, geef anders 2
            if (steentjes.isEmpty()){
                potLeeg = true;
            } else {
                spelerhand.add(steentjes.get(0)); //voeg steentjes toe aan de spelerhand
                steentjes.remove(0); //verwijder ze uit de pot
            }

        }
    }
    /**
     * Deze methode steekt een geselecteerd steentje in de pot
     * @param geselecteerd
     */
    public void steekSteentjeInPot(int geselecteerd) {
        steentjes.add(spelerhand.get(geselecteerd));
        spelerhand.remove(geselecteerd);
    }
    /**
     * Deze methode laat toe een steentje te leggen waar dit mag
     * @param x
     * @param y
     * @param steentjesIndex
     */
    public void legSteentje(int x, int y, int steentjesIndex) {
        Steentje steentje = spelerhand.get(steentjesIndex);
        boolean kanLeggen = false;
        if (spelBord[y][x].getVakjeBevatSteentje()) { //checkt of het vakje al een steentje bevat
            throw new IllegalArgumentException("Dit vakje bevat al een steentje");
        } else if (spelBord[y][x].getKleur() == 0) {
            throw new IllegalArgumentException("Hier kan je geen steentje leggen");
        } else if (!(checkWaarde(steentje, x,y))) {
            throw new IllegalArgumentException("De cumilatieve waarde mag niet groter zijn dan 12!");
        } else if (!(checkPositie(steentje,x,y))) {
            throw new IllegalArgumentException("Je steentje moet verbonden zijn met een ander steentje dat je niet deze ronde hebt gelegd!");
        } else if (spelBord[y][x].getKleur() == 2) {
            Integer[] scores = checkScore(steentje,x,y);
            for (int i = 0; i<scores.length;i++){
                if (i ==10 || i ==11 || i ==12){
                    kanLeggen = true;
                }
            }
            if (kanLeggen){
                juistGelegd.add(spelBord[y][x]);
                spelBord[y][x].setSteentje(steentje);
            }else {
                throw new IllegalArgumentException("Als je een steentje op een grijs vakje wilt leggen moet je cumulatieve waarde 10,11 of 12 zijn!");
            }
        } else {
            juistGelegd.add(spelBord[y][x]);
            spelBord[y][x].setSteentje(steentje);
        }
    }
    /**
     * Deze methode laat toe een steentje te leggen waar dit mag in de eerste beurt
     * @param x
     * @param y
     * @param steentjesIndex
     */
    public void legSteentjeEersteBeurt(int x, int y, int steentjesIndex) {
        Steentje steentje = spelerhand.get(steentjesIndex);
        boolean kanLeggen = false;
        if (spelBord[y][x].getVakjeBevatSteentje()) { //checkt of het vakje al een steentje bevat
            throw new IllegalArgumentException("Dit vakje bevat al een steentje");
        } else if (spelBord[y][x].getKleur() == 0) {
            throw new IllegalArgumentException("Hier kan je geen steentje leggen");
        } else if (!(checkWaarde(steentje, x,y))) {
            throw new IllegalArgumentException("De cumilatieve waarde mag niet groter zijn dan 12!");
        } else if (!(checkPositie(steentje,x,y))) {
            throw new IllegalArgumentException("Je steentje moet verbonden zijn met een ander steentje dat je niet deze ronde hebt gelegd!");
        } else if (spelBord[y][x].getKleur() == 2) {
            Integer[] scores = checkScore(steentje,x,y);
            for (int i = 0; i<scores.length;i++){
                if (i ==10 || i ==11 || i ==12){
                    kanLeggen = true;
                }
            }
            if (kanLeggen){

                spelBord[y][x].setSteentje(steentje);
            }else {
                throw new IllegalArgumentException("Als je een steentje op een grijs vakje wilt leggen moet je cumulatieve waarde 10,11 of 12 zijn!");
            }
        } else {

            spelBord[y][x].setSteentje(steentje);
        }
    }
    /**
     * Deze methode laat toe een steentje te leggen in de eerste ronde
     * @param steentjesIndex
     */
    public void legSteentjeEersteRonde(int x, int y,int steentjesIndex){
        Steentje steentje = spelerhand.get(steentjesIndex);
        spelBord[y][x].setSteentje(steentje);

    }
    /**
     * Deze methode verwijdert een steentje uit de hand van de huidige speler
     * @param gekozen
     */
    public void verwijderSteentje(int gekozen){
        spelerhand.remove(gekozen); //verwijdert steentje uit de hand
    }
    /**
     * Deze methode reset het juist gelegde steentje
     */
    public void resetJuistGelegd(){
        juistGelegd = new ArrayList<Vakje>();
    }
    /**
     * Deze methode geeft het Scoreblad
     */
    public void geefScoreblad() {
    	
    }
}
