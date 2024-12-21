package a02c.sol2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di indicare un quadrato iniziale, per poi espanderlo trascinando un vertice verso l'esterno,
     * di volta in volta:
     * 1 - inizialmente l'utente clicka su una cella qualunque della griglia, che diventa il centro di un quadrato di '*'
     * come mostrato in fig1
     * 2 - clickando su uno qualunque dei 4 vertici del quadrato, questo si allarga espandendo il vertice verso l'esterno di una cella:
     * ad esempio, da fig1:
     * -- clickando sul suo vertice in alto-dx, ci si porta come da fig2
     * -- poi clickando ancora sul suo vertice alto-dx, ci si porta come da fig3
     * -- poi clickando sul suo vertice basso-sx, ci si porta come da fig4
     * -- e così via
     * 3 - alla prima pressione su una cella che farebbe toccare al quadrato il bordo della griglia si chiuda l'applicazione
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione di tutte e quattro le direzioni, ossia il click sui quattro vertici (basta gestirne solo due a scelta a piacimento)
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */


    public static void main(String[] args) {
        new GUI(10); 
    }
}
