package a01b.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare i due estremi (alto-basso) di un rombo e quindi diegnarlo
     * (più precisamente, il rombo è un quadrato orientato "in diagonale"):
     * 1 - l'utente clicka due celle che devono definire la parte alta e bassa (in qualunque ordine) di un rombo 
     * (devono essere celle sulla stessa colonna, a distanza multiplo di 2)
     * 2 - ad ognuno dei due click, se corretto, si mostra un * (come quelli in figura)
     * 3 - se il secondo click non è corretto, tutte le selezioni scompaiono e si devo ricominciare daccapo
     * 4 - se il secondo click è corretto invece, si metta un "o" in ogni cella ulteriore del rombo, come in figura
     * 5 - al click successivo, ovunque esso sia, l'applicazione parta daccapo
     *
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione di correttezza del secondo click (click non corretti possono essere ignorati)
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
