package objects;

import java.awt.Image;

import biuoop.DrawSurface;
import interfaces.FillableForBlock;

/**
 * fill the block with image.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-09
 */
public class FillBlockWithImg implements FillableForBlock {

    private Image img;

    /**
     * the constructor.
     *
     * @param img the image of the block
     */
    public FillBlockWithImg(Image img) {
        this.img = img;
    }

    @Override
    public void fill(DrawSurface d, Block block) {
        d.drawImage((int) block.getCollisionRectangle().getUpperLeft().getX(),
                    (int) block.getCollisionRectangle().getUpperLeft().getY(), this.img);
    }

    @Override
    public String toString() {
        return this.img.toString();
    }
}
