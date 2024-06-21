package a04.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un insieme di celle, finchè non si individua un percorso a zig-zag
     * che congiunge la prima e l'ultima riga della griglia, come segue:
     * 1 - all'avvio compare subito una * in posizione random della prima riga in alto
     * 2 - l'utente seleziona un insieme di celle clickandoci sopra:
     * -- i click sulla prima riga vanno ignorati
     * -- se riclicka dove già clickato non succede nulla
     * 3 - non appena una pressione definisce un insieme di celle che include un percorso a zig-zag che congiunge la prima e l'ultima riga, 
     * allora tutte le celle si disabilitano, e l'applicazione non fa più nulla.
     * FINE - Un percorso è a zig-zag se ogni cella di una riga è connessa ad una cella della riga sottostante in diagonale basso-sx o basso-dx.
     * NOTA - Si noti che per concludere il gioco, va bene anche se sono selezionate più celle rispetto a quelle del percorso a zig-zag.
     * 
     * In Fig 1 è mostrata una selezione di celle che include un percorso a zig-zag, che ad esempio procede dalla cella della prima
     * riga in questo ordine:
     * - basso-sx, basso-sx, basso-dx, basso-sx, basso-dx, basso-dx
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione della fine del gioco che non consideri la NOTA qui sopra (ossia va bene anche se si controlla che le celle selezionate
     * indichino esattamente il percorso a zig-zag, con nessuna selezione in più). 
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
