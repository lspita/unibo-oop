package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {
    private DeathNote dn;
    private static final String NAME_1 = "Nicholas Magi";
    private static final String NAME_2 = "Matteo Tonelli";
    private static final String NAME_3 = "Ludovico Spitaleri";
    
    private static final String DEFAULT_CAUSE = "heart attack";
    private static final String CAUSE_1 = "karting accident";
    private static final String CAUSE_2 = "diarrhea";
    
    private static final String DEFAULT_DETAILS = "";
    private static final String DETAILS_1 = "ran for too long";
    private static final String DETAILS_2 = "a lot";
    
    private static final long CAUSE_TIMEOUT_MS = 1000;
    private static final long DETAILS_TIMEOUT_MS = 6400;

    private Set<Integer> invalidRuleNumbers = Set.of(
        0, 
        -1, 
        DeathNote.RULES.size() + 1
    );

    @BeforeEach
    public void init() {
        this.dn = new DeathNoteImplementation();
    }

    private void assertNotNullEmptyBlank(final String s) {
        assertNotNull(s);
        assertNotEquals(s.trim(), "");
    }

    /**
     * Rule number 0 and negative rules do not exist in the DeathNote rules
     */
    @Test
    public void testInvalidRuleNumbers() {
        for (final int i : invalidRuleNumbers) {
            assertThrows(
                IllegalArgumentException.class, 
                () -> dn.getRule(i)
            );
        }
    }

    /**
     * No rule is empty or null in the DeathNote rules
     */
    @Test
    public void testRulesNonEmpty() {
        for (int i = 1; i <= DeathNote.RULES.size(); i++) {
            assertNotNullEmptyBlank(dn.getRule(i));
        }
    }

    /**
     * The human whose name is written in the DeathNote will eventually die
     */
    @Test
    public void testHumanKill() {
        assertFalse(dn.isNameWritten(NAME_1));
        dn.writeName(NAME_1);
        assertTrue(dn.isNameWritten(NAME_1));
        assertFalse(dn.isNameWritten(NAME_2));
        assertFalse(dn.isNameWritten(""));
    }

    /**
     * Only if the cause of death is written within the next 40 milliseconds of 
     * writing the person's name, it will happen.
     */
    @Test
    public void testCauseOfDeath() throws InterruptedException {
        assertThrows(
            IllegalStateException.class, 
            () -> dn.writeDeathCause(null)
        );
        assertThrows(
            IllegalStateException.class, 
            () -> dn.writeDeathCause(CAUSE_1)
        );
        dn.writeName(NAME_1);
        assertEquals(dn.getDeathCause(NAME_1), DEFAULT_CAUSE);
        dn.writeName(NAME_2);
        assertTrue(dn.writeDeathCause(CAUSE_1));
        assertEquals(dn.getDeathCause(NAME_2), CAUSE_1);
        dn.writeName(NAME_3);
        Thread.sleep(CAUSE_TIMEOUT_MS);
        assertFalse(dn.writeDeathCause(CAUSE_2));
        assertEquals(dn.getDeathCause(NAME_3), DEFAULT_CAUSE);
    }

    /**
     * Only if the cause of death is written within the next 6 seconds 
     * and 40 milliseconds of writing the death's details, it will happen
     */
    @Test
    public void testDeathDetails() throws InterruptedException {
        assertThrows(
            IllegalStateException.class,
            () -> dn.writeDetails(null)
        );
        assertThrows(
            IllegalStateException.class,
            () -> dn.writeDetails(DETAILS_1)
        );
        dn.writeName(NAME_1);
        assertEquals(dn.getDeathDetails(NAME_1), DEFAULT_DETAILS);
        dn.writeName(NAME_2);
        assertFalse(dn.writeDetails(DETAILS_1));
        assertEquals(dn.getDeathDetails(NAME_2), DEFAULT_DETAILS);
        assertTrue(dn.writeDeathCause(CAUSE_1));
        assertTrue(dn.writeDetails(DETAILS_1));
        assertEquals(dn.getDeathDetails(NAME_2), DETAILS_1);
        dn.writeName(NAME_3);
        assertTrue(dn.writeDeathCause(CAUSE_2));
        Thread.sleep(DETAILS_TIMEOUT_MS);
        assertFalse(dn.writeDetails(DETAILS_2));
        assertEquals(dn.getDeathDetails(NAME_3), DEFAULT_DETAILS);
    }
}