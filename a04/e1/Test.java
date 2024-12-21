package a04.e1;

import static org.junit.Assert.*;

/**
 * Si consulti la documentazione della interfaccia BankAccountFactory, che modella
 * una factory per BankAccount, che a sua volta modella un conto corrente con un certo saldo (balance),
 * e possibilità di deposito soldi (deposit) e prelievo (withdrawal): dipendentemente dall'implementazione
 * il prelievo potrebbe essere soggetto a tasse, blocchi, o credito, o combinazioni di questi.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo factory (ossia, a scelta se ne realizzino 4,
 *   ma considerando il primo metodo testBasic() come obbligatorio)
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

	private BankAccountFactory factory;

	@org.junit.Before
	public void init() {
		// this.factory = new BankAccountFactoryImpl();
	}

	@org.junit.Test
	public void testBasic() {
		var account = this.factory.createBasic();
		// quello che prelievo, se disponible, viene rimosso
		assertEquals(0, account.getBalance());
		account.deposit(100);
		account.deposit(30);
		assertEquals(130, account.getBalance());
		assertTrue(account.withdraw(40));
		assertEquals(90, account.getBalance());
		assertFalse(account.withdraw(200));
		assertEquals(90, account.getBalance());
	}

	@org.junit.Test
	public void testWithFee() {
		var account = this.factory.createWithFee(amount -> amount > 35 ? 1 : 0);
		// quello che prelievo, se disponible, viene rimosso, togliendo un euro in più se prelievo più di 35
		assertEquals(0, account.getBalance());
		account.deposit(100);
		account.deposit(30);
		assertEquals(130, account.getBalance());
		assertTrue(account.withdraw(40));
		assertEquals(89, account.getBalance()); // the fee is applied
		assertTrue(account.withdraw(20));
		assertEquals(69, account.getBalance()); // the fee is NOT applied
		assertFalse(account.withdraw(80));
		assertEquals(69, account.getBalance());
	}

	@org.junit.Test
	public void testWithCredit() {
		var account = this.factory.createWithCredit(credit -> credit < 100, credit -> 10);
		// il conto può andare in "rosso" fino a 99 euro, ma ogni operazione che mi manda in rosso viene tassata di 10 euro
		assertEquals(0, account.getBalance());
		account.deposit(100);
		account.deposit(30);
		assertEquals(130, account.getBalance());
		assertTrue(account.withdraw(60));
		assertEquals(70, account.getBalance()); 
		assertFalse(account.withdraw(170)); // 100 of credit is too much!!
		assertEquals(70, account.getBalance()); 
		assertTrue(account.withdraw(150)); // 80 of credit is ok, but it becomes 80+10!
		assertEquals(-90, account.getBalance()); 
		assertFalse(account.withdraw(20)); // 110 of credit is too much!!
		assertEquals(-90, account.getBalance()); 
		assertTrue(account.withdraw(9)); // 99 of credit is ok, but it becomes 99+10
		assertEquals(-109, account.getBalance()); 
	}

	@org.junit.Test
	public void testWithBlock() {
		var account = this.factory.createWithBlock((amount,balance) -> amount > balance);
		// se si tenta di prelevare più di quello che si ha il conto si blocca e non si può più prelevare nulla
		assertEquals(0, account.getBalance());
		account.deposit(100);
		account.deposit(30);
		assertEquals(130, account.getBalance());
		assertTrue(account.withdraw(60));
		assertEquals(70, account.getBalance()); 
		assertTrue(account.withdraw(30));
		assertEquals(40, account.getBalance()); 
		assertFalse(account.withdraw(50)); // now it is blocked, from now on
		assertEquals(40, account.getBalance()); 
		assertFalse(account.withdraw(10));
		assertEquals(40, account.getBalance()); 
		assertFalse(account.withdraw(2));
		assertEquals(40, account.getBalance());  
	}

	@org.junit.Test
	public void testWithFeeAndCredit() {
		var account = this.factory.createWithFeeAndCredit(amount -> 1, credit -> credit < 100, credit -> 10);
		// tassa fissa 1 euro a prelievo, credito fino a max 100 euro esclusi, e 10 euro di tassa quando si va in rosso
		// ossia una combinazione di Fee e Credit
		assertEquals(0, account.getBalance());
		account.deposit(100);
		account.deposit(30);
		assertEquals(130, account.getBalance());
		assertTrue(account.withdraw(60));
		assertEquals(69, account.getBalance()); 
		assertFalse(account.withdraw(170)); // 100 of credit is too much!!
		assertEquals(69, account.getBalance()); 
		assertTrue(account.withdraw(150)); // 81 of credit is ok, but it becomes 81+11!
		assertEquals(-92, account.getBalance()); 
		assertFalse(account.withdraw(20)); // 110 of credit is too much!!
		assertEquals(-92, account.getBalance()); 
		assertTrue(account.withdraw(5)); // 92+5+1 of credit is ok, but it becomes 108
		assertEquals(-108, account.getBalance()); 
	}
}
