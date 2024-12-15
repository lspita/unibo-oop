package a03a.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di identificare una traiettoria con rimbalzo per colpire un target:
     * 1 - all'inizio, si posizioni in modo random un "o" nell'ultima colonna, come in fig1
     * 2 - l'utente clicka su una cella qualunque della griglia: deve comparire in un colpo solo la traccia diagonale verso alto-destra, 
     * che quando raggiunge il lato superiore (o il lato inferiore) rimbalza come mostrato in fig2, finchè non raggiunge l'ultima colonna
     * 3 - se la traccia non colpisce (ossia va sopra) la "o", allora sia data la possibilità all'utente di ritentare, come da punto 2
     * 4 - se la traccia colpisce la "o", si chiuda l'applicazione
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


    public static void main(String[] args) throws java.io.IOException {
        new GUI(20, 7); 
    }
}
