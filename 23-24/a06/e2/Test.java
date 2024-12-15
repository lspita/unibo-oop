package a06.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare un gioco che simula una variante del gioco di carte "memory", dove si 
     * dispongono delle carte coperte, e bisogna trovare coppie di carte uguali alzandone due alla volta, prima una e poi l'altra 
     * (e rigirandole se non sono uguali fra loro)
     * Nello specifico il game da realizzare dovrà funzionare così:
     * 
     * 1 - si crea una griglia N*N (N è il parametro del costruttore), e in ogni cella si associa un numero random compreso fra 1 e 6,
     * che però non verrà mostrato inizialmente
     * 2 - l'utente clicka due celle (non già disabilitate) e il valore ad esse associato viene mostrato
     * 3 - se i due valori sono uguali le due celle vengonio disabilitate perché si è trovata una coppia, 
     *     altrimenti al prossimo click i loro valori vengono ri-nascosti
     * 4 - si procede come da punto 2: quando nelle celle non disabilitate rimangono solo valori tutti diversi tra loro, 
     *     il gioco è finito, e quindi si disabilitino tutte le celle
     *     (l'apllicazione non si chiude, rimane bloccata così)
     * 
     * In Fig 1-7 è mostrato un esempio di partita, a pezzi:
     * - fig 1: situazione iniziale, tutto nascosto
     * - fig 2: dopo aver clickato sulle prime due celle (hanno valore diverso, quindi verranno poi ri-nascoste)
     * - fig 3: dopo aver clickato sulle altre due celle della prima riga (hanno valore diverso, quindi verranno poi ri-nascoste)
     * - fig 4: avendo visto nella prima fila ci sono due 4, posso fare una coppia, quindi clicko su un 4, e...
     * - fig 5: clicko sull'altra cella che so contenere un 4, formando una coppia che viene quindi disabilitata subito
     * ... la partita procede, disabilitando via via tutte le coppie trovate...
     * - fig 6: rimangono tre celle che non possono fare coppie, quindi la partita termina e tutto viene disabilitato
     * (l'apllicazione non si chiude, rimane bloccata così)
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione disabilitazioni
     * - gestione del fine partita
     * N.B: è tassativo ri-nascondere le coppie di celle selezionate se (e solo se) non hanno valore uguale
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 4 punti
	 * - correttezza della parte opzionale: 3 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        new GUI(4); 
    }
}
