package interfaces;

import biuoop.DrawSurface;

/**
 * A Animation is showing the Animation.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public interface Animation {

    /**
     * draw the frame.
     *
     * @param d the DrawSurface to draw with
     * @param dt the amount of seconds passed since the last call
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * if the game should be stopped.
     *
     * @return true if the game should stop,
     *         otherwise, return false.
     */
    boolean shouldStop();
}
