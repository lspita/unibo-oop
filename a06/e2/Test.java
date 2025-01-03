package a06.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, 
     * e la cui logica preveda di far collassare (per somma) coppie di celle adiacenti in verticale 
     * con stesso valore, finché possibile, come segue:
     * 
     * - la pressione delle celle della griglia non ha mai alcun effetto;
     * - all'inizio si riempia la griglia con valori random 1 o 2;
     * - alla pressione del pulsante "Fire", in ogni colonna si tenta un (e non più d'un) "collasso di due celle" 
     *   ossia, data una certa colonna si cercano le prime due celle consecutive dal basso che hanno 
     *   stesso valore: una volta trovate, le si collassa in un'unica cella il cui valore è la somma dei due; 
     *   per "gravità verso il basso", questo deve comportare lo svuotamento progressivo dei valori nelle colonne 
     *   dall'alto;
     * - ad esempio, considerando la griglia di fig1, premendo "Fire" si giunge come da fig2, premendo ancora 
     *   come da fig3, poi fig4, e infine in fig5;
     * - come mostrato in fig5, quando nessuna colonna ha due celle collassabili, allora il gioco finisce,
     *   e il pulsante "Fire" va disabilitato
     * 
     *  Ad esempio in fig1, nella prima colonna si trovano due "1" in seconda e terza riga (dal basso): premuto 
     *  il pulsante "Fire" nella prima colonna questi due "1" diventano un solo "2", e si crea un buco in alto,
     *  come si evince da fig2. Situazione analoga la si ottiene in fig1 sulla seconda colonna (due "2" diventano
     *  un "4"), sulla terza colonna (i due "1" in basso diventano un "2") eccetera.
     *  
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al 
     * raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione della disabilitazione del pulsante "Fire"
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 4 punti
	 * - correttezza della parte opzionale: 3 punti
     * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento del punteggio 
     *   complessivo, anche in caso di bonus
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(5); 
    }
}
