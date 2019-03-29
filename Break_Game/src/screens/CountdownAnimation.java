package screens;

import java.awt.Color;

import biuoop.DrawSurface;

import interfaces.Animation;

import objects.SpriteCollection;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public class CountdownAnimation implements Animation {

    private SpriteCollection gameScreen;
    private int countFrom;
    private double numOfSeconds;
    private double startTime;
    private double currentTime;;

    /**
     * create new count down animation.
     *
     * @param numOfSeconds the number of second
     *                     to show the count down
     * @param countFrom the number to count from down to 0
     * @param gameScreen the spirits that should be in the background
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.gameScreen = gameScreen;
        this.countFrom = countFrom;
        this.numOfSeconds = numOfSeconds;
        this.startTime = System.currentTimeMillis();
        this.currentTime = this.startTime;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);

        this.currentTime = System.currentTimeMillis();
        this.drawOn(d);

    }

    /**
     * Draw on the screen the seconds.
     *
     * @param d a DrawSurface
     */
    private void drawOn(DrawSurface d) {

        int currentSec = this.countFrom - (int) (((this.currentTime - this.startTime) / 1000) * this.numOfSeconds);
        d.setColor(Color.WHITE);
        if (currentSec > 0) {
            d.drawText(d.getWidth() / 2 - 10, d.getHeight() / 2, "" + currentSec, 30);
        } else if (currentSec == 0) {
            d.drawText(d.getWidth() / 2 - 20, d.getHeight() / 2, "GO", 30);
        }
    }

    @Override
    public boolean shouldStop() {

        double currentSec = (this.currentTime - this.startTime) / 1000;
        if (currentSec > this.numOfSeconds) {
            return true;
        }

        return false;
    }
}
