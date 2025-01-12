package a01a.sol2;

import a01a.sol2.GUI;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un insieme di celle, e quindi calcolare il più piccolo rettangolo
     * che le "circoscrive":
     * 1 - l'utente clicka quante celle vuole (non stiano nel bordo della griglia), e in ognuna si mette una "*"
     * (nell'esempio di figura ne ha ckickate 6)
     * 2 - la prima volta che clicka su una cella che ha già la "*", allora si mostri il più piccolo rettangolo
     * che include tutte le "*" all'interno del suo perimetro: lo si disegni come in figura, mettendo 
     * 1,2,3,4 nei vertici (nell'ordine preferito) e "o" altrove
     * 3 - al click successivo, ovunque esso sia, l'applicazione parta daccapo
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - inserimenti dei numeri nei vertici (ci si può mettere quello che si vuole)
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
