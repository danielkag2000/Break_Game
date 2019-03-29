package objects;

import biuoop.DrawSurface;

import interfaces.Sprite;

/**
 * A collection of Sprites.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-15
 */
public class SpriteCollection {

    // the list of Sprite objects
    private java.util.List<Sprite> spriteList;

    /**
     * Create new Sprite Collection.
     */
    public SpriteCollection() {
        this.spriteList = new java.util.ArrayList<Sprite>();
    }

    /**
     * add a new Sprite to the list.
     *
     * @param s the Spirit to add
     */
    public void addSprite(Sprite s) {
        spriteList.add(s);
    }

    /**
     * remove a new Sprite to the list.
     *
     * @param s the Spirit to remove
     */
    public void removeSprite(Sprite s) {
        spriteList.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt the amount of seconds passed since the last call
     */
    public void notifyAllTimePassed(double dt) {
        java.util.List<Sprite> copy = new java.util.ArrayList<Sprite>(this.spriteList);
        for (Sprite spirit : copy) {
            spirit.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the surface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.spriteList.size(); i++) {
            this.spriteList.get(i).drawOn(d);
        }
    }
}
