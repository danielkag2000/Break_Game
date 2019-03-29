package objects;

import java.io.Serializable;

/**
 * Player's score information of the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public class ScoreInfo implements Serializable {

    /**
     * the serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    /**
     * the constructor for a new player's score.
     *
     * @param name the name of the player
     * @param score the score of the player
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * get the player's name.
     *
     * @return the player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the player's score.
     *
     * @return the player's score.
     */
    public int getScore()  {
        return this.score;
    }
}
