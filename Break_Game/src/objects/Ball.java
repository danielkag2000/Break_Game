package objects;

import java.awt.Color;

import game.GameLevel;

import interfaces.Sprite;

import biuoop.DrawSurface;

/**
 * A ball in the 2D.
 * which represented by radius, color and location.
 *
 * @author Daniel Kaganovich
 * @version 1.1
 * @since 2018-03-23
 */
public class Ball implements Sprite {

    private int size;  // the radius of the ball
    private Color color;  // the color of the ball
    private Point location;  // the location of the ball in [XY] axes
    private Velocity velocity; // the velocity of the ball
    private Point strartP;  // the start point of the screen
    private Point endP;  // the end point of the screen
    private GameEnvironment gameEnv;  // the GameEnvironment of the ball

    /**
     * The ball constructor
     * for the values: radius, location and color.
     *
     * @param center the initial location of the ball
     * @param r the size of the ball
     * @param color the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.size = r;
        this.color = color;
        this.location = center;
        this.velocity = new Velocity(0, 0);  // the ball is static
        setScreenSize(new Point(0, 0), new Point(800, 800));  // default
        this.gameEnv = null;
    }

    /**
     * The ball constructor
     * for the values: x value location, y value location, location and color.
     *
     * @param x the initial x location of the ball in the [XY] axes
     * @param y the initial y location of the ball in the [XY] axes
     * @param r the size of the ball
     * @param color the color of the ball
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this (new Point(x, y), r, color);
    }

    /**
     * The ball constructor
     * for the values: x value location, y value location, location and color.
     *
     * @param b the ball to copy
     */
    public Ball(Ball b) {
        this (new Point(b.getX(), b.getY()), b.size, b.color);
    }

    /**
     * get the x value of the center of the ball in [XY] axes.
     *
     * @return the x value of the ball in the [XY] axes
     */
    public int getX() {
        return (int) this.location.getX();
    }

    /**
     * get the y value of the center of the ball in [XY] axes.
     *
     * @return the y value of the ball in the [XY] axes
     */
    public int getY() {
        return (int) this.location.getY();
    }

    /**
     * get the size of the ball.
     *
     * @return the size of the ball (radius)
     */
    public int getSize() {
        return this.size;
    }

