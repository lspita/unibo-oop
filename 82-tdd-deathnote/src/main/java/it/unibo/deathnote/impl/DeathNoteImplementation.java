package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

/**
 * Base {@link DeathNote} interface implementation
 */
public final class DeathNoteImplementation implements DeathNote {
    private enum TimedOperation {
        DEATH_CAUSE(40),
        DEATH_DETAILS(6040);
        
        private long timeout;
        private long startTime;
        private static final long UNASSIGNED_START_TIME = -1;

        private TimedOperation(final long timeout) {
            this.timeout = timeout;
            this.startTime = UNASSIGNED_START_TIME;
        }

        /**
         * Check if timed as not timed out
         * @return `true` if operation has not timed out, `false` otherwise
         */
        public boolean isValid() {
            return 
                this.startTime != UNASSIGNED_START_TIME && 
                System.currentTimeMillis() - this.startTime <= this.timeout;
        }

        /**
         * Make opeartion valid for its specific amount of time
         */
        public void enable() {
            this.startTime = System.currentTimeMillis();
        }
    }

    private final class DeathInfo {
        public static String DEFAULT_CAUSE = "heart attack";
        public static String DEFAULT_DETAILS = "";

        private String cause;
        private String details;
        
        public String getCause() {
            return cause != null ? cause : DEFAULT_CAUSE;
        }
        
        public String getDetails() {
            return details != null ? details : DEFAULT_DETAILS;
        }
    }

    private Map<String, DeathInfo> deathInfos;
    private String currentName;
    
    /**
     * Init empty death note
     */
    public DeathNoteImplementation() {
        this.deathInfos = new HashMap<>();
    }

    /**
     * Cast rule number to index for the {@link #RULES} list
     * @param ruleNumber
     * @return
     */
    private static int ruleNumberIndex(final int ruleNumber) {
        return ruleNumber - 1;
    }

    /**
     * Get death infos of current name
     * @return death infos associated with current name
     */
    private DeathInfo getCurrentDeathInfo() {
        return this.getDeathInfo(this.currentName);
    }

    /**
     * Get death infos of given name
     * @param name name of the dead
     * @return death infos
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote.
     */
    private DeathInfo getDeathInfo(final String name) throws IllegalArgumentException {
        if (!this.isNameWritten(name)) {
            throw new IllegalArgumentException(name + " is not written on the death note");
        }
        return this.deathInfos.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(int ruleNumber) throws IllegalArgumentException {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Rule number out of range");
        }
        final int index = ruleNumberIndex(ruleNumber);
        return DeathNote.RULES.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(String name) throws NullPointerException {
        name = Objects.requireNonNull(name);
        this.currentName = name;
        this.deathInfos.put(name, new DeathInfo());
        TimedOperation.DEATH_CAUSE.enable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == null || this.currentName == null) {
            throw new IllegalStateException("Invalid state for death cause");
        }
        if (!TimedOperation.DEATH_CAUSE.isValid()) {
            return false;
        }
        this.getCurrentDeathInfo().cause = cause;
        TimedOperation.DEATH_DETAILS.enable();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(String details) {
        if (details == null || this.currentName == null) {
            throw new IllegalStateException("Invalid state for death details");
        }
        if (!TimedOperation.DEATH_DETAILS.isValid()) {
            return false;
        }
        this.getCurrentDeathInfo().details = details;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(String name) {
        return this.getDeathInfo(name).getCause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(String name) {
        return this.getDeathInfo(name).getDetails();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(String name) {
        return this.deathInfos.containsKey(name);
    }

}