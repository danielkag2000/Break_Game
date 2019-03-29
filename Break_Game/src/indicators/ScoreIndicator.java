package indicators;

import java.awt.Color;

import biuoop.DrawSurface;

import game.GameLevel;

import interfaces.Sprite;

import objects.Counter;

/**
 * A ScoreIndicator is in charge of show the score of the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public class ScoreIndicator implements Sprite {

    private Counter score;

    /**
     * Create new ScoreIndicator.
     *
     * @param score the initial score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // d.setColor(Color.BLACK);
        // d.drawRectangle(0, 0, 800, Game.getDistanceFromEdge());
        d.setColor(Color.WHITE.darker());
        d.fillRectangle(d.getWidth() / 3, 0, (d.getWidth() * 2) / 3, GameLevel.getDistanceFromEdge());
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 3 + d.getWidth() / 6 - 30, (GameLevel.getDistanceFromEdge() * 3) / 4,
                   "Score: " + this.score.getValue(), 15);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }

}
