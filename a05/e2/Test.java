package a05.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png fornita (e successive),  
     * che realizza un gioco in cui il player P va a caccia del nemico E muovendosi di casella in casella, e il nemico E
     * cerca di sfuggire con movimenti random laddove in pericolo. In particolare:
     * 1 - si considerino nel prosieguo che due celle sono "vicine" se lo sono in orizzontale/verticale/diagonale
     * 2 - all'inizio vengono posizionati in modo random il player P e il nemico E, in modo che NON siano vicini
     * 3 - se l'utente clicka su una cella vicina a P, P si sposta su tale cella
     * 4 - se P finisce vicino ad E (ossia E è attaccato), e E si può spostare su una cella vicina non attaccata da P, allora E si sposta lì
     * 4b -- fra le varie celle dove E si può spostare in base alla regola qui sopra, se ne scelga una random
     * 4c -- se E non ha nessuna via di fuga allora non si muove
     * 5 - se P finisce sopra E la partita finisce, tutte le celle si disabilitano, e l'applicazione non fa più nulla.
     * 
     * In Fig 1-5 è mostrato un esempio di partita:
     * - fig 1: situazione iniziale
     * - fig 2: P viene spostato in giù, E scappa salendo in alto-sx
     * - fig 3: P viene spostato a sx, E scappa scendendo in basso-sx
     * - fig 4: P viene spostato in basso-sx, E non può scappare, e sta quindi fermo
     * - fig 5: P mangia E
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione dei casi 4b e 4c qui sopra (ossia va bene anche se il nemico non si muove in modo random, e se tenta sempre la fuga) 
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        new GUI(7); 
    }
}
