package indicators;

import java.awt.Color;

import biuoop.DrawSurface;

import game.GameLevel;

import interfaces.Sprite;

import objects.Counter;

/**
 * A LivesIndicator is in charge of show the lives of the player.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public class LivesIndicator implements Sprite {

    private Counter lives;

    /**
     * Create new LivesIndicator.
     *
     * @param lives the lives of the player
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE.darker());
        d.fillRectangle(0, 0, d.getWidth() / 3, GameLevel.getDistanceFromEdge());
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 6 - 30, (GameLevel.getDistanceFromEdge() * 3) / 4,
                   "Lives: " + this.lives.getValue(), 15);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }
}
