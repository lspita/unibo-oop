package a04.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, 
     * la cui logica permette di giocare una sorta di partita a scacchi, dove il giocatore umano muove
     * una torre ("R", ossia rook) mentre il computer muove un re ("K", ossia king) in modo random 
     * (ma mangiando la torre se ci riesce). 
     * 
     * L'applicazione si comporti complessivamente come segue:
     * 1) all'inizio si posizionano R e K in modo random su due celle diverse
     * 2) l'utente clicka una posizione valida d'arrivo per la sua torre R
     * -- se è valida (ossia corrisponde ad un movimento in orizzontale o verticale di quante caselle si vuole,
     *    ma senza saltare oltre il re) allora la torre si trasferisce lì
     * -- se la torre si mette al posto del re, ossia lo mangia, si scriva "Vittoria" su console e si ricomincia 
     *    la partita d'accapo, ossia dal punto 1)
     * 3) mossa la torre, il computer muove subito il re K, come segue:
     * -- le sue mosse possibili sono verso una casella adiacente in orizzontale, verticale o diagonale
     * -- se il re può mangiare la torre muovendosi, allora la mangia: in tal caso si chiuda l'applicazione
     * -- altrimenti il re si muove in modo random in una delle celle consentite
     * 4) si torna al punto 2
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - controllo che la torre non salti sopra il re: ossia, va bene anche se si accetta che la torre salti 
     *   oltre il re, contrariamente a quanto avviene negli scacchi
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
        new GUI(4); 
    }
}
