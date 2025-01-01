package a01c.sol1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia TimeSheetFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di Timesheet, 
	 * catturato dall'interfaccia Timesheet: essenzialmente è una tabella con giorni
	 * (tipicamente giorni di un mese) nelle colonne, e attività lavorative nelle
	 * righe (ad esempio: "didattica", "ricerca progetto 1", "ricerca progetto 2",...)
	 * dove in ogni cella si riportano quante ore (>=0) sono state svolte in
	 * un certo giorno per una certa attività.
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

	private TimeSheetFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new TimeSheetFactoryImpl();
	}

	private List<Pair<String, String>> basicData(){
		// Un esempio di dati per un timesheet, usato nei test qui sotto
		return List.of(
				new Pair<>("act1", "day1"),
				new Pair<>("act1", "day2"),
				new Pair<>("act1", "day2"), // quindi due ore dell'atività act1 nel day2
				new Pair<>("act1", "day3"),
				new Pair<>("act1", "day3"),
				new Pair<>("act1", "day3"), // quindi tre ore dell'atività act1 nel day2
				new Pair<>("act2", "day3"));
	}

	private void testExampleTimeSheet(TimeSheet sheet) {
		// un insieme di test per un timesheet ottenuto da basicData()
		assertEquals(Set.of("act1", "act2"), sheet.activities()); // due attività estratte dai dati del timesheet
		assertEquals(Set.of("day1", "day2", "day3"), sheet.days()); // tre giornate estratte dai dati del timesheet
		assertEquals(1, sheet.getSingleData("act1", "day1"));
		assertEquals(2, sheet.getSingleData("act1", "day2")); // 2 ore presenti nei dati
		assertEquals(3, sheet.getSingleData("act1", "day3")); // 3 ore presenti nei dati
		assertEquals(0, sheet.getSingleData("act2", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
	}

	@org.junit.Test
	public void testOfRawData() {
		// il timesheet ottenuto da una lista vuota non ha attività e giorni 
		TimeSheet empty = this.factory.ofRawData(List.of());
		assertEquals(Set.of(), empty.activities());
		assertEquals(Set.of(), empty.days());

		// il timesheet ottenuto da basicData() passa il test di testExampleTimeSheet
		testExampleTimeSheet(this.factory.ofRawData(basicData()));
	}

	@org.junit.Test
	public void testWithBoundsPerActivity() {
		TimeSheet sheet = this.factory.withBoundsPerActivity(
			basicData(),
			Map.of("act1", 7, "act2", 7) // max 7 ore su act1, e 7 su act2
		);
		testExampleTimeSheet(sheet);
		// è un timesheet valido
		assertTrue(sheet.isValid());

		// Stesso test qui sotto, ma con vincoli più stringeni, che non lo rendono valido
		TimeSheet sheet2 = this.factory.withBoundsPerActivity(
			basicData(),
			Map.of("act1", 4, "act2", 3)
		);
		testExampleTimeSheet(sheet2);
		assertFalse(sheet2.isValid());
	}

	@org.junit.Test
	public void testWithBoundsPerDay() {
		TimeSheet sheet = this.factory.withBoundsPerDay(
			basicData(),
			Map.of("day1", 8, "day2", 8, "day3", 8) // max 8 ore su day,...
		);
		testExampleTimeSheet(sheet);
		// è un timesheet valido
		assertTrue(sheet.isValid());

		// Stesso test qui sotto, ma con vincoli più stringeni, che non lo rendono valido
		TimeSheet sheet2 = this.factory.withBoundsPerDay(
			basicData(),
			Map.of("day1", 2, "day2", 2, "day3", 2)
		);
		testExampleTimeSheet(sheet2);
		assertFalse(sheet2.isValid());
	}

	@org.junit.Test
	public void testWithBounds() {
		TimeSheet sheet = this.factory.withBounds(
			basicData(),
			Map.of("act1", 7, "act2", 4), // max 7 ore su act1...
			Map.of("day1", 8, "day2", 8, "day3", 8) // max 8 ore su day1...
		);
		testExampleTimeSheet(sheet);
		// è un timesheet valido
		assertTrue(sheet.isValid());

		// Stesso test qui sotto, ma con vincoli più stringeni, che non lo rendono valido
		TimeSheet sheet2 = this.factory.withBounds(
			basicData(),
			Map.of("act1", 4, "act2", 4),
			Map.of("day1", 2, "day2", 2, "day3", 2)
		);
		testExampleTimeSheet(sheet2);
		assertFalse(sheet2.isValid());
	}
}
