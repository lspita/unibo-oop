package a03b.sol2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di identificare un insieme di triangoli, finché non si colpisce un target:
     * 1 - all'inizio, si posizioni in modo random un "o" nella griglia, come in fig1
     * 2 - l'utente clicka su una cella qualunque della griglia: da qui parte un triangolo alla sua destra, che si estende nelle direzioni
     * su-destra, giù-destra, finché  non collide con un bordo della griglia: ad esempio in fig2 si è clickato sulla cella di coordinata
     * (3,3) (considerando l'origine in alto-sinistra) -- in fig3 e fig4 altri due esempi di triangoli, formati clickando sull oro vertice di sinistra;
     * 3 - se il triangolo non colpisce (ossia va sopra) la "o", allora sia data la possibilità all'utente di ritentare, come da punto 2
     * 4 - se il traingolo colpisce la "o", si chiuda l'applicazione
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
        new GUI(20, 10); 
    }
}
