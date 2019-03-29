package objects;

import game.GameLevel;

import interfaces.Collidable;
import interfaces.FillableForBlock;
import interfaces.HitNotifier;
import interfaces.Sprite;
import interfaces.HitListener;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * a obstacle block in the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-13
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle recBlock;  // the rectangle of the block
    private int hits;
    private java.awt.Color stroke;
    private List<HitListener> hitListeners;
    private List<FillableForBlock> fillK;

    /**
     * the Block constructor.
     *
     * @param rec the rectangle of the block
     * @param hits the number of hits of block
     * @param c the color of the block
     */
    public Block(Rectangle rec, int hits, java.awt.Color c) {
        this.recBlock = rec;
        this.fillK = new ArrayList<FillableForBlock>();
        if (c != null) {
            this.fillK.add(new FillBlockWithColor(c));
        }
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = null;
    }

    /**
     * the Block constructor.
     *
     * @param rec the rectangle of the block
     * @param hits the number of hits of block
     * @param c the color of the block
     * @param stroke the stroke color
     */
    public Block(Rectangle rec, int hits, java.awt.Color c, java.awt.Color stroke) {
        this.recBlock = rec;
        this.fillK = new ArrayList<FillableForBlock>();
        if (c != null) {
            this.fillK.add(new FillBlockWithColor(c));
        }
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = stroke;
    }

    /**
     * the Block constructor.
     *
     * @param upperLeft the upper left point of the rectangle Block
     * @param width the width of the rectangle Block
     * @param height the height of the rectangle Block
     * @param c the color of the block
     * @param hits the number of hits of block
     */
    public Block(Point upperLeft, double width, double height, int hits, java.awt.Color c) {
        this (new Rectangle(upperLeft, width, height), hits, c);
    }

    /**
     * the Block constructor.
     *
     * @param upperLeft the upper left point of the rectangle Block
     * @param width the width of the rectangle Block
     * @param height the height of the rectangle Block
     * @param img the image of the block
     * @param hits the number of hits of block
     */
    public Block(Point upperLeft, double width, double height, int hits, Image img) {
        this (new Rectangle(upperLeft, width, height), hits, null);
        this.fillK.add(new FillBlockWithImg(img));
    }

    /**
     * the Block constructor.
     *
     * @param upperLeft the upper left point of the rectangle Block
     * @param width the width of the rectangle Block
     * @param height the height of the rectangle Block
     * @param c the color of the block
     * @param hits the number of hits of block
     * @param s the stroke color
     */
    public Block(Point upperLeft, double width, double height, int hits,
               java.awt.Color c, java.awt.Color s) {
        this (new Rectangle(upperLeft, width, height), hits, c, s);
    }

    /**
     * the Block constructor.
     *
     * @param upperLeft the upper left point of the rectangle Block
     * @param width the width of the rectangle Block
     * @param height the height of the rectangle Block
     * @param img the image of the block
     * @param hits the number of hits of block
     * @param s the stroke color
     */
    public Block(Point upperLeft, double width, double height, int hits,
               Image img, java.awt.Color s) {
        this (new Rectangle(upperLeft, width, height), hits, null, s);
        this.fillK.add(new FillBlockWithImg(img));
    }

    /**
     * the Block constructor.
     *
     * @param upperLeft the upper left point of the rectangle Block
     * @param width the width of the rectangle Block
     * @param height the height of the rectangle Block
     * @param fillK the fill for each hit point of the block
     * @param hits the number of hits of block
     */
    public Block(Point upperLeft, double width, double height, int hits, List<FillableForBlock> fillK) {

        this.recBlock = new Rectangle(upperLeft, width, height);
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = null;
        this.fillK = fillK;
    }

    /**
     * the Block constructor.
     *
     * @param upperLeft the upper left point of the rectangle Block
     * @param width the width of the rectangle Block
     * @param height the height of the rectangle Block
     * @param fillK the fill for each hit point of the block
     * @param hits the number of hits of block
     * @param stroke the stroke color
     */
    public Block(Point upperLeft, double width, double height, int hits,
                 List<FillableForBlock> fillK, java.awt.Color stroke) {

        this.recBlock = new Rectangle(upperLeft, width, height);
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = stroke;
        this.fillK = fillK;
    }

    /**
     * the Block constructor.
     *
     * @param block a block to copy.
     */
    public Block(Block block) {

        Point up = new Point(block.recBlock.getUpperLeft().getX(), block.recBlock.getUpperLeft().getY());
        this.recBlock = new Rectangle(up, block.getCollisionRectangle().getWidth(),
                        block.getCollisionRectangle().getHeight());
        this.hits = block.hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = block.stroke;
        this.fillK = block.fillK;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return recBlock;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        // the new velocity
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        Line[] recLines = this.recBlock.getLineArr();

        double thisX = collisionPoint.getX();
        double thisY = collisionPoint.getY();

        double stX = this.recBlock.getUpperLeft().getX();
        double stY = this.recBlock.getUpperLeft().getY();

        double enX = this.recBlock.getUpperLeft().getX() + this.recBlock.getWidth();
        double enY = this.recBlock.getUpperLeft().getY() + this.recBlock.getHeight();

        if (this.hits != 0) {
            this.hits--;
        }

        // if the collision on the edge
        if (collisionPoint.equals(recLines[0].start()) || collisionPoint.equals(recLines[0].end())
            || collisionPoint.equals(recLines[2].start()) || collisionPoint.equals(recLines[2].end())) {
            this.notifyHit(hitter);
            return new Velocity(-1 * newVelocity.getDx(), -1 * newVelocity.getDy());
        }

        if (stX < thisX && thisX < enX) {
            newVelocity.setDy(-1 * newVelocity.getDy());
        }

        if (stY < thisY && thisY < enY) {
            newVelocity.setDx(-1 * newVelocity.getDx());
        }
        this.notifyHit(hitter);

        return newVelocity;
    }

    @Override
    public void drawOn(DrawSurface d) {

        if (this.hits != 0) {
            this.fillK.get(this.hits - 1).fill(d, this);
        } else {
            this.fillK.get(0).fill(d, this);
        }

        if (this.stroke != null) {
            d.setColor(this.stroke);
            d.drawRectangle((int) this.recBlock.getUpperLeft().getX(), (int) this.recBlock.getUpperLeft().getY(),
                (int) this.recBlock.getWidth(), (int) this.recBlock.getHeight());
        }
        /*
        int textSize = 15;
        d.setColor(java.awt.Color.WHITE);
        if (this.hits != 0 && this.hits != 1) {
            d.drawText((int) (this.recBlock.getUpperLeft().getX() + this.recBlock.getWidth() / 2 -  1),
                        (int) (this.recBlock.getUpperLeft().getY() + this.recBlock.getHeight() / 2 + 5),
                        "" + this.hits, textSize);
        }*/
        /*else {
            d.drawText((int) (this.recBlock.getUpperLeft().getX() + this.recBlock.getWidth() / 2),
                        (int) (this.recBlock.getUpperLeft().getY() + this.recBlock.getHeight() / 2),
                        "X", textSize);
        }*/

    }

    /**
     * remove the block from the game.
     *
     * @param game the Game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }

    /**
     * add the block
     * as a Sprite and as a Collidable object to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.getSpriteCollection().addSprite(this);
        g.getGameEnvironment().addCollidable(this);
    }

    /**
     * A get function of the gui of the game.
     *
     * @return the gui of the game
     */
    public int getHit() {
        return this.hits;
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notify for all the listeners that there was a hit.
     *
     * @param hitter the ball that hit the block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
        }
     }

    /**
     * the function return the number of
     * hit points of the block.
     *
     * @return the number of hit points of the block.
     */
    public int getHitPoints() {
        return this.hits;
    }
}
