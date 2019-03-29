package listeners;

import game.GameLevel;

import interfaces.HitListener;

import objects.Ball;
import objects.Block;
import objects.Counter;

/**
 * A BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-17
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * the constructor of the Listener.
     *
     * @param game the current game
     * @param removedBlocks the counter of the blocks
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points
     * should be removed from the game.
     *
     * @param beingHit the hitted block
     * @param hitter the block that hitted the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}
