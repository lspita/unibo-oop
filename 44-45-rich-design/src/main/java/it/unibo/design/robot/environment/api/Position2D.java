package it.unibo.design.robot.environment.api;

/**
 * A position in a bidimensional space
 */
public interface Position2D {

    /**
     * @return X position
     */
    int getX();

    /**
     * @return Y position
     */
    int getY();

    /**
     * @param p
     *            delta movement to sum
     * @return the new position
     */
    Position2D plus(Position2D p);

    /**
     * @param x
     *            X delta
     * @param y
     *            Y delta
     * @return the new position
     */
    Position2D plus(int x, int y);

}
