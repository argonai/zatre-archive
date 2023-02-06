package main;

import domein.DomeinController;
import ui.ZatreApplicatie;

public class StartUp {
    public static void main(String[] args) {

        new ZatreApplicatie(new DomeinController()).startMenu();
    }
}