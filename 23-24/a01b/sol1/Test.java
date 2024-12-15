package a01b.sol1;

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
	
	@org.junit.Test
	public void testFlat() {
		// un time sheet con 3 attività su 5 giorni, con un'ora al giorno su ciascuna attività su ciascun giorno
		TimeSheet sheet = this.factory.flat(3, 5, 1);
		assertEquals(List.of("act1", "act2", "act3"), sheet.activities());
		assertEquals(List.of("day1", "day2", "day3", "day4", "day5"), sheet.days());
		assertEquals(1, sheet.getSingleData("act1", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
		assertEquals(0, sheet.getSingleData("act22", "day30")); // attività/giorni non presenti, ritorno 0
		assertEquals(Map.of("act1",5, "act2",5,"act3",5), sheet.sumsPerActivity()); // 5 ore per ogni attività
		assertEquals(Map.of("day1",3, "day2",3,"day3",3, "day4",3, "day5",3), sheet.sumsPerDay()); // 3 ore su ogni giorno
	}

	@org.junit.Test
	public void testOfListsOfLists() {
		// un timesheet con 2 attività su 3 giorni, con nomi forniti
		TimeSheet sheet = this.factory.ofListsOfLists(
			List.of("a1","a2"), 
			List.of("d1", "d2", "d3"),
			List.of(
				List.of(1,2,3), // attività a1: 1,2,3 ore nei 3 giorni d1, d2, d3, ordinatamente
				List.of(0,0,1)  // attività a2: 0,0,1 ore nei 3 giorni d1, d2, d3, ordinatamente
			));
		assertEquals(List.of("a1", "a2"), sheet.activities());
		assertEquals(List.of("d1", "d2", "d3"), sheet.days());
		assertEquals(2, sheet.getSingleData("a1", "d2"));
		assertEquals(3, sheet.getSingleData("a1", "d3"));
		assertEquals(0, sheet.getSingleData("a2", "d2"));
		assertEquals(1, sheet.getSingleData("a2", "d3"));
		assertEquals(Map.of("a1",6, "a2",1), sheet.sumsPerActivity()); // ore per attività
		assertEquals(Map.of("d1",1, "d2",2,"d3",4), sheet.sumsPerDay()); // ore per giorno
	}

	@org.junit.Test
	public void testOfRawData() {
		// un timesheet con 2 attività su 3 giorni, con nomi standard
		TimeSheet sheet = this.factory.ofRawData(2, 3, List.of(
			new Pair<>(0,0), // un'ora su act1 e day1
			new Pair<>(0,1), // un'ora su act1 e day2
			new Pair<>(0,1), // un'ora su act1 e day2 (diventano due in tutto)
			new Pair<>(0,2), // un'ora su act1 e day3
			new Pair<>(0,2), // un'ora su act1 e day3 (diventano due in tutto)
			new Pair<>(0,2), // un'ora su act1 e day3 (diventano tre in tutto)
			new Pair<>(1,2)));// un'ora su act2 e day3
		assertEquals(List.of("act1", "act2"), sheet.activities());
		assertEquals(List.of("day1", "day2", "day3"), sheet.days());
		assertEquals(2, sheet.getSingleData("act1", "day2"));
		assertEquals(3, sheet.getSingleData("act1", "day3"));
		assertEquals(0, sheet.getSingleData("act2", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
		assertEquals(Map.of("act1",6, "act2",1), sheet.sumsPerActivity());
		assertEquals(Map.of("day1",1, "day2",2,"day3",4), sheet.sumsPerDay());
	}

	@org.junit.Test
	public void testOfMap() {
		// un timesheet con 2 attività su 3 giorni, con nomi forniti
		TimeSheet sheet = this.factory.ofPartialMap(
			List.of("act1","act2"), 
			List.of("day1", "day2", "day3"),
			Map.of( // mappa (attività, giorno) -> n_ore
				new Pair<>("act1","day1"),1,  
				new Pair<>("act1","day2"),2,
				new Pair<>("act1","day3"),3,
				new Pair<>("act2","day3"),1));
		assertEquals(List.of("act1", "act2"), sheet.activities());
		assertEquals(List.of("day1", "day2", "day3"), sheet.days());
		assertEquals(2, sheet.getSingleData("act1", "day2"));
		assertEquals(3, sheet.getSingleData("act1", "day3"));
		assertEquals(0, sheet.getSingleData("act2", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
		assertEquals(Map.of("act1",6, "act2",1), sheet.sumsPerActivity());
		assertEquals(Map.of("day1",1, "day2",2,"day3",4), sheet.sumsPerDay());
	}
}
