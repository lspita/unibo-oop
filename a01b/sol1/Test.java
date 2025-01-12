package a01b.sol1;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Stream;

public class Test {

	/*
	 * Implementare l'interfaccia IteratorCombiners come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per creare iteratori standard
	 * (java.util.Iterator) combinando due iteratori esistenti.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento)
	 * - la buona progettazione della soluzione, utilizzando soluzioni progettuali che portino a
	 * codice succinto che evita ripetizioni e sprechi di memoria
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria (e assenza di difetti al codice): 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per buon design)
	 * 
	 */

	private IteratorsCombiners factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new IteratorsCombinersFactory();
	}

	// una utility per facilitare il testing, che converte iteratori in liste
	private <X> List<X> toList(Iterator<X> iterator){
		return Stream.iterate(iterator, Iterator::hasNext, it -> it).map(Iterator::next).toList();
	}

	@org.junit.Test
	public void testAlternate() {
		// alternando fra l'iteratore che produce (10,20,30) e quello che produce (40,50) si ottiene
		// un iteratore che produce (10,40,20,50,30), e analogamente per gli altri casi...
		assertEquals(List.of(10,40,20,50,30), 
			toList(this.factory.alternate(List.of(10,20,30).iterator(), List.of(40,50).iterator())));
		assertEquals(List.of(10,40,20,30), 
			toList(this.factory.alternate(List.of(10,20,30).iterator(), List.of(40).iterator())));
		assertEquals(List.of(10,40,50,60), 
			toList(this.factory.alternate(List.of(10).iterator(), List.of(40,50,60).iterator())));
	}

	@org.junit.Test
	public void testSequence() {
		// mettendo in sequenza l'iteratore che produce (10,20,30) e quello che produce (40,50) si ottiene
		// un iteratore che produce (10,20,30,40,50), e analogamente per gli altri casi...
		assertEquals(List.of(10,20,30,40,50), 
			toList(this.factory.seq(List.of(10,20,30).iterator(), List.of(40,50).iterator())));
		assertEquals(List.of(10,20,30,40), 
			toList(this.factory.seq(List.of(10,20,30).iterator(), List.of(40).iterator())));
		assertEquals(List.of(10,40,50,60), 
			toList(this.factory.seq(List.of(10).iterator(), List.of(40,50,60).iterator())));
	}

	@org.junit.Test
	public void testMap2() {
		// sommando (con map2) i risultati degli iteratori che producono (10,20,30) e (100,200) si ottiene
		// l'iteratore che produce (10+100, 20+200) -- e ignora il 30. Analogamente per gli altri casi...
		assertEquals(List.of(110,220), 
			toList(this.factory.map2(List.of(10,20,30).iterator(), List.of(100,200).iterator(), (x,y)->x+y)));
		assertEquals(List.of(110), 
			toList(this.factory.map2(List.of(10,20,30).iterator(), List.of(100).iterator(), (x,y)->x+y)));
		assertEquals(List.of(110), 
			toList(this.factory.map2(List.of(10).iterator(), List.of(100,200,300).iterator(), (x,y)->x+y)));
	}

	@org.junit.Test
	public void testZip() {
		// zippando i risultati degli iteratori che producono (10,20,30) e (100,200) si ottiene
		// l'iteratore che produce le coppie (<10,100>, <20,200>) -- e ignora il 30. Analogamente per gli altri casi...
		assertEquals(List.of(new Pair<>(10,100), new Pair<>(20,200)), 
			toList(this.factory.zip(List.of(10,20,30).iterator(), List.of(100,200).iterator())));
		assertEquals(List.of(new Pair<>(10,100)), 
			toList(this.factory.zip(List.of(10,20,30).iterator(), List.of(100).iterator())));
		assertEquals(List.of(new Pair<>(10,100)), 
			toList(this.factory.zip(List.of(10).iterator(), List.of(100,200,300).iterator())));
	}
}