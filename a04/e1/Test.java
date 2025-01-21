package a04.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia ListExtractorFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di ListExtractor,
	 * catturato dall'omonima interfaccia: essenzialmente contiene una funzione pura
	 * che data una lista estrae una sua certa sottosequenza e la usa per produrre,
	 * elemento per elemento, un risultato.
	 * Nell'esercizio completo, va applicato riuso via erediterietà.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento)
	 * - la buona progettazione della soluzione, utilizzando per tutte le varie versioni
	 * del ListExtractor riuso via ereditarietà (ossia, nella parte obbligatoria
	 * va bene se il riuso via ereditarietà è usato per almeno due ListExtractors)
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per buon design)
	 * 
	 */

	private ListExtractorFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new ListExtractorFactoryImpl();
	}

	@org.junit.Test
	public void testHead() {
		// un estrattore che produce il primo elemento della lista, se disponibile
		final ListExtractor<Integer, Optional<Integer>> le = this.factory.head();
		assertEquals(Optional.of(10), le.extract(List.of(10,20,30,40,50)));
		assertEquals(Optional.of(10), le.extract(List.of(10)));
		assertEquals(Optional.empty(), le.extract(List.of()));
	}

	@org.junit.Test
	public void testCollectUntil() {
		final ListExtractor<Integer, List<Integer>> le = this.factory.<Integer, Integer>collectUntil(x -> x + 1, x -> x >= 30);
		// raccoglie gli elementi della lista finché non raggiungono il 30, sommando uno ad ognuno
		assertEquals(List.of(11, 21), le.extract(List.of(10,20,30,40,50)));
		assertEquals(List.of(11), le.extract(List.of(10,50,20,40,50)));
		assertEquals(List.of(), le.extract(List.of(30,50,20,40,50)));
		assertEquals(List.of(), le.extract(List.of()));
	}

	@org.junit.Test
	public void testScanFrom() {
		final ListExtractor<Integer, List<List<Integer>>> le = this.factory.<Integer>scanFrom(x -> x >= 30);
		// raccoglie gli elementi da quando ce ne è uno >= 30 fino in fondo, producendo liste di liste incrementali
		assertEquals(List.of(List.of(30), List.of(30,20), List.of(30,20,50)), le.extract(List.of(10,20,30,20,50)));
		assertEquals(List.of(List.of(30)), le.extract(List.of(30)));
		assertEquals(List.of(), le.extract(List.of(10,20,25)));
	}

	@org.junit.Test
	public void testCount() {
		final ListExtractor<String, Integer> le = this.factory.countConsecutive("a");
		// conta quante "a" successive ci sono a partire dalla prima occorrenza
		assertEquals(3, le.extract(List.of("b", "a", "a","a","c","d","a")).intValue());
		assertEquals(1, le.extract(List.of("a", "b", "a", "a","a","c","d","a")).intValue());
		assertEquals(1, le.extract(List.of("b", "c", "d","a")).intValue());
		assertEquals(0, le.extract(List.of("b", "c", "d")).intValue());
		assertEquals(0, le.extract(List.of()).intValue());
	}
}