package interfaces;

import biuoop.DrawSurface;

import objects.Block;

/**
 * a FillableForBlock is in charge of fill the block.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public interface FillableForBlock {

    /**
     * fill the block.
     *
     * @param d the Draw Surface
     * @param block the block
     */
    void fill(DrawSurface d, Block block);
}
