package a01a.e1;

import static org.junit.Assert.*;

import java.util.*;
import java.util.function.Supplier;

public class Test {

	/*
	 * Implementare l'interfaccia ComposableParserFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di ComposableParser: 
	 * essenzialmente un parser (riconoscitore) di sequenze finite di elementi.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento -- 
	 * considerando che fromList e fromAnyList sono di fatto obbligatori)
	 * - la buona progettazione della soluzione, utilizzando soluzioni progettuali che portino a
	 * codice succinto che evita ripetizioni e sprechi di memoria
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria (e assenza di difetti al codice): 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per buon design)
	 */

	private ComposableParserFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new ComposableParserFactoryImpl();
	}

	@org.junit.Test
	public void testEmpty() {
		// Riconoscitore di sequenza vuota: si deve terminare il parsing subito
		var p1 = this.factory.empty();
		assertTrue(p1.end());
		
		p1 = this.factory.empty();
		assertTrue(this.factory.empty().parseToEnd(List.of()));		
	}

	@org.junit.Test
	public void testOne() {
		// Riconoscitore di sequenza ("a"): si deve riconoscere "a" e poi terminare
		var p1 = this.factory.one("a");
		assertFalse(p1.end());
		
		p1 = this.factory.one("a");
		assertTrue(p1.parse("a"));
		assertTrue(p1.end());

		p1 = this.factory.one("a");
		assertFalse(p1.parse("b"));
		
		// verifica di corretto funzionamento di parseToEnd e parseMany
		assertTrue(this.factory.one("a").parseToEnd(List.of("a")));
		assertTrue(this.factory.one("a").parseMany(List.of("a")));
		assertTrue(this.factory.one("a").parseMany(List.of()));				
	}

	@org.junit.Test
	public void testFromList() {
		// Riconoscitore di sequenza (10,20,30)
		var p1 = this.factory.fromList(List.of(10,20,30));
		assertTrue(p1.parse(10));
		assertTrue(p1.parse(20));
		assertTrue(p1.parse(30));
		assertTrue(p1.end());

		p1 = this.factory.fromList(List.of(10,20,30));
		assertTrue(p1.parseMany(List.of(10,20)));
		assertTrue(p1.parse(30));
		assertTrue(p1.end());

		assertTrue(this.factory.fromList(List.of(10,20,30)).parseMany(List.of(10,20,30)));
		assertTrue(this.factory.fromList(List.of(10,20,30)).parseMany(List.of(10,20)));
		assertTrue(this.factory.fromList(List.of(10,20,30)).parseMany(List.of(10)));
		assertTrue(this.factory.fromList(List.of(10,20,30)).parseToEnd(List.of(10,20,30)));

		assertFalse(this.factory.fromList(List.of(10,20,30)).parseMany(List.of(10,30)));
		assertFalse(this.factory.fromList(List.of(10,20,30)).parseMany(List.of(10,20,30,40)));
		assertFalse(this.factory.fromList(List.of(10,20,30)).parseToEnd(List.of(10,20)));		
	}

	@org.junit.Test
	public void testFromAnyList() {
		// Riconoscitore di (10,20,30) o (10,100,200)
		Set<List<Integer>> input = Set.of(List.of(10,20,30), List.of(10,100,200));
		assertTrue(this.factory.fromAnyList(input).parseMany(List.of(10,20,30)));
		assertTrue(this.factory.fromAnyList(input).parseMany(List.of(10,20)));
		assertTrue(this.factory.fromAnyList(input).parseMany(List.of(10)));
		assertTrue(this.factory.fromAnyList(input).parseMany(List.of(10,100)));
		assertTrue(this.factory.fromAnyList(input).parseMany(List.of(10,100,200)));
		assertTrue(this.factory.fromAnyList(input).parseToEnd(List.of(10,20,30)));
		assertTrue(this.factory.fromAnyList(input).parseToEnd(List.of(10,100,200)));

		assertFalse(this.factory.fromAnyList(input).parseMany(List.of(10,20,200)));
		assertFalse(this.factory.fromAnyList(input).parseMany(List.of(10,100,30)));
		assertFalse(this.factory.fromAnyList(input).parseToEnd(List.of(10,20)));
		assertFalse(this.factory.fromAnyList(input).parseToEnd(List.of(10,100)));
	}

	@org.junit.Test
	public void testOr() {
		// Riconoscitore di (10,20,30) o (10,100,200), oppure (10,2000,3000)
		// nota: p1 e p2 come Supplier vengono usati per generare ogni volta un nuovo parser,
		// 		per evitare di ridefinire le variabili 
		Supplier<ComposableParser<Integer>> p1 = () -> this.factory.fromAnyList(Set.of(List.of(10,20,30), List.of(10,100,200)));
		Supplier<ComposableParser<Integer>> p2 = () -> this.factory.fromList(List.of(10,2000,3000));
		assertTrue(this.factory.or(p1.get(), p2.get()).parseToEnd(List.of(10,20,30)));
		assertTrue(this.factory.or(p1.get(), p2.get()).parseToEnd(List.of(10,100,200)));
		assertTrue(this.factory.or(p1.get(), p2.get()).parseToEnd(List.of(10,2000,3000)));
		
		assertFalse(this.factory.or(p1.get(), p2.get()).parseToEnd(List.of(10,20)));
		assertFalse(this.factory.or(p1.get(), p2.get()).parseToEnd(List.of(10,100,200,3000)));
		assertFalse(this.factory.or(p1.get(), p2.get()).parseToEnd(List.of(10,2000)));
	}

	@org.junit.Test
	public void testSeq() {
		// Riconoscitore di (10,20,30) o (10,100,200), che poi procedere riconoscendo (1000,2000)
		Supplier<ComposableParser<Integer>> p = ()->this.factory.fromAnyList(Set.of(List.of(10,20,30), List.of(10,100,200)));
		assertTrue(this.factory.seq(p.get(), List.of(1000,2000)).parseToEnd(List.of(10,20,30,1000,2000)));
		assertTrue(this.factory.seq(p.get(), List.of(1000,2000)).parseToEnd(List.of(10,100,200,1000,2000)));
		
		assertFalse(this.factory.seq(p.get(), List.of(1000,2000)).parseToEnd(List.of(10,20,30)));
		assertFalse(this.factory.seq(p.get(), List.of(1000,2000)).parseToEnd(List.of(10,20,30,2000)));
	}
}