package objects;


import java.awt.Color;

import biuoop.DrawSurface;
import interfaces.FillableForBlock;

/**
 * fill the block with color.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-09
 */
public class FillBlockWithColor implements FillableForBlock {

    private Color color;

    /**
     * the constructor.
     *
     * @param color the color of the block
     */
    public FillBlockWithColor(Color color) {
        this.color = color;
    }

    @Override
    public void fill(DrawSurface d, Block block) {
        d.setColor(this.color);
        d.fillRectangle((int) block.getCollisionRectangle().getUpperLeft().getX(),
                        (int) block.getCollisionRectangle().getUpperLeft().getY(),
                        (int) block.getCollisionRectangle().getWidth(),
                        (int) block.getCollisionRectangle().getHeight());
    }

    @Override
    public String toString() {
        return this.color.toString();
    }
}
