package a01c.sol2;

public class Test {

/*
    * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
    * che realizza la possibilità di selezionare un rettangolo di celle, e poi estenderlo via via fino a uscire dalla griglia:
    * 1 - con i primi due click (su celle diverse), si individuano due celle numerate 1 e 2
    * 2 - effettuato un terzo click (su qualunque cella), tutte le celle del rettangolo individuato dai vertici opposti 1 e 2 vengono numerati 0 (fig2)
    * 3 - al quarto click, vengo numerate 0 anche le celle confinanti con l'attuale rettangolo, e così via ad ogni click
    * 4 - non appena un click porterebbe a coprire tutta la griglia (fig3), l'applicazione si chiuda
    * 
    * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
    * al raggiungimento della totalità del punteggio:
    * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
    * - gestione della posizione dell'1 e del 2 (va bene anche se si fa una assunzione del tipo "1 in alto a sx, 2 in basso a dx")
    *  
    * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
    * 
    * Indicazioni di punteggio:
    * - correttezza della parte obbligatoria: 10 punti
    * - qualità della parte opzionale: 5 punti
	* - correttezza della parte opzionale: 2 punti
    */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(10); 
    }
}
