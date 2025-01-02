package a02a.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia ListBuilderFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di ListBuilder,
	 * ossia un oggetto immutabile utilizzabile per facilitare la creazione di liste
	 * con struttura articolata (intento del pattern Builder).
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento --
	 * i primi 3 sono comunque obbligatori)
	 * - la buona progettazione della soluzione, utilizzando soluzioni progettuali
	 * che portino a
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

	private ListBuilderFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new ListBuilderFactoryImpl();
	}

	@org.junit.Test
	public void testEmpty() {
		// empty() rappresenta il builder di una lista vuota
		ListBuilder<Integer> empty = this.factory.<Integer>empty();
		assertEquals(List.of(), empty.build());
		// se gli aggiungo 10 e 20 diventa il builder di una lista (10, 20)
		assertEquals(List.of(10, 20),
				empty.add(List.of(10, 20))
						.build());
		// posso fare due add consecutive, concatenando le chiamate
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.add(List.of(30))
						.build());
		// con la concat ottengo un builder che rappresenta la concatenazione delle liste
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.concat(empty.add(List.of(30)))
						.build());
		// altro esempio con la concat
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.concat(empty.add(List.of(30)))
						.build());
	}

	@org.junit.Test
	public void testFromElement() {
		// fromElement() rappresenta il builder di una lista con un elemento
		ListBuilder<Integer> one = this.factory.fromElement(1);
		// add e concat funzionano come ci si aspetta
		assertEquals(List.of(1), one.build());
		assertEquals(List.of(1, 2, 3, 4),
				one.add(List.of(2, 3, 4)).build());
		assertEquals(List.of(1, 2, 1),
				one.concat(this.factory.fromElement(2))
						.concat(one)
						.build());
	}

	@org.junit.Test
	public void testBasicFromList() {
		// fromList() rappresenta il builder di una lista con n elementi
		ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
		assertEquals(List.of(1, 2, 3), l.build());
		// concat funzionano come ci si aspetta
		assertEquals(List.of(1, 2, 3, 1, 2, 3),
				l.concat(l).build());
		// replaceAll qui rimpiazza gli "1" con coppie "-1, -2"
		assertEquals(List.of(-1, -2, 2, 3, -1, -2, 2, 3),
				l.concat(l)
						.replaceAll(1, this.factory.fromList(List.of(-1, -2)))
						.build());
		// se non c'è match, la replaceAll non fa nulla
		assertEquals(List.of(1, 2, 3, 1, 2, 3),
				l.concat(l)
						.replaceAll(10, this.factory.fromList(List.of(-1, -2)))
						.build());
	}

	@org.junit.Test
	public void testReverseFromList() {
		ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
		assertEquals(List.of(1, 2, 3), l.build());
		// la reverse fa ottenere un builder che rappresenta la lista invertita
		assertEquals(List.of(3, 2, 1), l.reverse().build());
		assertEquals(List.of(1, 2, 3), l.reverse().reverse().build());
		assertEquals(List.of(1, 2, 3, 3, 2, 1),
				l.reverse().reverse().concat(l.reverse()).build());
	}

	@org.junit.Test
	public void testJoin() {
  		// la join è usabile per concatenare più builder, con un elemento iniziale e uno finale
		assertEquals(List.of("(", "1", "2", "3", "4",")"),
				this.factory.join("(", ")", 
						List.of(this.factory.fromElement("1"), 
								this.factory.fromElement("2"),
								this.factory.fromList(List.of("3","4")))).build());
	}

}