package objects;

import java.awt.Color;

import biuoop.DrawSurface;

import interfaces.Collidable;
import interfaces.Sprite;

/**
 * the screen of the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-13
 */
public class Screen implements Collidable, Sprite {

    private Point startP;  // the start point of the screen
    private Point endP;  // the end point of the screen
    private int width;  // the width of the screen
    private int height;  // the height of the screen

    // the color of the screen -- default dark blue
    private Color screenColor = Color.BLUE.darker();

    /**
     * the constructor of screen.
     *
     * @param width the width of the screen
     * @param height the height of the screen
     */
    public Screen(int width, int height) {
        this (new Point(0, 0), width, height);
    }

    /**
     * the constructor of screen.
     *
     * @param startP the start point of the screen
     * @param endP the end point of the screen
     */
    public Screen(Point startP, Point endP) {
        this.width = (int) Math.abs(startP.getX() - endP.getX());
        this.height = (int) Math.abs(startP.getY() - endP.getY());
        this.startP = startP;
        this.endP = endP;
    }

    /**
     * the constructor of screen.
     *
     * @param startP the start point of the screen
     * @param width the width of the screen
     * @param height the height of the screen
     */
    public Screen(Point startP, int width, int height) {
        this.width = width;
        this.height = height;
        this.startP = startP;
        this.endP = new Point(startP.getX() + width, startP.getX() + height);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(new Point(0, 0), this.width, this.height);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // the new velocity
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());

        // if the point on the border of the y axis
        if ((collisionPoint.getX()) <= this.startP.getX()
                || (collisionPoint.getX()) >= this.endP.getX()) {

                newVelocity.setDx(-1 * newVelocity.getDx());
        }

        // if the point on the border of the x axis
        if ((collisionPoint.getY()) <= this.startP.getY()
                || (collisionPoint.getY()) >= this.endP.getY()) {

                newVelocity.setDy(-1 * newVelocity.getDy());
        }

        return newVelocity;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.screenColor);
        d.fillRectangle((int) this.startP.getX(), (int) this.startP.getY(), this.width, this.height);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }

    /**
     * a getter of the width of the screen.
     *
     * @return the width of the screen
     */
    public int width() {
        return this.width;
    }

    /**
     * a getter of the height of the screen.
     *
     * @return the height of the screen
     */
    public int height() {
        return this.height;
    }

    /**
     * a getter of the start point of the screen.
     *
     * @return the start point of the screen
     */
    public Point startPoint() {
        return this.startP;
    }

    /**
     * a getter of the end point of the screen.
     *
     * @return the end point of the screen
     */
    public Point endPoint() {
        return this.endP;
    }

    /**
     * set the color of the screen.
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.screenColor = color;
    }
}
