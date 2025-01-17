package a02c.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia ReplacerFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di Replacer,
	 * ossia una funzionalità che data in ingresso una lista e un elemento, modifica 
	 * la lista sostituendo a certe occorrenze dell'elemento una nuova sotto-lista, 
	 * dipendentemente dall'implementazione. Fornisce quindi in generale n soluzioni,
	 * una per ogni occorrenza sostituita.
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

	private ReplacersFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new ReplacersFactoryImpl();
	}

	@org.junit.Test
	public void testNone() {
		// un replacer che non sostituisce nulla, quindi non fornisce soluzioni
		var replacer = this.factory.noReplacement();
		assertEquals(List.of(), replacer.replace(List.of(10,20,30), 10));
		assertEquals(List.of(), replacer.replace(List.of(10,20,30), 11));
	}

	@org.junit.Test
	public void testDuplicateFirst() {
		// un replacer che duplica la prima occorrenza di un certo numero, quindi fornisce 0 o 1 soluzione
		var replacer = this.factory.duplicateFirst();
		// c'è il 10, sostituisce la sua prima occorrenza con 10,20,30
		assertEquals(List.of(List.of(10, 10, 20, 30)), replacer.replace(List.of(10,20,30), 10));
		assertEquals(List.of(List.of(0, 10, 10, 20, 10)), replacer.replace(List.of(0, 10,20,10), 10));
		// l'11 non c'è: nessuna soluzione
		assertEquals(List.of(), replacer.replace(List.of(0, 10,20,10), 11));
	}

	@org.junit.Test
	public void testTranslateLastWith() {
		// un replacer che sostituisce all'ultima occorrenza di un certo numero, una certa sottolista (-1, -1)
		var replacer = this.factory.translateLastWith(List.of(-1,-1));
		// Il 10 c'è: la sua ultima (e unica) occorrenza è sostituita con -1,-1
		assertEquals(List.of(List.of(-1, -1, 20, 30)), replacer.replace(List.of(10,20,30), 10));
		// Il 10 c'è più volta: la sua ultima occorrenza è sostituita con -1,-1
		assertEquals(List.of(List.of(0, 10, 20, -1, -1)), replacer.replace(List.of(0, 10,20,10), 10));
		// L'11 non c'è, nessuna soluzione
		assertEquals(List.of(), replacer.replace(List.of(0, 10,20,10), 11));
	}

	@org.junit.Test
	public void testRemoveEach() {
		// un replacer che rimuove una a una ogni occorrenza di un certo numero, dando n soluzioni in generale
		var replacer = this.factory.removeEach();
		// c'è un solo 10, si ha una soluzione dove viene rimosso
		assertEquals(List.of(List.of(20, 30)), replacer.replace(List.of(10,20,30), 10));
		// ci sono due 10, si hanno due soluzioni dove vengono rimossi uno alla volta
		assertEquals(List.of(
			List.of(0,20,10),
			List.of(0, 10,20)
		), replacer.replace(List.of(0, 10,20,10), 10));
		// L'11 non c'è, nessuna soluzione
		assertEquals(List.of(), replacer.replace(List.of(0, 10,20,10), 11));
	}

	@org.junit.Test
	public void testReplaceEachFromSequence() {
		// le prime 3 occorrenze del numero sono rimpiazzate da -100, -101 e -102, rispettivamente
		// dando quindi al massimo 3 soluzioni (ma meno se ci sono meno occorrenze)
		var replacer = this.factory.replaceEachFromSequence(List.of(-100, -101, -102));
		// 1 sola occorrenza, sostituita con -100
		assertEquals(List.of(List.of(0, -100, 20, 30)), replacer.replace(List.of(0,10,20,30), 10));
		// 2 occorrenze, sostituite con -100 e -101 rispettivamente
		assertEquals(List.of(
			List.of(0, -100, 20,10),
			List.of(0, 10,20, -101)
		), replacer.replace(List.of(0, 10,20,10), 10));
		// 3 occorrenze...
		assertEquals(List.of(
			List.of(-100, 10, 10, 10),
			List.of(10, -101, 10, 10),
			List.of(10, 10, -102, 10)
		), replacer.replace(List.of(10, 10, 10, 10), 10));
		// 0 occorrenze...
		assertEquals(List.of(), replacer.replace(List.of(0, 10,20,10), 11));
	}
}