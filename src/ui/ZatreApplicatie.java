package ui;

import domein.DomeinController;
import domein.Speler;
import domein.Vakje;
import exceptions.OnvoldoendeKredietException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
/**
 * Deze klasse is de ZatreApplicatie voor het spel Zatre
 * @author G48
 * @version 1.0
 *
 */
public class ZatreApplicatie {
    private final DomeinController dc;
    Scanner sc = new Scanner(System.in);
    /**
     * De constructor voor de ZatreApplicatie
     * @param dc
     */
    public ZatreApplicatie(DomeinController dc) {
        this.dc = dc;

    }
    /**
     * Deze methode toont het startmenu
     */
    public void startMenu() {
        boolean run = true;
        while (run) {
            try {
                this.selectOption();
            } catch (InputMismatchException e) {
                System.out.println("Gelieve enkel een nummer in te voeren.");
                sc.next();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Nummer is geen geldige keuze.");
                sc.next();
            }

        }
    }
    /**
     * Deze methode toont de menu options dat je kan selecteren
     */
    public void selectOption() {
        int selection;
        System.out.println("Wat wilt u doen? \n"
                + "1: Speler registreren \n"
                + "2: Spelers selecteren \n"
                + "3: Toon Spelers \n"
                + "4: Start Spel(console backup) \n"
                + "9: Programma beeindigen");
        System.out.print("Uw selectie: ");

        selection = sc.nextInt();
        switch (selection) {
            case 1 -> createUser();
            case 2 -> {
                try {
                    selectSpeler();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (OnvoldoendeKredietException e) {
                    System.out.println(e.getMessage());
                }

            }
            case 3 -> this.toonSpelers();
            case 4 -> {
                try {
                    this.startSpel();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }

            }
            case 9 -> {
                System.out.println("Programma sluit zichzelf af...");
                System.exit(0);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + selection);
        }

    }
    /**
     * Deze methode laat toe het spel te spelen
     */
    public void startSpel() { //console backup versie
        dc.startSpel("Normal");
        System.out.println("Dit is een backup console versie");
        int selection = 0;
        boolean eersteSteentje = true;
        toonSpelBord(); //debug functie
        boolean beurtBezig;
        int ronde = 0; //houd ronde bij voor bonus
        boolean spelAfgebroken = false;
        boolean isEerstBeurt = true;
        boolean eersteHand = true;
        while (!dc.getSpel().getPotLeeg()) { //zolang spel bezig is
            ronde++;//ronde eindigt pas als elke speler aan de beurt is geweest
            for (int i = 0; i < dc.getSelectedSpelers().size(); i++) { //elke speler krijgt een beurt
                Speler s = dc.getSelectedSpelers().get(i); //angezien dat speler word aangepast tijdens de for iteratien we op index en net met foreach
                dc.geefSteentjes(eersteHand); //geeft de speler zijn steentjes
                beurtBezig = true;
                eersteHand = false;
                dc.resetGelegd();

                while (beurtBezig) {


                    System.out.printf("De huidige speler is %s, de waarde van je steentjes zijn:%n", s.getNaam());
                    for (int j = 0; j < this.dc.getSpel().getSpelerhand().size(); j++) {
                        System.out.printf("Steentje %d:%d%n", j, this.dc.getSpel().getSpelerhand().get(j).getGetal());
                    }
                    System.out.println("Wat wil je doen?\n"
                            + "1: Leg steentje\n"
                            + "2: Steek steentje in pot\n"
                            + "3: Toon spelbord\n"
                            + "4: Toon score\n"
                            + "5: Stop spel(voer 2 keer in)\n"

                            + "9: forceer einde spel(debug)");
                    try {
                        selection = sc.nextInt();
                    }catch (InputMismatchException e){
                        sc.next();
                        System.out.println("Gelieve enkel een nummer in te voeren");
                    }

                    switch (selection) {
                        case 1 -> {
                            if (eersteSteentje) {
                                System.out.println("Het eerste steentje moet verplicht in het midden liggen!");
                                System.out.println("Geef aan welk steentje je wilt leggen(1-3)");
                                int gekozen = sc.nextInt();

                                try {

                                    dc.legSteentjeEersteRonde(7,7,gekozen);
                                    System.out.println("steentje gelegd");
                                    dc.updateScoreBlad(i,gekozen,7,7, ronde);
                                    System.out.println("scoreblad geupdate");
                                    dc.verwijderSteentje(gekozen);
                                    System.out.println("Steentje correct gelegd!");
                                    eersteSteentje = false;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println(e.getMessage());
                                }


                            } else if (isEerstBeurt) {
                                System.out.println("Geef aan welk steentje je wilt leggen");
                                int gekozen = sc.nextInt();
                                System.out.println("Geef de X en Y coordinaten van waar je je steentje wilt leggen (1-15)");
                                System.out.print("X:");
                                int x = sc.nextInt();
                                System.out.print("Y:");
                                int y = sc.nextInt();
                                if ((x > 0 || x <= 15) && (y > 0 || y <= 15)) {
                                    x--;
                                    y--;
                                    try {
                                        dc.legSteentjeEersteBeurt(x, y, gekozen);
                                        dc.updateScoreBlad(i,gekozen,x,y,ronde);
                                        dc.verwijderSteentje(gekozen);
                                        System.out.println("Steentje correct gelegd!");
                                        isEerstBeurt = false;

                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println(e.getMessage());
                                    } catch (InputMismatchException e){
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } else {
                                System.out.println("Geef aan welk steentje je wilt leggen");
                                int gekozen = sc.nextInt();
                                System.out.println("Geef de X en Y coordinaten van waar je je steentje wilt leggen (1-15)");
                                System.out.print("X:");
                                int x = sc.nextInt();
                                System.out.print("Y:");
                                int y = sc.nextInt();
                                if ((x > 0 || x <= 15) && (y > 0 || y <= 15)) {
                                    x--;
                                    y--;
                                    try {
                                        dc.legSteentje(x, y, gekozen);
                                        dc.updateScoreBlad(i,gekozen,x,y,ronde);
                                        dc.verwijderSteentje(gekozen);
                                        System.out.println("Steentje correct gelegd!");

                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println(e.getMessage());
                                    } catch (InputMismatchException e){
                                        System.out.println(e.getMessage());
                                    }
                                }

                            }
                        }
                        case 2 -> {
                            System.out.println("Geef aan welk steentje je terug wilt leggen");
                            int gekozen = sc.nextInt();
                            try {
                                dc.steekSteentjeInPot(gekozen);
                            } catch (Error e) {
                                e.printStackTrace();
                            }
                        }
                        case 3 -> {
                            toonSpelBord();
                        }
                        case 4 -> {
                            toonScoreBlad(i);
                        }
                        case 5 ->{
                            spelAfgebroken = true;
                            dc.getSpel().setPotLeeg(true);
                            beurtBezig = false;
                            dc.getSpel().setSpelerhand(new ArrayList<>());
                        }
                        case 9 -> {
                            dc.getSpel().setPotLeeg(true);
                            beurtBezig = false;
                            dc.getSpel().setSpelerhand(new ArrayList<>());
                        }
                    }
                    beurtBezig = dc.isEindeBeurt(); //check of de beurt nog bezig is
                }

            }
        }

        if(spelAfgebroken){
            System.out.println("Spel is afgebroken zonder winnaar.");
        }else {
            for (int i = 0; i < dc.getSelectedSpelers().size(); i++) {
                System.out.printf("Scoreblad van speler %s is:%n", dc.getSelectedSpelers().get(i).getNaam());
                toonScoreBlad(i);
            }
            Speler winnaar = dc.getSpel().bepaalWinnaar();
            System.out.printf("De winaar is %s, zijn nieuwe speelkansen zijn %d%n", winnaar.getNaam(),winnaar.getAantalSpeelbeurten());
        }

    }
    /**
     * Deze methode roept een nieuwe user aanmaken aan 
     */
    public void createUser() {
        String spNaam;
        int spGebJaar;

        System.out.print("Geef de naam in van de speler: ");
        spNaam = sc.next();
        System.out.print("Geef het geboortejaar in van de speler: ");
        spGebJaar = sc.nextInt();
        Speler newSpeler = dc.registreer(spNaam, spGebJaar);
        if (newSpeler != null) {
            System.out.println("Speler " + newSpeler.getNaam() + " met geboortejaar " + newSpeler.getGeboorteDatum() + " is aangemaakt.");
        } else {
            System.out.println("Deze speler bestaat al.");
        }
    }
    /** 
     * Deze methode toont alle spelers
     */
    public void toonSpelers() {
        List<Speler> spelers = dc.getSpelers();
        for (Speler sp : spelers) {
            System.out.printf("%d. naam: %s, geboortejaar: %d, Credits: %d \n", sp.getUserID(), sp.getNaam(), sp.getGeboorteDatum(), sp.getAantalSpeelbeurten());
        }
    }
    /**
     * Deze methode laat toe een speler te selecteren
     */
    public void selectSpeler() {
        List<Speler> spelers = dc.getSpelers();
        System.out.printf("Selecteer het nummer van de speler die je wilt selecteren : \n");
        toonSpelers();
        int index = sc.nextInt();
        //TODO: fix this shit
        dc.selecteerSpeler(spelers.get(index - 1).getNaam(), spelers.get(index - 1).getGeboorteDatum());
        Speler selectedSpeler = spelers.get(index - 1);
        System.out.println("Speler " + selectedSpeler.getNaam() + " met geboortejaar " + selectedSpeler.getGeboorteDatum() + " is Geselecteerd.\n");

    }
    /**
     * Deze methode toont het spelbord
     */
    public void toonSpelBord() {
        System.out.println("[ 1] [ 2] [ 3] [ 4] [ 5] [ 6] [ 7] [ 8] [ 9] [10] [11] [12] [13] [14] [15]");

        Vakje[][] spelbord = this.dc.getSpel().getSpelBord();
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (spelbord[y][x].getVakjeBevatSteentje()) {
                    System.out.printf("[%d ] ", spelbord[y][x].getSteentje().getGetal());
                } else if (spelbord[y][x].getKleur() == 2) {
                    System.out.printf("[X ] ");
                } else if (spelbord[y][x].getKleur() == 1) {
                    System.out.printf("[  ] ");
                } else if (spelbord[y][x].getKleur() == 0) {
                    System.out.printf("[. ] ");
                }

            }
            System.out.println();
        }
    }
    /**
     * Deze methode toont het scoreblad
     * @param spelerIndex
     */
    public void toonScoreBlad(int spelerIndex){
        int totaal = 0;
        System.out.println("[Dubbel][  10  ][  11  ][  12  ][ bonus][totaal]");
        for (int i = 0; i < dc.getSelectedSpelers().get(spelerIndex).getScoreBlad().getScoreblad().size(); i++) {
            for (int j = 0; j < dc.getSelectedSpelers().get(spelerIndex).getScoreBlad().getScoreblad().get(i).length; j++) {
                System.out.printf("%6d", dc.getSelectedSpelers().get(spelerIndex).getScoreBlad().getScoreblad().get(i)[j]);
            }
            System.out.printf("%6d%n", dc.getSelectedSpelers().get(spelerIndex).getScoreBlad().geefScoreRow(i));
            totaal += dc.getSelectedSpelers().get(spelerIndex).getScoreBlad().geefScoreRow(i);
        }
        System.out.printf("Het totaal van speler %s tot nu toe is: %d%n", dc.getSelectedSpelers().get(spelerIndex).getNaam(), totaal);
    }
}
