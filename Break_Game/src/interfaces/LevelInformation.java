package interfaces;

import java.util.List;

import objects.Ball;
import objects.Block;
import objects.Velocity;

/**
 * The LevelInformation interface specifies
 * the information required to fully describe a level.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-19
 */
public interface LevelInformation {

    /**
     * get the number of balls in the level.
     *
     * @return the number of balls in the level.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     *
     * @return List of Velocity of each ball.
     */
    List<Velocity> initialBallVelocities();

    /**
     * get the speed of the paddle.
     *
     * @return the speed of the paddle.
     */
    int paddleSpeed();

    /**
     * get the width of the paddle.
     *
     * @return the width of the paddle.
     */
    int paddleWidth();

    /**
     * get the name of the level.
     *
     * @return the name of the level
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return a list of blocks of the level
     */
    List<Block> blocks();

    /**
     * The balls that make up this level, each ball contains
     * its size, color, location and velocity.
     *
     * @return a list of blocks of the level
     */
    List<Ball> balls();

    /**
     * Number of levels that should be removed
     * before the level is considered to be "cleared".
     *
     * @return the number of blocks to remove
     */
    int numberOfBlocksToRemove();
}
