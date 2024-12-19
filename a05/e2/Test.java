package a05.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, 
     * la cui logica permette di giocare una mini-partita simile alla dama, dove il giocatore umano muove
     * il pedino H dal basso verso l'alto mentre il computer muove il pedino C dall'alto verso il basso 
     * (il computer muove sempre in modo random, mangiando obbligatoriamente il pedino dell'umano se possibile, 
     * come indicato di seguito). 
     * 
     * L'applicazione si comporti complessivamente come segue:
     * 0) siccome nella dama si gioca solo su caselle nere, si disabilitano metà delle caselle come da figura 
     * 1) all'inizio si posizionano H e C in posizioni random (non disabilitate): C in una delle due prime file
     *    in alto, e H nell'ultima fila in basso
     * 2) l'utente umano clicka una posizione valida d'arrivo per il suo pedino H
     * -- se è valida (ossia corrisponde ad un movimento verso l'alto in una casella contigua, in diagonale 
     *    alto-sx o alto-dx) allora si trasferisce lì
     * -- se in quella posizione si trova invece il pezzo nemico (la C) allora lo si mangia: 
     *    in tal caso la partita ricominci stampando "Vittoria" su console
     * -- se si giunge nella prima fila in alto, allora ci si comporti come in caso di vittoria qui sopra 
     * -- se l'utente clicka su posizione invalida non deve succedere nulla
     * 3) una volta che ha mosso l'utente umano, è quindi il turno del computer C, che muovo allo stesso modo, 
     *    ma dall'alto verso il basso:
     * -- le sue mosse possibili sono verso il basso in diagonale (basso-sx o basso-dx), e fra queste se ne 
     *    sceglia una in modo random
     * -- se è possibile mangiare il pezzo H, allora la partita termini stampando "Sconfitta" su console
     * -- se si giunge nell'ultima fila in basso, allora ci si comporti come in caso di sconfitta qui sopra 
     * 4) si torna al punto 2
     * 
     * Ad esempio, la fig1 fornita rappresenta una posizione iniziale lecita come da punto 0. L'utente umano "H"
     * può solo clickare la posizione diagonale in alto-dx rispetto alla H. A quel punto, il computer può scendere
     * in diagonale basso-sx o basso-dx, e questa scelta viene fatta probabilisticamente. E così via.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al 
     * raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - la possibilità che il computer mangi: ossia, va bene anche se il computer non ti mangia obbligatoriamente quando può
     * 
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 3 punti
	 * - correttezza della parte opzionale: 4 punti
     * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento 
     *   del punteggio complessivo, anche in caso di bonus
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(8); 
    }
}
