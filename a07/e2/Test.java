package a07.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita:
     * - all'inizio tutti i pulsanti sono "vuoti"
     * - alla pressione di un pulsante, si considerano i 9 pulsanti che formano il suo vicinato, ossia lui stesso
     *   e gli 8 adiacenti (ovviamente il vicinato ha meno elementi se il pulsante è nel bordo)
     * - se la maggioranza dei pulsanti del vicinato ha una '*' allora si svuotano tutti i pulsanti del vicinato
     * - se invece solo la minoranza dei pulsanti del vicinato ha una '*' allora in tutti i pulsanti del vicinato si mette una '*'
     * - quando la maggioranza delle celle complessive ha una '*' si resetti la griglia svuotandola e ricominciando daccapo
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione corretta dei pulsanti nel bordo, ossia si assuma che si clicki solo sulle '*' che non sono nel bordo
     * 
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 4 punti
	 * - correttezza della parte opzionale: 3 punti
     * - errori di programmazione comportano decurtamento del punteggio complessivo
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(7); 
    }
}
