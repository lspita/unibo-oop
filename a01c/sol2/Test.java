package a01c.sol2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un rettangolo e quindi riempirlo:
     * 1 - l'utente clicka quattro celle che devono definire vertici consecutivi di un rettangolo, nell'ordine
     * alto-sx, alto-dx, basso-dx, basso-sx
     * 2 - ad ogni click corretto, si mostra un numero incrementale: 1,2,3,4
     * 3 - se c'è un click non corretto, tutte le selezioni scompaiono e si devo ricominciare daccapo
     * 4 - arrivati al 4 (corretto), si metta un "*" in ogni cella interna al rettangolo identificato dai 4 vertici
     * 5 - al click successivo, ovunque esso sia, l'applicazione parta daccapo
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - ordine dei click sui vertici (può essere qualunque, a piacimento)
     * - gestione della ripartenza del gioco
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria (e assenza di difetti al codice): 10 punti
	 * - qualità del codice: 4 punti
	 * - correttezza della parte opzionale (e assenza di difetti al codice): 3 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        new GUI(10); 
    }
}
