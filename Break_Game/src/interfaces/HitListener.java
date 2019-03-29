package interfaces;

import objects.Ball;
import objects.Block;

/**
 * Objects that want to be notified of hit events.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-13
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the Block that is hitted
     * @param hitter the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
