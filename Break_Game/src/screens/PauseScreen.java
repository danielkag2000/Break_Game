package screens;

import biuoop.DrawSurface;

import interfaces.Animation;

/**
 * Pause the Game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public class PauseScreen implements Animation {

    private boolean stop;

    /**
     * Create the option to Pause the Game.
     */
    public PauseScreen() {
        this.stop = false;
     }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
     }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
