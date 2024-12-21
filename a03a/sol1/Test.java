package a03a.sol1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia WindowingFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di Windowing, ossia
	 * un particolare tipo di trasformazione da sequenze a sequenze.
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

	private WindowingFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new WindowingFactoryImpl();
	}

	@org.junit.Test
	public void testTrivial() {
		Windowing<String, String> windowing = this.factory.trivial();
		// l'input corrisponde all'output
		assertEquals(Optional.of("a"), windowing.process("a"));
		assertEquals(Optional.of("b"), windowing.process("b"));
		assertEquals(Optional.of("a"), windowing.process("a"));
	}

	@org.junit.Test
	public void testPairing() {
		Windowing<Integer, Pair<Integer, Integer>> windowing = this.factory.pairing();
		// gli ultimi due input forniti a process formano un pair, la prima volta Optional.empty
		assertEquals(Optional.empty(), windowing.process(1));
		assertEquals(Optional.of(new Pair<>(1, 3)), windowing.process(3));
		assertEquals(Optional.of(new Pair<>(3, 2)), windowing.process(2));
		assertEquals(Optional.of(new Pair<>(2, 1)), windowing.process(1));
	}

	@org.junit.Test
	public void testSumFour() {
		Windowing<Integer, Integer> windowing = this.factory.sumLastFour();
		// gli ultimi quattro input forniti a process producono la loro somma, le prime 3 volte Optional.empty
		assertEquals(Optional.empty(), windowing.process(1));
		assertEquals(Optional.empty(), windowing.process(10));
		assertEquals(Optional.empty(), windowing.process(100));
		assertEquals(Optional.of(1111), windowing.process(1000)); //1+10+100+1000
		assertEquals(Optional.of(1112), windowing.process(2)); // 10+100+1000+2
		assertEquals(Optional.of(1122), windowing.process(20)); // 100+1000+2+20
	}

	@org.junit.Test
	public void testLastN() {
		Windowing<Integer, List<Integer>> windowing = this.factory.lastN(4);
		// gli ultimi N input forniti a process producono una lista, le prime N-1 volte Optional.empty
		assertEquals(Optional.empty(), windowing.process(1));
		assertEquals(Optional.empty(), windowing.process(10));
		assertEquals(Optional.empty(), windowing.process(100));
		assertEquals(Optional.of(List.of(1, 10, 100, 1000)), windowing.process(1000));
		assertEquals(Optional.of(List.of(10, 100, 1000, 2)), windowing.process(2));
		assertEquals(Optional.of(List.of(100, 1000, 2, 20)), windowing.process(20));
	}

	@org.junit.Test
	public void testSumAtLeast() {
		Windowing<Integer, List<Integer>> windowing = this.factory.lastWhoseSumIsAtLeast(10);
		// la lista degli ultimi elementi la cui somma è almeno N (N=10 in questo caso) producono una lista
		assertEquals(Optional.empty(), windowing.process(5)); // ancora non si è giunti a 10
		assertEquals(Optional.empty(), windowing.process(3)); // ancora non si è giunti a 10
		assertEquals(Optional.empty(), windowing.process(1)); // ancora non si è giunti a 10
		assertEquals(Optional.of(List.of(5,3,1,1)), windowing.process(1)); // 5+3+1+1 >= 10
		assertEquals(Optional.of(List.of(5,3,1,1, 2)), windowing.process(2)); // 5+3+1+1+2 >= 10, mentre 3+1+1+2 < 10
		assertEquals(Optional.of(List.of(3,1,1, 2, 4)), windowing.process(4)); // 3+1+1+2+4 >= 10, mentre 1+1+2+4 < 10
		assertEquals(Optional.of(List.of(4, 8)), windowing.process(8)); // etc...
		assertEquals(Optional.of(List.of(20)), windowing.process(20));
	}

}

