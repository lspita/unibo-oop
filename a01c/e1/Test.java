package a01c.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia SimpleIteratorFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei SimpleIterator, che hanno
	 * solo un metodo per ottenere il prossimo elemento (che però potrebbe fallire se 
	 * l'iterazione è finita).
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento)
	 * - la buona progettazione della soluzione, utilizzando soluzioni progettuali che portino a
	 * codice succinto che evita ripetizioni e sprechi di memoria.
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria (e assenza di difetti al codice): 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per buon design)
	 */

	private SimpleIteratorFactory factory;

	@org.junit.Before
	public void initFactory() {
		// this.factory = new SimpleIteratorFactoryImpl();
	}

	// una utility per facilitare il testing, che verifica se i prossimi elementi generati dall'iteratore 
	// corrispondono alla lista expected
	private <X> boolean nextElements(List<X> expected, SimpleIterator<X> iterator){
		return expected.stream().allMatch(e -> e.equals(iterator.next()));
	}
	
	@org.junit.Test
	public void testNaturals(){
		// naturals produce 0,1,2,3,...
		var it = this.factory.naturals();
		assertEquals(0, it.next().intValue());
		assertEquals(1, it.next().intValue());
		assertEquals(2, it.next().intValue());
		assertEquals(3, it.next().intValue());
		for (int i = 0; i < 100; i++){
			it.next();
		}
		assertEquals(104, it.next().intValue());
		assertTrue(nextElements(List.of(105, 106, 107), it));
	}

	@org.junit.Test
	public void testCircularList(){
		// produce "a", "b", "c", "a",... all'infinito
		var it = this.factory.circularFromList(List.of("a","b","c"));
		assertTrue(nextElements(List.of("a","b","c","a","b","c","a"), it));
	}

	@org.junit.Test
	public void testCut(){
		// dopo i primi 5 elementi, ogni chiamata a next fallisce
		var it = this.factory.cut(5, this.factory.naturals());
		assertEquals(0, it.next().intValue());
		assertEquals(1, it.next().intValue());
		assertEquals(2, it.next().intValue());
		assertEquals(3, it.next().intValue());
		assertEquals(4, it.next().intValue());
		assertThrows(NoSuchElementException.class, () -> it.next());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}

	@org.junit.Test
	public void testWindow2(){
		// produce coppie successive a partire dall'iteratore in ingresso, qui naturals
		var it = this.factory.window2(this.factory.naturals());
		assertEquals(new Pair<>(0, 1), it.next());
		assertEquals(new Pair<>(1, 2), it.next());
		assertEquals(new Pair<>(2, 3), it.next());
		assertEquals(new Pair<>(3, 4), it.next());
		assertEquals(new Pair<>(4, 5), it.next());
		for (int i = 0; i < 100; i++){
			it.next();
		}
	}

	@org.junit.Test
	public void testSums(){
		// produce la somma delle coppie successive, indefinitamente 
		var it = this.factory.sumPairs(this.factory.circularFromList(List.of(10,20,30,40)));
		assertEquals(10+20, it.next().intValue());
		assertEquals(20+30, it.next().intValue());
		assertEquals(30+40, it.next().intValue());
		assertEquals(40+10, it.next().intValue());
		assertEquals(10+20, it.next().intValue());
	}

	@org.junit.Test
	public void testWindow(){
		// generalizza window2, ma producendo liste di n elementi, 3 in questo caso
		var it = this.factory.window(3, this.factory.circularFromList(List.of(10,20,30,40)));
		assertEquals(List.of(10,20,30), it.next());
		assertEquals(List.of(20,30,40), it.next());
		assertEquals(List.of(30,40,10), it.next());
		assertEquals(List.of(40,10,20), it.next());
		assertEquals(List.of(10,20,30), it.next());
		for (int i = 0; i < 100; i++){
			it.next();
		}

		// iteratore su finestre di 5 elementi a partire da naturals
		it = this.factory.window(5, this.factory.naturals());
		assertEquals(List.of(0,1,2,3,4), it.next());
		assertEquals(List.of(1,2,3,4,5), it.next());
		for (int i = 0; i < 100; i++){
			it.next();
		}
		assertEquals(List.of(102,103,104,105,106), it.next());
	}
}