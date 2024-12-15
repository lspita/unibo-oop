package a01b.sol2;

public class Test {

	/*
    * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
    * che realizza la possibilità di selezionare 5 celle, per poi farle traslare tutte a sinistra fino al bordo,
    * quindi tutte a destra fino all'altro bordo:
    * 1 - all'inizio l'utente clicka su 5 celle (diverse tra loro), che si numerano incrementalmente (fig 1)
    * 2 - alla sesta pressione di un pulsante qualsiasi, tutte le celle si spostano a sinistra di una posizione (fig 2)
    * 3 - si procede come sopra finchè una cella numerata non finisce nel bordo (fig 3)...
    * 4 - a quel punto ogni altra successiva pressione di qualunque cella porta le celle a spostarsi a destra di una posizione (fig 4, poi 5...)
    * 5 - non appena una pressione causerebbe l'uscita dalla griglia di una cella numerata (fig 6), l'applicazione si chiuda
    * 
    * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
    * al raggiungimento della totalità del punteggio:
    * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
    * - gestione della fine del gioco
    *  
    * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
    * 
    * Indicazioni di punteggio:
    * - correttezza della parte obbligatoria: 10 punti
    * - qualità della parte opzionale: 5 punti
	* - correttezza della parte opzionale: 2 punti
    */


    public static void main(String[] args) {
        new GUI(10); 
    }
}
