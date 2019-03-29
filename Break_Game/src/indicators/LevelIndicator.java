package indicators;

import java.awt.Color;

import biuoop.DrawSurface;
import game.GameLevel;
import interfaces.Sprite;

/**
 * A LevelIndicator is in charge of show the level of the player.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-19
 */
public class LevelIndicator implements Sprite {

    private String levelName;

    /**
     * Create new LivesIndicator.
     *
     * @param levelName the level of the player
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE.darker());
        d.fillRectangle((d.getWidth() * 2) / 3, 0, d.getWidth(), GameLevel.getDistanceFromEdge());
        d.setColor(Color.BLACK);
        d.drawText((d.getWidth() * 2) / 3, (GameLevel.getDistanceFromEdge() * 3) / 4,
                    "Level Name: " + this.levelName, 15);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }
}
