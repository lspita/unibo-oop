package a02a.sol2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un insieme di celle vuote disposte su una griglia di celle non contigue,
     * fino a che gli ultimi 4 click non individuano 4 celle "o" che formano un quadrato minimale:
     * 1 - all'avvio la griglia di celle riporta delle "*" come mostrato in fig.1, lasciando vuote delle celle
     * che formano una sottogriglia di celle tutte a distanza 2 una dall'altra
     * 2 - l'utente clicka su celle vuote (altrimenti il click viene ignorato): ad ogni click valido la cella riporta "o"
     * 3 - l'applicazione si chiude quando (e se) gli ultimi 4 click corrispondono a quattro celle diventate "o" che
     * sono vicine (a distanza 2) una dall'altra e formano un quadrato minimale -- ad esempio se dalla situazione di figura
     * 2 si clickasse la cella vuota in alto a sinistra, le quattro "o" presenti formerebbero un quadrato minimale, e quindi
     * si dovrebbe uscire.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione della fine del gioco (che potrà essere semplificata, richiedendo che le ultime 4 celle "o" formino il quadrato
     * richiesto ma impostato in un certo ordine, ad esempio: alto-sx, poi alto-dx, poi basso-dx, poi basso-sx).
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
