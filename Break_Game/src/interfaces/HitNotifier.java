package interfaces;

/**
  * Notifier of hitting a block from the action to be performed
 * when a block is hit.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-13
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl the HitListener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the HitListener
     */
    void removeHitListener(HitListener hl);
}
