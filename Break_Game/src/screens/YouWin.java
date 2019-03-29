package screens;

import java.awt.Color;

import biuoop.DrawSurface;
import interfaces.Animation;

/**
 * the Won screen.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-20
 */
public class YouWin implements Animation {

    private int score;
    private boolean stop;

    /**
     * create new end game animation.
     *
     * @param score the score of the player
     */
    public YouWin(int score) {
        this.score = score;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 800);

        d.setColor(Color.YELLOW.darker());
        d.drawText(d.getWidth() / 2 - 200, 100, "You Win! Your score is " + this.score, 40);

        d.setColor(Color.ORANGE.darker());
        d.drawText(d.getWidth() / 2 - 100, d.getHeight() / 2 - 40, "Good Job :D", 35);

        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 150, d.getHeight() - 50, "press space to continue", 30);
     }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
