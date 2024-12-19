package a06.e1;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Si consulti la documentazione della interfaccia FluentParserFactory, che modella
 * una factory per FluentParser, che a sua volta modella un parser (riconoscitore) di
 * singole sequence (infinite) di valori, che ne riceve uno alla volta restituendo ogni volta il 
 * prossimo parser da utilizzare.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo factory (ossia, a scelta se ne realizzino 4 -- il primo obbligatorio)
 * - elementi di qualità come concisione del codice, uso di pattern, rimozione di ripetizioni
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti  
 * - qualità della soluzione: 3 punti
 * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento del punteggio 
 *   complessivo, anche in caso di bonus
 * - ATTENZIONE: non tentare nessun approccio alla rimozione di ripetizioni fra le varie factory può comportare 
 *   un decurtamento del punteggio anche in caso di bonus (1-2 punti)
 */

public class Test {

	private FluentParserFactory factory;

	@org.junit.Before
	public void init() {
		// this.factory = new FluentParserFactoryImpl();
	}

	@org.junit.Test
	public void testNaturals() {
		var parser = this.factory.naturals();
		var outParser = parser.accept(0)
				.accept(1)
				.accept(2)
				.accept(3)
				.accept(4);
		
		assertThrows(IllegalStateException.class, ()->outParser.accept(4));
	}

	@org.junit.Test
	public void testIncrementingNaturalLists() {
		var parser = this.factory.incrementalNaturalLists();
		var outParser = parser.accept(List.of())
				.accept(List.of(0))
				.accept(List.of(0,1))
				.accept(List.of(0,1,2))
				.accept(List.of(0,1,2,3))
				.accept(List.of(0,1,2,3,4));
		
		assertThrows(IllegalStateException.class, ()->outParser.accept(List.of(0,1,2,3,4,5,6)));
	}

	@org.junit.Test
	public void testRepetitiveIncrementingNaturals() {
		var parser = this.factory.repetitiveIncrementalNaturals();
		var outParser = parser.accept(0)
				.accept(0)
				.accept(1)
				.accept(0)
				.accept(1)
				.accept(2)
				.accept(0);
		
		assertThrows(IllegalStateException.class, ()->outParser.accept(2));
	}

	@org.junit.Test
	public void testRepetitiveIncrementingStrings() {
		var parser = this.factory.repetitiveIncrementalStrings("a");
		var outParser = parser.accept("a")
				.accept("a")
				.accept("aa")
				.accept("a")
				.accept("aa")
				.accept("aaa")
				.accept("a");
		
		assertThrows(IllegalStateException.class, ()->outParser.accept("a"));
	}

	@org.junit.Test
	public void testIncrementalPairs() {
		var parser = this.factory.incrementalPairs(0, i->i+2, "a");
		var outParser = parser.accept(new Pair<>(0,List.of()))
		.accept(new Pair<>(2,List.of("a","a")))
		.accept(new Pair<>(4,List.of("a","a","a","a")));
		
		assertThrows(IllegalStateException.class, ()->outParser.accept(new Pair<>(4,List.of("a","a","a","a"))));
	}
}
