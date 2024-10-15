package it.unibo.design.robot.environment.impl;

import it.unibo.design.robot.environment.api.Position2D;

/**
 * Models a (x,y) position for a {@link it.unibo.design.robot.api.Robot}
 */
public final class RobotPosition implements Position2D {

    private final int x;
    private final int y;

    /**
     * @param x
     *            X position
     * @param y
     *            Y position
     */
    public RobotPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(final Object o) {
        return this == o || o instanceof RobotPosition position && x == position.getX() && y == position.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int hashCode() {
        /*
         * This could be implemented WAY better.
         */
        return x ^ y;
    }

    public RobotPosition plus(final int x, final int y) {
        return new RobotPosition(this.x + x, this.y + y);
    }

    public RobotPosition plus(final Position2D p) {
        return new RobotPosition(x + p.getX(), y + p.getY());
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
