package objects;

import game.GameLevel;

import interfaces.Collidable;
import interfaces.Sprite;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Paddle is the player in the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-15
 */
public class Paddle implements Sprite, Collidable {

    private biuoop.KeyboardSensor keyboard;  // the players keyboard
    private Rectangle paddle;
    private int paddleMove = 5;  // the move of the paddle -- default 5
    private double paddleMoveOfDt = 5;  // the move of the paddle -- default 5
    private int fromX;  // the left border
    private int toX;  // the right border

    /**
     * the paddle constructor.
     *
     * @param keyboard the player's keybord
     * @param upperLeft the upper left point of the paddle
     * @param width the height of the paddle
     * @param height the height of the paddle
     * @param fromX the left border of the paddle
     * @param toX the right border of the paddle
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Point upperLeft, int width, int height, int fromX, int toX) {
        this.keyboard = keyboard;
        this.paddle = new Rectangle(upperLeft, width, height);
        this.fromX = fromX;
        this.toX = toX;
    }

    /**
     * move the player to left direction.
     */
    public void moveLeft() {
        double epsilon = 0.02;
        if (this.paddle.getUpperLeft().getX() - this.paddleMoveOfDt < this.fromX + epsilon) {
            this.paddle = new Rectangle(new Point(this.fromX + epsilon, this.paddle.getUpperLeft().getY()),
                                        (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        } else {
            this.paddle = new Rectangle(new Point(this.paddle.getUpperLeft().getX() - this.paddleMoveOfDt,
                    this.paddle.getUpperLeft().getY()),
                    (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        }
    }

    /**
     * move the player to right direction.
     */
    public void moveRight() {
        double epsilon = 0.02;
        if (this.paddle.getUpperLeft().getX() + this.paddle.getWidth() + this.paddleMoveOfDt > this.toX - epsilon) {
            this.paddle = new Rectangle(new Point(this.toX - this.paddle.getWidth() - epsilon,
                                        this.paddle.getUpperLeft().getY()),
                                        (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        } else {
            this.paddle = new Rectangle(new Point(this.paddle.getUpperLeft().getX() + this.paddleMoveOfDt,
                    this.paddle.getUpperLeft().getY()),
                    (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // the new velocity
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        Line[] recLines = this.paddle.getLineArr();

        double thisX = collisionPoint.getX();
        double thisY = collisionPoint.getY();

        double stX = this.paddle.getUpperLeft().getX();
        double stY = this.paddle.getUpperLeft().getY();

        double enX = this.paddle.getUpperLeft().getX() + this.paddle.getWidth();
        //double enY = this.paddle.getUpperLeft().getY() + this.paddle.getHeight();

        // if the collision on the edge
        if (collisionPoint.equals(recLines[0].start()) || collisionPoint.equals(recLines[0].end())
            || collisionPoint.equals(recLines[2].start()) || collisionPoint.equals(recLines[2].end())) {

            if (collisionPoint.equals(recLines[0].start())) {
                return Velocity.fromAngleAndSpeed(-60, currentVelocity.speed());

            } else if (collisionPoint.equals(recLines[0].end())) {
                return Velocity.fromAngleAndSpeed(60, currentVelocity.speed());

            } else {
                return new Velocity(-1 * newVelocity.getDx(), -1 * newVelocity.getDy());
            }
        }

        if (((stX) < thisX && thisX < (enX)) && thisY == this.paddle.getUpperLeft().getY()) {
            if (thisY == stY) {
                int sector = (int) ((collisionPoint.getX() - this.paddle.getUpperLeft().getX())
                               / (this.paddle.getWidth() / 5));

                return Velocity.fromAngleAndSpeed(300 + 30 * sector, currentVelocity.speed());
            }
            newVelocity.setDy(-1 * newVelocity.getDy());
        }

        //if (stY < thisY && thisY < enY) {
            //newVelocity.setDx(-1 * newVelocity.getDx());
        //}

        return newVelocity;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.ORANGE);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                        (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }

    @Override
    public void timePassed(double dt) {

        this.paddleMoveOfDt = dt * this.paddleMove;
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * align the Paddle to the center.
     */
    public void alignToCenter() {
        this.paddle = new Rectangle(new Point(800 / 2 - this.paddle.getWidth() / 2,
                                    600 - 20 - GameLevel.getDistanceFromEdge()), this.paddle.getWidth(), 20);
    }

    /**
     * add the paddle
     * as a Sprite and as a Collidable object to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.getSpriteCollection().addSprite(this);
        g.getGameEnvironment().addCollidable(this);
    }

    /**
     * get the speed of the paddle.
     *
     * @return the speed of the paddle.
     */
    public int getPaddleMove() {
        return (int) paddleMoveOfDt;
    }

    /**
     * set the speed of the paddle.
     *
     * @param speed the speed of the paddle
     */
    public void setPaddleMove(int speed) {
        this.paddleMove = speed;
        this.paddleMoveOfDt = speed;
    }

}
