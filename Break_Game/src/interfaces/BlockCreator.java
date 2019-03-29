package interfaces;

import objects.Block;

/**
 * interface of a factory-object that is used for creating blocks.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public interface BlockCreator {

    /**
     * Create a block at the specified location.
     *
     * @param xpos the x position
     * @param ypos the y position
     * @return a new Block
     */
    Block create(int xpos, int ypos);
}
