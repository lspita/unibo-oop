package a05.e1;

import static org.junit.Assert.*;

/**
 * Si consulti la documentazione della interfaccia ExpressionParserFactory, che modella
 * una factory per ExpressionParser, che a sua volta modella un parser (riconoscitore) di
 * una espressione matematica (somme di numeri con parentesi, in generale), ricevuta
 * elemento per elemento (da sinistra a destra) attraverso chiamate di metodo successive.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quarto metodo factory (ossia, a scelta se ne realizzino 3)
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

	private ExpressionParserFactory factory;

	@org.junit.Before
	public void init() {
		// this.factory = new ExpressionParserFactoryImpl();
	}

	@org.junit.Test
	public void testOneOp() {
		var parser = this.factory.oneSum();
		// 0 + 1 is accepted
		parser.acceptNumber(0);
		parser.acceptSum();
		parser.acceptNumber(1);
		parser.close();

		// 3 + 1 is accepted
		parser = this.factory.oneSum();
		parser.acceptNumber(3);
		parser.acceptSum();
		parser.acceptNumber(1);
		parser.close();
	}

	@org.junit.Test
	public void testOneFail() {
		final var parser = this.factory.oneSum();
		// should start with a num
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(0);
		// now only a sum
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(10));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		parser.acceptSum();
		// now only a num
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(1);
		// now only close
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(10));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		parser.close();
	}

	@org.junit.Test
	public void testZeroOrManySums() {
		var parser = this.factory.zeroOrManySums();
		// 0  is accepted
		parser.acceptNumber(0);
		parser.close();

		// 3 + 1 is accepted
		parser = this.factory.zeroOrManySums();
		parser.acceptNumber(3);
		parser.acceptSum();
		parser.acceptNumber(1);
		parser.close();

		// 3 + 1 + 2  is accepted
		parser = this.factory.zeroOrManySums();
		parser.acceptNumber(3);
		parser.acceptSum();
		parser.acceptNumber(1);
		parser.acceptSum();
		parser.acceptNumber(2);
		parser.close();
	}

	@org.junit.Test
	public void testZeroOrManySumsFail() {
		final var parser = this.factory.zeroOrManySums();
		// now only a sum
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(0);
		// now could close or sum
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(10));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		parser.acceptSum();
		// now only a num
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(1);
		// now could close or sum
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(10));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		parser.acceptSum();
		// now only a num
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(1);
		// now could close or sum
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(10));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		parser.close();
	}

	@org.junit.Test
	public void testOneLevel() {
		var parser = this.factory.oneLevelParens();
		// 0  is accepted
		parser.acceptNumber(0);
		parser.close();

		// 3 + (1)  is accepted
		parser = this.factory.oneLevelParens();
		parser.acceptNumber(3);
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptNumber(1);
		parser.acceptCloseParen();
		parser.close();

		// 3 + (1 + 2) + (1) + 2  is accepted
		parser = this.factory.oneLevelParens();
		parser.acceptNumber(3);
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptNumber(1);
		parser.acceptSum();
		parser.acceptNumber(2);
		parser.acceptCloseParen();
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptNumber(1);
		parser.acceptCloseParen();
		parser.acceptSum();
		parser.acceptNumber(2);
		parser.close();
	}

	@org.junit.Test
	public void testOneLevelFails() {
		var parser = this.factory.oneLevelParens();
		// now could num or openParen
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptOpenParen();
		// now only num
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(1);
		// now could sum or closeParen
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(1));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptSum();
		// now only num
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptNumber(2);
		// now could sum or closeParen
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(1));
		assertThrows(IllegalStateException.class, ()->parser.acceptOpenParen());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptCloseParen();
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptNumber(1);
		parser.acceptCloseParen();
		parser.close();
	}

	@org.junit.Test
	public void testManyOpsWithBoxingParens() {
		var parser = this.factory.manySumsWithBoxingParens();
		parser.acceptOpenParen();
		parser.acceptOpenParen();
		parser.acceptNumber(3);
		parser.acceptCloseParen();
		parser.acceptCloseParen();
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptNumber(1);
		parser.acceptCloseParen();
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptOpenParen();
		parser.acceptOpenParen();
		parser.acceptNumber(2);
		parser.acceptCloseParen();
		parser.acceptCloseParen();
		parser.acceptCloseParen();
		parser.close();
	}

	@org.junit.Test
	public void testManyOpsWithBoxingParensFail() {
		var parser = this.factory.manySumsWithBoxingParens();
		parser.acceptOpenParen();
		parser.acceptOpenParen();
		parser.acceptNumber(3);
		// now only close two parens
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		parser.acceptCloseParen();
		assertThrows(IllegalStateException.class, ()->parser.acceptSum());
		assertThrows(IllegalStateException.class, ()->parser.close());
		parser.acceptCloseParen();
		// back with usual behaviour...
		assertThrows(IllegalStateException.class, ()->parser.acceptNumber(10));
		assertThrows(IllegalStateException.class, ()->parser.acceptCloseParen());
		parser.acceptSum();
		parser.acceptOpenParen();
		parser.acceptNumber(1);
		parser.acceptCloseParen();
	}


}
