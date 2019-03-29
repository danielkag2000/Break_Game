package screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import interfaces.LevelInformation;
import interfaces.Sprite;

import biuoop.DrawSurface;

import game.GameLevel;

import objects.Ball;
import objects.Block;
import objects.Point;
import objects.Screen;
import objects.Velocity;

/**
 * Green 3 Level, in the green color
 * with 40 blocks and 2 balls and a building.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-20
 */
public class Green3Level implements LevelInformation, Sprite {

    private Screen screen;
    private List<Color> colorArray;  // the colors of the lines

    /**
     * Create the Green 3 Level.
     */
    public Green3Level() {
        this.screen = new Screen(new Point(GameLevel.getDistanceFromEdge(), GameLevel.getDistanceFromEdge() * 2),
                                 new Point(800 - GameLevel.getDistanceFromEdge(), 600));
        this.screen.setColor((new Color(44, 114, 37)));

        colorArray = new ArrayList<Color>();
        addColors(colorArray);
    }

    @Override
    public int numberOfBalls() {
        return this.balls().size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();

        List<Ball> balls = this.balls();

        for (int i = 0; i < balls.size(); i++) {
            list.add(balls.get(i).getVelocity());
        }

        return list;
    }

    @Override
    public int paddleSpeed() {
        return 600;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return this;
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<Block>();

        int width = 50;
        int height = 25;

        int x;
        int y = GameLevel.getDistanceFromEdge() + height * 5;

        for (int i = 0; i < 5; i++) {
            x = 800 - GameLevel.getDistanceFromEdge();
            int numberInLine = 10 - i;

            int hitPoints = 1;

            if ((i + 1) % 2 == 0) {
                hitPoints = 2;
            }

            for (int j = 0; j < numberInLine; j++) {
                Block b = new Block(new Point(x - width, y),
                                    width, height, hitPoints, this.colorArray.get(i));
                list.add(b);
                x -= width;
            }
            y += height;
        }

        return list;
    }

    @Override
    public List<Ball> balls() {
        List<Ball> list = new ArrayList<Ball>();

        double yBall = 600 - 200;
        int xBall = 274;

        int yCenter = 600 - GameLevel.getDistanceFromEdge() - 20;
        int xCenter = 400;

        Velocity v1, v2;
        double angle;
        int speed = 420;

        Ball b1 = new Ball(xBall, (int) yBall, 5, Color.WHITE);
        angle = -1 * Math.toDegrees(Math.atan((yBall - yCenter) / (xBall - xCenter)));
        v1 = Velocity.fromAngleAndSpeed(angle, speed);
        b1.setVelocity(v1);
        b1.setScreenSize(this.screen.startPoint(), this.screen.endPoint());

        Ball b2 = new Ball(800 - xBall, (int) yBall, 5, Color.WHITE);
        angle = Math.toDegrees(Math.atan((yBall - yCenter) / (xBall - xCenter)));
        v2 = Velocity.fromAngleAndSpeed(angle, speed);
        b2.setVelocity(v2);
        b2.setScreenSize(this.screen.startPoint(), this.screen.endPoint());

        list.add(b1);
        list.add(b2);

        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }

    @Override
    public void drawOn(DrawSurface d) {
        screen.drawOn(d);

        // all the "magic" numbers are custom to a screen of 800 X 600

        // draw the building
        d.setColor(Color.BLACK);
        d.fillRectangle(GameLevel.getDistanceFromEdge() * 3, 600 - 150, 100, 150);

        d.setColor(Color.WHITE);
        int x;
        int y = 600 - 150 + 10;
        int width = 100 / 5 - 8;
        int height = 150 / 5 - 5;

        for (int i = 0; i < 5; i++) {
            x = GameLevel.getDistanceFromEdge() * 3 + 10;
            for (int j = 0; j < 5; j++) {
                d.fillRectangle(x, y, width, height);

                x += width + 5;
            }
            y += height + 5;
        }

        // draw the antena
        d.setColor(new Color(57, 55, 62));
        d.fillRectangle(GameLevel.getDistanceFromEdge() * 3 + 50 - 15, 600 - 150 - 60, 30, 60);

        d.setColor((new Color(57, 55, 62)).brighter());
        d.fillRectangle(GameLevel.getDistanceFromEdge() * 3 + 50 - 6, 600 - 150 - 60 - 200, 12, 200);

        // the circles
        Point p = new Point(GameLevel.getDistanceFromEdge() * 3 + 50, 600 - 150 - 60 - 200 - 10);

        d.setColor(new Color(238, 189, 115));
        d.fillCircle((int) p.getX(), (int) p.getY(), 11);

        d.setColor(new Color(249, 102, 66));
        d.fillCircle((int) p.getX(), (int) p.getY(), 8);

        d.setColor(Color.WHITE);
        d.fillCircle((int) p.getX(), (int) p.getY(), 3);
    }

    /**
     * set the colors that we use in the level.
     *
     * @param colorArr the list to add to the colors
     */
    private void addColors(List<Color> colorArr) {
        colorArr.add(Color.GRAY);
        colorArr.add(Color.RED);
        colorArr.add(Color.YELLOW);
        colorArr.add(Color.BLUE);
        colorArr.add(Color.WHITE);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }
}
