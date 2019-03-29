package screens;

import java.awt.Color;

import biuoop.DrawSurface;

import interfaces.Animation;

import objects.HighScoresTable;

/**
 * Graphical representation of the scores.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;
    private String endKey;
    private boolean stop;

    /**
     * the constructor.
     *
     * @param scores the score table
     * @param endKey the key to continue
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey) {
        this.scores = scores;
        this.endKey = endKey;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 800);

        d.setColor(Color.YELLOW.darker());
        d.drawText(d.getWidth() / 2 - 175, 50, "See The High Scores", 40);

        d.setColor(Color.BLACK);
        d.drawLine(d.getWidth() / 2, d.getHeight() / 2 - 100, d.getWidth() / 2, d.getHeight() / 2 + 150);
        d.drawLine(d.getWidth() / 2 - 200, d.getHeight() / 2 - 20, d.getWidth() / 2 + 200, d.getHeight() / 2 - 20);

        d.setColor(Color.CYAN.darker());
        d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2 - 40, "Name", 40);
        d.drawText(d.getWidth() / 2 + 50, d.getHeight() / 2 - 40, "Score", 40);

        int y = d.getHeight() / 2 + 10;

        for (int i = 0; i < this.scores.getHighScores().size(); i++) {

            d.setColor(Color.ORANGE.darker());
            d.drawText(d.getWidth() / 2 - 150, y, this.scores.getHighScores().get(i).getName(), 25);
            d.drawText(d.getWidth() / 2 + 50, y, "" + this.scores.getHighScores().get(i).getScore(), 25);

            y += 25;
        }

        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 200, d.getHeight() - 50, "press " + endKey + " to back go to menu", 30);

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
