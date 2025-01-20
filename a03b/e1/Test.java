package a03b.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia InfiniteIteratorHelpers come indicato nel metodo
	 * initFactory qui sotto. Realizza una interfaccia di funzioni di utilità per un 
	 * concetto di iteratore infinito, catturato dall'interfaccia InfiniteIterator: 
	 * essenzialmente è una iteratore che prosegue a fornire valori all'infinito.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento)
	 * - la buona progettazione della soluzione, utilizzando soluzioni progettuali che portino a
	 * codice succinto che evita ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per buon design)
	 * 
	 */

	private InfiniteIteratorsHelpers iih;

	@org.junit.Before
	public void initFactory() {
		this.iih = new InfiniteIteratorHelpersImpl();
	}

	@org.junit.Test
    public void testValue() {
        // Test su sequenze costituite da un solo elemento
        assertEquals(List.of("a","a","a","a","a"), iih.of("a").nextListOfElements(5));
        assertEquals(List.of("a"), iih.of("a").nextListOfElements(1));
        assertEquals(List.of("a","a","a","a","a","a","a","a","a","a"), iih.of("a").nextListOfElements(10));
        assertEquals(List.of(1,1,1,1,1,1,1,1,1,1), iih.of(1).nextListOfElements(10));
    }
     
	@org.junit.Test
    public void testCyclic() {
        // Test su sequenze cicliche
        // sequenza: a,b,a,b,a,b,a,b,a,b,....
        assertEquals(List.of("a","b","a","b","a"), iih.cyclic(List.of("a","b")).nextListOfElements(5));
        // sequenza: 1,2,3,1,2,3,1,2,3,1,2,3,1,2,....
        assertEquals(List.of(1,2,3,1,2,3,1,2,3,1), iih.cyclic(List.of(1,2,3)).nextListOfElements(10));
    }
	
	@org.junit.Test
    public void testIncrementing() {
		// Test su costruzione sequenza di incrementi
		assertEquals(List.of(1,3,5,7,9), iih.incrementing(1, 2).nextListOfElements(5));    	
        assertEquals(List.of(0,-3,-6,-9), iih.incrementing(0, -3).nextListOfElements(4));    	
    }
	
	@org.junit.Test
    public void testAlternating() {
		// Test su costruzione sequenze alternando
		var i1 = iih.incrementing(1, 1);
		var i2 = iih.of(0);
        assertEquals(List.of(1,0,2,0,3,0), iih.alternating(i1, i2).nextListOfElements(6));
    }

	@org.junit.Test
    public void testWindow() {
		// Test su costruzione sequenze "finestra"
		assertEquals(List.of(
				List.of(1,2,3,4),
				List.of(2,3,4,5),
				List.of(3, 4, 5 ,6)),
			iih.window(iih.incrementing(1, 1), 4).nextListOfElements(3));
			
    }

	
}