package it.unibo.design.robot.environment.impl;

import it.unibo.design.robot.environment.api.Position2D;

/**
 * Models the environment in which a {@link it.unibo.design.robot.api.Robot}
 * is situated
 *
 */
public class RobotEnvironment {

    /**
     * Environment upper bound for the X coordinate
     */
    public static final int X_UPPER_LIMIT = 50;
    /**
     * Environment lower bound for the X coordinate
     */
    public static final int X_LOWER_LIMIT = 0;
    /**
     * Environment upper bound for the Y coordinate
     */
    public static final int Y_UPPER_LIMIT = 80;
    /**
     * Environment lower bound for the X coordinate
     */
    public static final int Y_LOWER_LIMIT = 0;

    private Position2D position;

    /**
     * 
     * @param position the robot position
     */
    public RobotEnvironment(final Position2D position) {
        this.position = position;
    }

    protected boolean isWithinWorld(final Position2D p) {
        final var x = p.getX();
        final var y = p.getY();
        return x >= X_LOWER_LIMIT && x <= X_UPPER_LIMIT && y >= Y_LOWER_LIMIT && y <= Y_UPPER_LIMIT;
    }

    /**
     * Move the robot to a new position
     * 
     * @param dx
     * @param dy
     * @return A boolean indicating if the robot moved or not (a robot can move
     *         only inside the environment's boundaries)
     */
    public boolean move(final int dx, final int dy) {
        final var newPos = position.plus(dx, dy);
        if (isWithinWorld(newPos)) {
            position = newPos;
            return true;
        }
        return false;
    }

    /**
     * @return Current X position
     */
    public Position2D getPosition() {
        return position;
    }

}