    /**
     * get the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * get the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * set the velocity to the velocity
     * the function gets.
     *
     * @param v the velocity to change to
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * setting the screen size for the ball to move on.
     *
     * @param p1 first point
     * @param p2 second point
     */
    public void setScreenSize(Point p1, Point p2) {
        this.strartP = new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));  // top left point
        this.endP = new Point(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));  // bottom right point
        double posX = this.location.getX();  // the x position of the center
        double posY = this.location.getY();  // the y position of the center

        if (this.location.getX() - this.size < this.strartP.getX()) {
            posX = this.strartP.getX() + this.size;
        } else if (this.location.getX() + this.size > this.endP.getX()) {
            posX = this.endP.getX() - this.size;
        }

        if (this.location.getY() - this.size < this.strartP.getY()) {
            posY = this.strartP.getY() + this.size;
        } else if (this.location.getY() + this.size > this.endP.getY()) {
            posY = this.endP.getY() - this.size;
        }

        this.location = new Point(posX, posY);
    }

    /**
     * setting the Game Environment for the ball.
     *
     * @param ge the Game Environment
     */
    public void setGameEnvironment(GameEnvironment ge) {
        this.gameEnv = ge;
    }

    /**
     * set the velocity to the velocity with dx and dy
     * the function gets.
     * @param dx the change in position on [X] axis
     * @param dy the change in position on [Y] axis
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);  // set the color to the ball color
        surface.fillCircle(this.getX(), this.getY(), this.size);  // create the circle
        surface.setColor(Color.BLACK);  // set the color to the border of the ball
        surface.drawCircle(this.getX(), this.getY(), this.size);  // create the circle
    }

    /**
     * moving one step forward.
     *
     * @param dt the amount of seconds passed since the last call
     */
    public void moveOneStep(double dt) {

        Velocity dtVec = new Velocity(this.velocity.getDx() * dt, this.velocity.getDy() * dt);

        Point nextPoint = dtVec.applyToPoint(this.location);  // the next point of movement
        Line trajectory = new Line(this.location, nextPoint);  // the way without obstacles
        CollisionInfo theClosestObstacle = this.gameEnv.getClosestCollision(trajectory);
        if (theClosestObstacle != null) {

            this.location = moveTheBallToClosePoint(theClosestObstacle.collisionPoint(), dtVec);

            Block insideBlock = pointInblock();
            if (insideBlock != null) {
                this.location = moveTheBallOutOfTheBlock(insideBlock, dtVec);
            }

            dtVec = theClosestObstacle.collisionObject().hit(this, theClosestObstacle.collisionPoint(), dtVec);

        } else {
                this.location = dtVec.applyToPoint(this.location);
        }

        ///////////////////
        Paddle pad = this.getGamePaddle();
        double epsilone = 0.01;

        //System.out.println("velocity is: " + this.velocity);
        if (pad != null) {
            if (this.isInPaddle(pad, this.location)) {
                this.location = moveFromPaddle(pad, dtVec);

                /*
                double paddleUpperY = pad.getCollisionRectangle().getUpperLeft().getY();
                double newX = pad.getCollisionRectangle().getUpperLeft().getX()
                              + pad.getCollisionRectangle().getWidth() / 2;

                this.location = new Point(newX, paddleUpperY - epislon);
                this.velocity = pad.hit(new Point(newX, paddleUpperY), this.velocity);
                */
            } else if (theClosestObstacle != null) {
                if (theClosestObstacle.collisionObject() instanceof Paddle) {
                    if (this.location.getY() > pad.getCollisionRectangle().getUpperLeft().getY()) {
                        dtVec.setDx(-1 * dtVec.getDx());
                    }
                }
            }

            /*if (this.velocity.getDy() > 0 && this.location.getY() > this.endP.getY() - epsilone) {
                this.location = new Point(this.location.getX(), this.endP.getY() - epsilone);
                this.velocity.setDy(-1 * Math.abs(this.velocity.getDy()));
            }*/
        }

        // check if the block is not go out of the frame
        if (this.location.getX() > this.endP.getX()) {
            this.location = new Point(this.endP.getX() - epsilone, this.location.getY());
            dtVec.setDx(Math.abs(-1 * dtVec.getDx()));

        } else if (this.location.getX() < this.strartP.getX()) {
            this.location = new Point(this.strartP.getX() + epsilone, this.location.getY());
            dtVec.setDx(Math.abs(dtVec.getDx()));
        }

        if (this.location.getY() < this.strartP.getY() + epsilone) {
            this.location = new Point(this.location.getX(), this.strartP.getY() + epsilone);
            dtVec.setDy(Math.abs(dtVec.getDy()));

        }
        /*else if (this.location.getY() > this.endP.getY() - epsilone) {
            this.location = new Point(this.location.getX(), this.endP.getY() - epsilone);
            this.velocity.setDy(-1 * Math.abs(this.velocity.getDy()));
        }*/

        // check if the ball not enter to a block
        Block insideBlock = pointInblock();
        if (insideBlock != null) {
            this.location = moveTheBallOutOfTheBlock(insideBlock, dtVec);
        }

        this.velocity = new Velocity(dtVec.getDx() / dt, dtVec.getDy() / dt);
        //System.out.println("location: " + this.location + "   velocity: " + this.velocity);
        ///////////////////
    }

    /**
     * moving the ball outside the block.
     *
     * @param block the block
     * @param dtVec the velocity
     * @return a point outside the block
     */
    private Point moveTheBallOutOfTheBlock(Block block, Velocity dtVec) {
        double epsilon = 0.01;

        double valueX = this.location.getX();
        double valueY = this.location.getY();

        double startXblock = block.getCollisionRectangle().getUpperLeft().getX();
        double startYblock = block.getCollisionRectangle().getUpperLeft().getY();
        double endXblock = block.getCollisionRectangle().getLineArr()[1].end().getX();
        double endYblock = block.getCollisionRectangle().getLineArr()[1].end().getY();

        if (dtVec.getDx() > 0) {
            valueX = startXblock - epsilon;
        } else if (dtVec.getDx() < 0) {
            valueX = endXblock + epsilon;
        }

        if (dtVec.getDy() > 0) {
            valueY = startYblock - epsilon;

        } else if (dtVec.getDy() < 0) {
            valueY = endYblock + epsilon;
        }

        return new Point(valueX, valueY);
    }

    /**
     * check if the ball in any block.
     *
     * @return if exist block the ball inside it return the block
     *         otherwise null.
     */
    private Block pointInblock() {
        for (int i = 0; i < this.gameEnv.getSize(); i++) {
            if (this.gameEnv.getIndexI(i) instanceof Block) {
                Rectangle rec = this.gameEnv.getIndexI(i).getCollisionRectangle();
                if (rec.inPointInRec(this.location)) {
                    return (Block) this.gameEnv.getIndexI(i);
                }
            }
        }
        return null;
    }

    /**
     * in a case that the ball intersect with the vertical lines of the paddle.
     *
     * @param pad the paddle
     * @param dtVec the velocity
     * @return a new Point outside the paddle with the current speed
     */
    private Point moveFromPaddle(Paddle pad, Velocity dtVec) {
        double epsilone = 0.015;
        double midPaddle = pad.getCollisionRectangle().getUpperLeft().getX()
                           + pad.getCollisionRectangle().getWidth() / 2;
        Point newPoint = new Point(this.location.getX(), this.location.getY());
        if (dtVec.getDx() > 0 && this.location.getX() <= midPaddle) {
            dtVec.setDx(-1 * dtVec.getDx());

            if (pad.getCollisionRectangle().getUpperLeft().getX() > pad.getPaddleMove() + epsilone) {
                newPoint = new Point(pad.getCollisionRectangle().getUpperLeft().getX() - pad.getPaddleMove() - epsilone,
                                        this.location.getY());
            } else {
                newPoint = new Point(epsilone, this.location.getY());
            }

        } else if (dtVec.getDx() < 0 && this.location.getX() <= midPaddle) {
            if (pad.getCollisionRectangle().getUpperLeft().getX() > pad.getPaddleMove() + epsilone) {
                newPoint = new Point(pad.getCollisionRectangle().getUpperLeft().getX() - pad.getPaddleMove() - epsilone,
                                        this.location.getY());
            } else {
                newPoint = new Point(epsilone, this.location.getY());
            }

        } else if (dtVec.getDx() < 0 && this.location.getX() > midPaddle) {
            dtVec.setDx(-1 * dtVec.getDx());

            if (pad.getCollisionRectangle().getLineArr()[1].end().getX() < this.endP.getX()
                                                                           - pad.getPaddleMove() - epsilone) {
                newPoint = new Point(pad.getCollisionRectangle().getLineArr()[1].end().getX() + pad.getPaddleMove()
                                     + epsilone, this.location.getY());
            } else {
                newPoint = new Point(this.endP.getX() - epsilone, this.location.getY());
            }

        } else if (dtVec.getDx() > 0 && this.location.getX() > midPaddle) {

            if (pad.getCollisionRectangle().getLineArr()[1].end().getX() < this.endP.getX()
                                                                           - pad.getPaddleMove() - epsilone) {
                newPoint = new Point(pad.getCollisionRectangle().getLineArr()[1].end().getX() + pad.getPaddleMove()
                                     + epsilone, this.location.getY());
            } else {
                newPoint = new Point(this.endP.getX() - epsilone, this.location.getY());
            }

        } else {  // the velocity == 0
            /*this.velocity = pad.hit(this, new Point(this.location.getX(),
                                      pad.getCollisionRectangle().getUpperLeft().getY()),
                                      this.velocity);
            newPoint = new Point(this.location.getX(), pad.getCollisionRectangle().getUpperLeft().getY() - epsilone);*/

            if (this.location.getX() > midPaddle) {

                if (pad.getCollisionRectangle().getLineArr()[1].end().getX() < this.endP.getX()
                        - pad.getPaddleMove() - epsilone) {

                    newPoint = new Point(pad.getCollisionRectangle().getLineArr()[1].end().getX() + pad.getPaddleMove()
                               + epsilone, this.location.getY());
                } else {
                    newPoint = new Point(this.endP.getX() - epsilone, this.location.getY());
                }
            } else {

                if (pad.getCollisionRectangle().getUpperLeft().getX() > pad.getPaddleMove() + epsilone) {
                    newPoint = new Point(pad.getCollisionRectangle().getUpperLeft().getX()
                                         - pad.getPaddleMove() - epsilone,
                                            this.location.getY());
                } else {
                    newPoint = new Point(epsilone, this.location.getY());
                }
            }
        }

       /** if (this.velocity.getDy() > 0 && ((newPoint.getY() > this.endP.getY() - epsilone)
                                            || this.location.getY() > this.endP.getY() - epsilone)) {

            newPoint = new Point(newPoint.getX(), this.endP.getY() - epsilone);
            this.velocity.setDy(-1 * this.velocity.getDy());
        }*/
        return newPoint;
    }

    /**
     * check if the point in the paddle.
     *
     * @param pad the paddle
     * @param p the point to check
     * @return true if in the paddle else, false
     */
    private boolean isInPaddle(Paddle pad, Point p) {
        Rectangle rec = pad.getCollisionRectangle();
        return rec.inPointInRec(p);
    }

    /**
     * moving the ball almost to the point.
     *
     * @param pointToGo the point to go to
     * @param dtVec the velocity
     * @return the new ball location
     */
    private Point moveTheBallToClosePoint(Point pointToGo, Velocity dtVec) {
        double valueX = pointToGo.getX();  // the x value
        double valueY = pointToGo.getY();  // the y value
        double epsilon = 0.015;

        if (dtVec.getDx() > 0) {
            valueX -= epsilon;
        } else if (dtVec.getDx() < 0) {
            valueX += epsilon;
        }

        if (dtVec.getDy() > 0) {
            valueY -= epsilon;
        } else if (dtVec.getDy() < 0) {
            valueY += epsilon;
        }

        return new Point(valueX, valueY);
    }

    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * add the ball as a Sprite to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.getSpriteCollection().addSprite(this);
    }

    /**
     * get the game paddle.
     *
     * @return the game paddle
     */
    private Paddle getGamePaddle() {
       for (int i = 0; i < this.gameEnv.getSize(); i++) {
           if (this.gameEnv.getIndexI(i) instanceof Paddle) {
                return (Paddle) this.gameEnv.getIndexI(i);
           }
       }
        return null;
    }

    /**
     * remove the block from the game.
     *
     * @param game the Game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

}
