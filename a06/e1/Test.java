package a06.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia ZipCombinerFactory come indicato nel metodo initFactory qui sotto. 
	 * Realizza una factory per un concetto di ZipCombiner, ossia una funzionalità per combinare 
	 * due liste in una lista risultante, come variante del classico "zip" (che è la prima semplice
	 * funzionalità da realizzare).
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento)
	 * - la buona progettazione della soluzione, cercando di riusare codice, e desumere l'algoritmo generico che cattura
	 * tutti e 4 i casi.
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per riuso di codice)
	 * 
	 */

	private ZipCombinerFactory factory;

	@org.junit.Before
	public void initFactory() {
		// this.factory = new ZipCombinerFactoryImpl();

	}

	@org.junit.Test
	public void testClassical() {
		ZipCombiner<Integer, String, Pair<Integer, String>> zc = this.factory.classical();
		// una lista di coppie formata da un elemento di l1 (0,1,2) e uno di l2 (a,b,c,d), assumendo l2 non più piccola di l1
		// il risulato è quindi la lista di coppie (<0,a>, <1,b>, <2,c>), mentre la parte restante di l2 viene sempre scartata.
		assertEquals(
			List.of(new Pair<>(0,"a"), new Pair<>(1,"b"),new Pair<>(2,"c")),
			zc.zipCombine(List.of(0,1,2), List.of("a","b","c","d")));
	}

	@org.junit.Test
	public void testFilter() {
		ZipCombiner<Integer, String, String>  zc = this.factory.mapFilter(i -> i % 2 == 0, s -> s + ";");
		// se l'elemento di l1 (0,1,2,3) è pari, allora si prende il corrispondente elemento di l2 (a,b,c,d,...), e gli si aggiunge un ";"
		assertEquals(
			List.of("a;", "c;"),
			zc.zipCombine(List.of(0,1,2, 3), List.of("a","b","c","d", "e")));
	}

	@org.junit.Test
	public void testTaker() {
		ZipCombiner<Integer, String, List<String>>  zc = this.factory.taker();
		// preso un numero 'n' da l1 (1,0,3), crea la lista dei prossimi 'n' numeri di l2 (a,b,c,d,...)
		// ossia prende da l2 la sottolista di lunghezza 1 (a), poi 0 (), poi 3 (b,c,d), scartando al solito il resto
		assertEquals(
			List.of(List.of("a"), List.of(), List.of("b","c","d")),
			zc.zipCombine(List.of(1,0,3), List.of("a","b","c","d", "e","f","g","h")));
	}

	@org.junit.Test
	public void testCounter() {
		ZipCombiner<String, Integer, Pair<String, Integer>>  zc = this.factory.countUntilZero();
		// preso un elemento di l1 (A,B,C), conta quanti elementi consecutivi ci sono in l2 fino al prossimo '0'
		// ossia la prima volta 10, 20 (lunghezza 2), poi 30,40,50 (lunghezza 3), infine 60 (lunghezza 1)
		assertEquals(
			List.of(new Pair<>("A",2), new Pair<>("B",3), new Pair<>("C",1)),
			zc.zipCombine(List.of("A", "B", "C"), List.of(10,20,0,30,40,50,0,60,0,70,80,90,100)));
	}

}