package a01d.e2;

public class Test {

	/*
    * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
    * che realizza la possibilità di selezionare il centro di un quadrato 5x5, per poi spostarlo su/giù/destra/sinistra
    * con successivi click sul bordo della griglia, finché non esce dalla griglia stessa. Ossia:
    * 1 - all'inizio l'utente seleziona su una cella, e in risposta si evidenziano (con un '*') quelle di un quadrato di lato 5 di cui la selezionata è centro (fig 1)
    * 2 - da quel punto l'utente clicka solo celle del bordo (altrimenti il click viene ignorato), in risposta, il quadrato di celle evidenziate si sposta di 1 in direzione del click, ossia:
    * -- se ho clickato sulla prima riga, il rettangolo si sposta in alto
    * -- se ho clickato sulla ultima riga, il rettangolo si sposta in basso
    * -- se ho clickato sulla prima colonna, il rettangolo si sposta a sinistra
    * -- se ho clickato sulla ultima colonna, il rettangolo si sposta a destra
    * -- i click sui vertici siano trattati a piacimento
    * 3 - se un click causerebbe l'uscita del rettangolo dalla griglia, allora si chiuda l'applicazione.
    *
    * Nelle figure fornite si illustra un caso in cui:
    * fig1) l'utente ha clickato la cella iniziale, e quindi è comparso il rettangolo
    * fig2) l'utente ha clickato nella prima riga della griglia, e il rettangolo si è spostato in su 
    * fig3) l'utente ha clickato ancora nella prima riga della griglia, e il rettangolo si è spostato in su 
    * fig4) l'utente ha clickato nella prima colonna della griglia, e il rettangolo si è spostato a sinistra 
    * fig5) l'utente ha clickato ancora nella prima colonna della griglia, e il rettangolo si è spostato a sinistra
    * a quel punto clockando ancora nella prima colonna della griglia, l'applicazione si chiude 
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


    public static void main(String[] args) throws java.io.IOException {
        new GUI(10); 
    }
}
