package a02b.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare una cella dove far apparire quella figura quadrata,
     * per poi selezionare un vertice dove questa si deve spostare, come indicato sotto:
     * 1 - l'utente clicka su una cella qualunque della griglia, e quindi appare la configurazione di "*" indicata in (fig 1), centrata su dove si è clickato,
     * si noti che tale configurazione presenta 4 celle vuote, in corrispondenza delle 4 direzioni diagonali
     * 2 - clickando su una di questa quattro celle la configurazione di "*" si sposta verso il
     * vertice corrispondente: ad esempio, clickando sulla cella vuota in alto-dx adiacente a dove si fece click nel punto precedente,
     * la configurazione si sposta nel vertica in alto a destra, come mostrato in fig2.
     * 3 - si continua come indicato al punto procedente, ricevendo i click su una delle quattro celle, spostandosi di conseguenza
     * 4 - quando si clicka la cella centrale della configurazione, l'applicazione si chiuda
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione di tutte e quattro le direzioni, ossia il click sulle quattro celle (basta gestirne due a scelta a piacimento)
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        new GUI(10); 
    }
}
