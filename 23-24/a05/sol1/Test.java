package a05.sol1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia BankAccountFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di BankAccount classico,
	 * catturato dall'omonima interfaccia, con metodi balance/deposit/withdraw per accedere/aggiungere/prelevare
	 * al conto. E' fornita anche una classe astratta AbstractBankAccount, che può semplificare molto l'implementazione
	 * di qualcuno dei conti correnti richiesti.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi della factory (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno a piacimento)
	 * - la buona progettazione della soluzione, utilizzando ove possibile AbstractBankAccount
	 * per semplificare l'implementazione.
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory)
	 * - qualità della soluzione: 4 punti (per uso corretto e completo di AbstractBankAccount)
	 * 
	 */

	private BankAccountFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new BankAccountFactoryImpl();
	}

	@org.junit.Test
	public void testSimple() {
		var ba = this.factory.simple();
		assertEquals(0, ba.balance()); // inizialmente vuoto
		ba.deposit(1000);
		ba.deposit(500);
		assertEquals(1500, ba.balance()); // deposito 1500
		assertTrue(ba.withdraw(300)); // prelievo 300
		assertEquals(1200, ba.balance()); // rimane 1200
		assertFalse(ba.withdraw(3000)); // non si riesce e prelevare 3000
		assertEquals(1200, ba.balance()); // rimasto a 1200
		assertTrue(ba.withdraw(200)); // prelievo 200
		assertEquals(1000, ba.balance()); // rimane 1000
	}

	@org.junit.Test
	public void testFeeAccount() {
		var ba = this.factory.withFee(1); // tassa 1 per ogni prelievo
		// tutto come prima, ma i prelievi tolgono 1 euro in più...
		assertEquals(0, ba.balance());
		ba.deposit(1000);
		ba.deposit(500);
		assertEquals(1500, ba.balance());
		assertTrue(ba.withdraw(300));
		assertEquals(1199, ba.balance()); // tolto 300 + 1
		assertFalse(ba.withdraw(3000));
		assertEquals(1199, ba.balance());
		assertTrue(ba.withdraw(200)); // tolto 200 + 1
		assertEquals(998, ba.balance());
	}

	@org.junit.Test
	public void testGettingBlocked() {
		var ba = this.factory.gettingBlocked();
		assertEquals(0, ba.balance());
		ba.deposit(1000);
		ba.deposit(500);
		assertEquals(1500, ba.balance());
		assertTrue(ba.withdraw(300));
		assertEquals(1200, ba.balance());
		assertTrue(ba.withdraw(200));
		assertEquals(1000, ba.balance());
		// fin qui tutto normale
		// Il tentativo di depositare una cifra negativa blocca il conto e ogni operazione ora è ignorata
		ba.deposit(-100);
		assertEquals(1000, ba.balance());
		ba.deposit(100);
		assertEquals(1000, ba.balance());
		ba.withdraw(100);
		assertEquals(1000, ba.balance());
	}

	@org.junit.Test
	public void testChecked() {
		var ba = this.factory.checked();
		assertEquals(0, ba.balance());
		ba.deposit(1000);
		ba.deposit(500);
		assertEquals(1500, ba.balance());
		assertTrue(ba.withdraw(300));
		assertEquals(1200, ba.balance());
		assertTrue(ba.withdraw(200));
		assertEquals(1000, ba.balance());
		// fin qui tutto normale
		// depositare ammontare negativo genera eccezione e nulla succede
		assertThrows(IllegalStateException.class, () -> ba.deposit(-100));
		assertEquals(1000, ba.balance());
		// prelevare ammontare negativo genera eccezione e nulla succede
		assertThrows(IllegalStateException.class, () -> ba.withdraw(-100));
		assertEquals(1000, ba.balance());
		// prelevare più di quanto si ha genera eccezione e nulla succede
		assertThrows(IllegalStateException.class, () -> ba.withdraw(2000));
		assertEquals(1000, ba.balance());
		// il conto è ancora usabile
		ba.withdraw(100);
		assertEquals(900, ba.balance());
	}

	@org.junit.Test
	public void testPool() {
		var ba1 = this.factory.simple();
		var ba2 = this.factory.simple();
		// un conto corrente pool, che si appoggia a due conti correnti semplici
		var ba = this.factory.pool(ba1, ba2);
		assertEquals(0, ba1.balance());
		assertEquals(0, ba2.balance());
		assertEquals(0, ba.balance());
		ba.deposit(1000); // va su ba1, che ha meno (o uguale)
		ba.deposit(500);  // va su ba2, che ha meno
		ba.deposit(600);  // va su ba2, che ha meno
		ba.deposit(200);  // va su ba1, che ha meno
		assertEquals(1200, ba1.balance());
		assertEquals(1100, ba2.balance());
		assertEquals(2300, ba.balance());
		assertTrue(ba.withdraw(500)); // preleva dal primo se si riesce
		assertEquals(700, ba1.balance());
		assertEquals(1100, ba2.balance());
		assertEquals(1800, ba.balance());
		assertTrue(ba.withdraw(200)); // preleva dal primo se si riesce
		assertEquals(500, ba1.balance());
		assertEquals(1100, ba2.balance());
		assertEquals(1600, ba.balance());
		assertTrue(ba.withdraw(600)); // non c'è disponibilità nel primo, preleva dal secondo
		assertEquals(500, ba1.balance());
		assertEquals(500, ba2.balance());
		assertEquals(1000, ba.balance());
	}

}