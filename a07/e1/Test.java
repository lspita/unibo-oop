package a07.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia Task che modella una computazione
 * resettabile, da eseguire passo-passo, e che può mostrare ogni volta il risultato ottenuto finora.
 * Ad esempio, un conteggio, un ordinamento, la produzione di una certa sequenza, eccetera.
 * Per i Task è prevista una factory TaskFactory per generarne di 3 tipi diversi.
 * C'è poi un interfaccia TaskExecutor, che modella un esecutore (con un paio di modalità diverse) 
 * per un Task.  
 * L'obiettivo è realizzare una implementazione per TaskFactory (3 metodi) e una per
 * TaskExecutor (2 metodi).
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - far passare tutti i 6 test (ossia, a scelta se ne facciano passare 4)
 * - concisione del codice e rimozione di tutte le ripetizioni con eventuale uso appropriato di pattern
 *
 * Si tolgano i commenti dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 3 punti  
 * - qualità della soluzione (eliminando ripetizioni di codice): 4 punti
 * - errori di programmazione comportano decurtamento del punteggio complessivo
 */

public class Test {

	private TaskExecutor executor;
	private TaskFactory taskFactory;

	@org.junit.Before
	public void init() {
		//this.executor = new TaskExecutorImpl();
		//this.taskFactory = new TaskFactoryImpl();
	}


	@org.junit.Test
	public void testCounterForever() {
		// task di conteggio
		var task = this.taskFactory.counter();
		var iterator = this.executor.executeForever(task);
		// eseguo il task di conteggio all'infinito
		assertTrue(iterator.hasNext());
		assertEquals(0, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(1, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.next().intValue());
		assertTrue(iterator.hasNext());
		assertEquals(3, iterator.next().intValue());
		for (int i=0; i<100; i++){
			iterator.next();
		}
		assertEquals(104, iterator.next().intValue());
	}


	@org.junit.Test
	public void testCounterWithBound() {
		// task di conteggio
		var task = this.taskFactory.counter();
		// eseguo il task di conteggio finché il risultato è >= 4, oppure al massimo 100 volte
		assertEquals(
			Optional.of(4), //risultato: 4
			this.executor.executeUntilConditionOrBound(task, i -> i >= 4, 100)
		);
		// eseguo il task di conteggio finché il risultato è <-1, oppure al massimo 1000 volte
		assertEquals(
			Optional.empty(), // dopo 1000 passi mi fermo e torno empty
			this.executor.executeUntilConditionOrBound(task, i -> i < -1, 1000)
		);
	}

	@org.junit.Test
	public void testFibonacciForever() {
		// task di produzione di una sequenza di fibonacci, un elemento alla volta
		var task = this.taskFactory.fibonacciSequenceCreator();
		var iterator = this.executor.executeForever(task);
		// eseguo il task all'infinito
		assertTrue(iterator.hasNext());
		assertEquals(List.of(0), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(List.of(0,1), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(List.of(0,1,1), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(List.of(0,1,1,2), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(List.of(0,1,1,2,3), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(List.of(0,1,1,2,3,5), iterator.next());
		assertTrue(iterator.hasNext());
	}


	@org.junit.Test
	public void testFibonacciWithBound() {
		// task di produzione di una sequenza di fibonacci, un elemento alla volta
		var task = this.taskFactory.fibonacciSequenceCreator();
		// eseguo il task finché non produco un elemento maggiore di 30, oppure al massimo 1000 volte
		assertEquals(
			Optional.of(List.of(0,1,1,2,3,5,8,13,21,34)),
			this.executor.executeUntilConditionOrBound(task, l -> l.get(l.size()-1) >= 30, 1000)
		);
		// eseguo il task finché non produco un elemento maggiore di 10000 (in cosa alla lista), oppure al massimo 10 volte
		assertEquals(
			Optional.empty(), // dopo 10 volte mi fermo...
			this.executor.executeUntilConditionOrBound(task, l -> l.get(l.size()-1) >= 10000, 10)
		);
	}

	@org.junit.Test
	public void testRemoverForever() {
		// task di rimozione degli elementi più grandi del 10 dal set, a partire dal più grande
		var task = this.taskFactory.removeBiggerThan(Set.of(11,8,12,6,3,20), 10);
		var iterator = this.executor.executeForever(task);
		// eseguo all'infinito
		assertTrue(iterator.hasNext());
		assertEquals(Set.of(11,8,12,6,3), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(Set.of(11,8,6,3), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(Set.of(8,6,3), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(Set.of(8,6,3), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(Set.of(8,6,3), iterator.next());
		assertTrue(iterator.hasNext());
	}

	@org.junit.Test
	public void testRemoverWithBound() {
		// task di rimozione degli elementi più grandi del 10 dal set, a partire dal più grande
		var task = this.taskFactory.removeBiggerThan(Set.of(11,8,12,6,3,20), 10);
		// eseguo il task di rimozione finchè tutti gli elementi sono minori o uguali a 10, al massimo 1000 volte
		assertEquals(
			Optional.of(Set.of(3,6,8)),
			this.executor.executeUntilConditionOrBound(task, l -> l.stream().allMatch(i -> i<=10), 1000)
		);
		// eseguo il task di rimozione finchè non ho svuotato il set, al massimo 1000 volte
		assertEquals(
			Optional.empty(), // il set non si svuota, eseguo 1000 volte e poi mi fermo...
			this.executor.executeUntilConditionOrBound(task, l -> l.isEmpty(), 1000)
		);
	}	
}
