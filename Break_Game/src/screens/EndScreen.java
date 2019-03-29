package screens;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import interfaces.Animation;

/**
 * the end game screen.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-20
 */
public class EndScreen implements Animation {

    private boolean win;
    private int score;
    //private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * create new end game animation.
     *
     * @param score the score of the player
     * @param win if the player win in the game or not
     * @param keyboard the player's keybord
     */
    public EndScreen(int score, boolean win, KeyboardSensor keyboard) {
        this.score = score;
        this.win = win;
        //this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        if (this.win) {
            d.drawText(d.getWidth() / 2 - 100, 50, "You Win! Your score is " + this.score, 20);
        } else {
            d.drawText(d.getWidth() / 2 - 100, 50, "Game Over. Your score is " + this.score, 20);
        }
        d.drawText(d.getWidth() / 2 - 100, d.getHeight() - 50, "press space to continue", 20);
     }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
