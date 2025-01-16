package a02b.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia RulesEngineFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di "motore di regole",
	 * catturato dall'interfaccia RulesEngine: essenzialmente è un sistema di riscrittura
	 * di sequenze di dati (liste), che a partire da un input produce svariati output,
	 * applicando regole di riscrittura.
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

	private RulesEngineFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new RulesEngineFactoryImpl();
	}

	@org.junit.Test
	public void testApplyRule() {
		// test del metodo che realizza l'applicazione di una sola regola
		// regola: 10 --> (11,12)
		// se l'input è (1,10,100) e ha quindi un solo 10, si ottiene una unica soluzione, ossia (1,11,12,100)
		assertEquals(List.of(List.of(1, 11, 12,100)),
			this.factory.applyRule(new Pair<>(10, List.of(11, 12)), List.of(1, 10, 100)));

		// regola: a --> (b)
		// se l'input è (a,a,a) si ottengono 3 soluzioni, in ognuna si sostituisce una a con b, in ordine
		assertEquals(List.of(List.of("b", "a", "a"),List.of("a", "b", "a"),List.of("a", "a", "b")),
			this.factory.applyRule(new Pair<>("a", List.of("b")), List.of("a", "a", "a")));

		// regola: 10 --> ()
		// se l'input è (1,10,100) si ha una sola soluzione, dove il 10 viene rimpiazzato dal nulla, ossia è rimosso
		assertEquals(List.of(List.of(1,100)),
			this.factory.applyRule(new Pair<>(10, List.of()), List.of(1, 10, 100)));

		// regola: 10 --> (11,12,13)
		// se l'input è (1,100) non c'è nessuna soluzione
		assertEquals(List.of(),
			this.factory.applyRule(new Pair<>(10, List.of(11, 12, 13)), List.of(1, 100)));
	}

	@org.junit.Test
	public void testSingle() {
		// singleRule crea un RulesEngine, che funziona con la logica del metodo sopra, solo che:
		// 1 - applica la regola da sx a dx finché riesce, quindi si ha sempre una sola soluzione
		// 2 - si deve poter resettare l'input per ricominciare
		
		// regola: a --> (b,c)
		var res = this.factory.singleRuleEngine(new Pair<>("a", List.of("b", "c")));
		// input: (a,z,z)
		res.resetInput(List.of("a","z","z"));
		// una sola soluzione
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("b", "c", "z", "z"), res.nextSolution());
		assertFalse(res.hasOtherSolutions());

		// input: (a,z,a)
		res.resetInput(List.of("a","z","a"));
		// una sola soluzione, trasformando entrambe le "a"
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("b", "c", "z", "b", "c"), res.nextSolution());
		assertFalse(res.hasOtherSolutions());

		// input: (z, z)
		res.resetInput(List.of("z", "z"));
		// nessuna trasformazione possibile, quindi ho una sola soluzione, che non modifica l'input
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("z", "z"), res.nextSolution());
		assertFalse(res.hasOtherSolutions());
	}

	@org.junit.Test
	public void testCascadingRules() {
		// Si testa il caso di due regole, in cui il risultato delle prima fa ottenere
		// una sequenza che attiva la seconda regola
		// a --> (b,c), e c-->d
		// di fatto qui, abbiamo lo stesso risultato che avremmo con la regola: a-->b,d e c-->d
		var res = this.factory.cascadingRulesEngine(
			new Pair<>("a", List.of("b", "c")),
			new Pair<>("c", List.of("d"))
		);
		res.resetInput(List.of("a","z"));
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("b", "d", "z"), res.nextSolution());
		assertFalse(res.hasOtherSolutions());

		// qui la "a" diventa "b,d", e, la "c" diventa "d"
		res.resetInput(List.of("a","c","z"));
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("b", "d", "d", "z"), res.nextSolution());
		assertFalse(res.hasOtherSolutions());
	}

	@org.junit.Test
	public void testConflictingRules() {
		// Si testa il caso di due regole con la medesima "testa", per ogni applicazione della regola
		// si generano 2 casi, le cui soluzioni vanno poi unite (eliminando le duplicazioni).
		// Ossia, ogni volta che si deve applicare la regola, se ne applica una con la SingleRule,
		// l'altra con la SingleRule, e si uniscono i risultati.
		
		// a --> b, e a-->c, d
		var res = this.factory.conflictingRulesEngine(
			new Pair<>("a", List.of("b")),
			new Pair<>("a", List.of("c", "d"))
		);
		res.resetInput(List.of("a","a"));
		// 4 soluzioni, ossia le combinazioni
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("b", "b"), res.nextSolution());
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("b", "c", "d"), res.nextSolution());
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("c", "d", "b"), res.nextSolution());
		assertTrue(res.hasOtherSolutions());
		assertEquals(List.of("c", "d", "c", "d"), res.nextSolution());
		assertFalse(res.hasOtherSolutions());
	}
}