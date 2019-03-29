package objects;

import java.util.Map;

import interfaces.BlockCreator;

/**
 * Block Factory.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-07
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * a block factory.
     *
     * @param spacerWidths the space map.
     * @param blockCreators the block map.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param s space symbol
     * @return true if 's' is a valid space symbol.
     *         otherwise, false
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey((String) s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s block symbol
     * @return true if 's' is a valid block symbol.
     *         otherwise, false
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey((String) s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     *
     * @param s block symbol
     * @param xpos the x position
     * @param ypos the y position
     * @return a block according to the definitions associated
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s space symbol
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
