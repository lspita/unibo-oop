package a01d.e1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {

	/*
	 * Implementare l'interfaccia TimetableFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di "orario settimanale
	 * di lezioni universitarie", catturato dall'interfaccia Timesheet: essenzialmente 
	 * traccia in quali giorni della settimana si tengono lezioni di quale corso, e in quale
	 * aula.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio:
	 * 
	 * - implementazione di tutti i metodi di Timetable (ossia, nella parte
	 * obbligatoria è sufficiente implementarli tutti tranne uno dei tre metodi getXYZ, a piacimento)
	 * - la buona progettazione della soluzione, utilizzando soluzioni progettuali che portino a
	 * codice succinto che evita ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo di Timetable)
	 * - qualità della soluzione: 4 punti (per buon design)
	 * 
	 */


	private TimetableFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new TimetableFactoryImpl();
	}

	// orario delle lezioni realistico (I semestre del II anno, appena concluso, senza i lab)
	private Timetable real(){
		return this.factory.empty()
			.addBooking("2.12", "OOP", Timetable.Day.WED, 9, 3)
			.addBooking("3.4", "MDP", Timetable.Day.WED, 13, 3)
			.addBooking("2.12", "SISOP", Timetable.Day.THU, 9, 3)
			.addBooking("2.12", "OOP", Timetable.Day.THU, 13, 3)
			.addBooking("2.12", "MDP", Timetable.Day.FRI, 9, 2)
			.addBooking("2.12", "SISOP", Timetable.Day.FRI, 11, 3);
	}

	@org.junit.Test
	public void testEmpty() {
		// orario delle lezioni "vuoto", diciamo durante il periodo gli esami...
		Timetable t = this.factory.empty();
		assertEquals(Set.of(), t.courses());
		assertEquals(Set.of(), t.rooms());
		assertEquals(List.of(), t.hours());
		assertEquals(Map.of(), t.getCourseTable("OOP"));
		assertEquals(Optional.empty(), t.getDayAndHour(Timetable.Day.MON, 9));
		assertEquals(Map.of(), t.getDayAtRoom("2.12", Timetable.Day.WED));
	}

	@org.junit.Test
	public void testBookings() {
		// orario delle lezioni realistico, test dei metodi courses, rooms e hours
		Timetable t = real();
		assertEquals(Set.of("OOP", "SISOP", "MDP"), t.courses()); // i corsi
		assertEquals(Set.of("2.12", "3.4"), t.rooms()); // le aule usate
		assertEquals(List.of(9,10,11,12,13,14,15), t.hours()); // le ore di aula utilizzate
	}

	@org.junit.Test
	public void testFindPlace() {
		// orario delle lezioni realistico
		Timetable t = real();
		// c'è posto in aula 3.4 il mercoledì per 3 ore di fila? si, alle 9 (fornisco la prima possibilità)
		assertEquals(Optional.of(9), t.findPlaceForBooking("3.4", Timetable.Day.WED, 3));
	    // c'è posto in aula 2.12 il giovedì per 2 ore di fila? no, tutto pieno
		assertEquals(Optional.empty(), t.findPlaceForBooking("2.12", Timetable.Day.THU, 2));
	}


	@org.junit.Test
	public void testCourseTable() {
		// test del metodo getCourseTable
		Timetable t = real();
		// test orario OOP
		assertEquals(Map.of(   
			Timetable.Day.WED, Map.of(9, "2.12", 10, "2.12", 11, "2.12"),
			Timetable.Day.THU, Map.of(13, "2.12", 14, "2.12", 15, "2.12")
		), t.getCourseTable("OOP"));
		// test orario MDP
		assertEquals(Map.of(  
			Timetable.Day.WED, Map.of(13, "3.4", 14, "3.4", 15, "3.4"),
			Timetable.Day.FRI, Map.of(9, "2.12", 10, "2.12")
		), t.getCourseTable("MDP"));
	}

	@org.junit.Test
	public void testDayAndHour() {
		// test del metodo getDayAndHour
		Timetable t = real();
		// test di cosa c'è il mercoledì alle 9
		assertEquals(Optional.of(new Pair<>("OOP", "2.12")), t.getDayAndHour(Timetable.Day.WED, 9));
		// test di cosa c'è il giovedì alle 9
		assertEquals(Optional.of(new Pair<>("SISOP", "2.12")), t.getDayAndHour(Timetable.Day.THU, 9));
		// test di cosa c'è il mercoledì alle 12: niente
		assertEquals(Optional.empty(), t.getDayAndHour(Timetable.Day.WED, 12));
	}

	@org.junit.Test
	public void testDayAtRoom() {
		// test del metodo getDayAtRoom
		Timetable t = real();
		// test di cosa c'è in 2.12 il mercoledì
		assertEquals(Map.of(9, "OOP", 10, "OOP", 11, "OOP"), t.getDayAtRoom("2.12", Timetable.Day.WED));
		// test di cosa c'è in 2.12 il giovedì
		assertEquals(
			Map.of(9, "SISOP", 10, "SISOP", 11, "SISOP", 13, "OOP", 14, "OOP", 15, "OOP"), 
			t.getDayAtRoom("2.12", Timetable.Day.THU));
	}


}
