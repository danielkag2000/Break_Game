package objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import interfaces.BlockCreator;
import interfaces.FillableForBlock;

/**
 * Create blocks.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public class BlockTemplateCreator implements BlockCreator {

    private Color stroke;
    private int width;
    private int height;
    private int hitPoints;
    private List<FillableForBlock> fillK;

    /**
     * make a new BlockTemplateCreator.
     *
     * @param width the width of the block
     * @param height the height of the block
     * @param hitPoints the hit Points of the block
     * @param fillK the fill for each hit point of the block
     * @param stroke the stroke color of the block
     */
    public BlockTemplateCreator(int width, int height, int hitPoints, List<FillableForBlock> fillK, Color stroke) {
        this.width = width;
        this.height = height;
        this.fillK = new ArrayList<FillableForBlock>();
        this.hitPoints = hitPoints;
        this.fillK = fillK;
        this.stroke = stroke;
    }

    @Override
    public Block create(int xpos, int ypos) {

        return new Block(new Point(xpos, ypos), this.width, this.height, this.hitPoints, this.fillK, this.stroke);
    }

}
