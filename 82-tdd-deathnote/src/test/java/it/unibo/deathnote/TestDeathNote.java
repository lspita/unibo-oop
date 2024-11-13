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
    private String mainName = "Nicholas Magi";
    private String mainDeathCause = "karting accident";
    private String mainDeathDetails = "Diarrhea";
    private String otherName = "Matteo Tonelli";
    private String otherDeathCause = "stomach disease";

    private String DEFAULT_CAUSE = "heart attack";
    private String DEFAULT_DETAILS = "";
    private static final long DEATH_CAUSE_TIMEOUT_MS = 1000;

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
        assertFalse(dn.isNameWritten(mainName));
        dn.writeName(mainName);
        assertTrue(dn.isNameWritten(mainName));
        assertFalse(dn.isNameWritten(otherName));
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
            () -> dn.writeDeathCause(mainDeathCause)
        );
        dn.writeName(mainName);
        assertEquals(dn.getDeathCause(mainName), DEFAULT_CAUSE);
        dn.writeName(mainName);
        assertTrue(dn.writeDeathCause(mainDeathCause));
        assertEquals(dn.getDeathCause(mainName), mainDeathCause);
        Thread.sleep(DEATH_CAUSE_TIMEOUT_MS);
        assertFalse(dn.writeDeathCause(otherDeathCause));
        assertEquals(dn.getDeathCause(mainName), mainDeathCause);
    }

    /**
     * Only if the cause of death is written within the next 6 seconds 
     * and 40 milliseconds of writing the death's details, it will happen
     */
    @Test
    public void testDeathDetails() {
        // TODO Incoherent
    }
}