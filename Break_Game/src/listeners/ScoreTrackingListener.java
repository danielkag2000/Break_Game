package listeners;

import interfaces.HitListener;

import objects.Ball;
import objects.Block;
import objects.Counter;

/**
 * A ScoreTrackingListener is in charge of the score of the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * the constructor of the Listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
        if (beingHit.getHitPoints() <= 0) {
            this.currentScore.increase(10);
        }
    }

    /**
     * If all the block are removed add 100 points.
     */
    public void gameCleared() {
        this.currentScore.increase(100);
    }

    /**
     * get the current score.
     *
     * @return the current score.
     */
    public int getCurrentScore() {
        return this.currentScore.getValue();
    }
}
