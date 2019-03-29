package screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

import game.GameLevel;

import interfaces.LevelInformation;
import interfaces.Sprite;

import objects.Ball;
import objects.Block;
import objects.Point;
import objects.Screen;
import objects.Velocity;

/**
 * the Wide Easy Level is a level with 15 block and 10 ball.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-19
 */
public class WideEasyLevel implements LevelInformation, Sprite {

    private Screen screen;

    /**
     * Create the Wide Easy Level.
     */
    public WideEasyLevel() {
        this.screen = new Screen(new Point(GameLevel.getDistanceFromEdge(), GameLevel.getDistanceFromEdge() * 2),
                                 new Point(800 - GameLevel.getDistanceFromEdge(), 600));
        this.screen.setColor(Color.WHITE);
    }

    @Override
    public int numberOfBalls() {
        return 10;
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
        return 400;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return this;
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<Block>();

        int y = 280;
        double x = GameLevel.getDistanceFromEdge();
        int height = 20;
        double width = (800 - GameLevel.getDistanceFromEdge() * 2) / 15.0;
        Color c = Color.WHITE;

        for (int i = 0; i < 15; i++) {
            if (i < 2) {
                c = Color.RED;
            } else if (i < 4) {
                c = Color.ORANGE;
            } else if (i < 6) {
                c = Color.YELLOW;
            } else if (i < 9) {
                c = Color.GREEN;
            } else if (i < 11) {
                c = Color.BLUE;
            } else if (i < 13) {
                c = Color.PINK;
            } else if (i < 15) {
                c = Color.CYAN;
            }
            Block b = new Block(new Point(x, y), width, height, 1, c);
            list.add(b);
            x += width;
        }
        return list;
    }

    @Override
    public List<Ball> balls() {
        List<Ball> list = new ArrayList<Ball>();
        int xFactor = 42;

        double yBall = 600 - 200;
        int xBall = 190;

        int yCenter = 600 - GameLevel.getDistanceFromEdge() - 20;
        int xCenter = 400;

        int angleFactor = 10;
        double r = Math.sqrt(Math.pow(yBall - yCenter, 2) + Math.pow(xBall - xCenter, 2));

        double angle;
        int speed = 420;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.numberOfBalls() / 2; j++) {

                yBall = 600 - Math.sqrt(Math.pow(r, 2) - Math.pow(xBall - xCenter, 2));

                Ball b = new Ball(xBall, (int) yBall, 5, Color.WHITE);

                if (i == 1) {
                    angle = 90 + Math.toDegrees(Math.atan((yBall - yCenter) / (xBall - xCenter))) - angleFactor;
                } else {
                    angle = -90 + Math.toDegrees(Math.atan((yBall - yCenter) / (xBall - xCenter))) + angleFactor;
                }

                Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
                b.setVelocity(v);
                b.setScreenSize(this.screen.startPoint(), this.screen.endPoint());
                list.add(b);

                xBall += xFactor;
            }

            xBall += xFactor;
        }

        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }

    @Override
    public void drawOn(DrawSurface d) {
        screen.drawOn(d);

        Point p = new Point(150, 150);

        int x = GameLevel.getDistanceFromEdge();
        int xFactor = 7;
        int num = (800 - GameLevel.getDistanceFromEdge() * 2 - 76) / xFactor;

        d.setColor(new Color(255, 245, 204));

        for (int i = 0; i < num; i++) {
            d.drawLine((int) p.getX(), (int) p.getY(), x, 280);
            x += xFactor;
        }


        d.setColor(new Color(255, 245, 204));
        d.fillCircle((int) p.getX(), (int) p.getY(), 65);

        d.setColor(new Color(243, 211, 114));
        d.fillCircle((int) p.getX(), (int) p.getY(), 50);

        d.setColor(new Color(253, 228, 53));
        d.fillCircle((int) p.getX(), (int) p.getY(), 40);

    }

    @Override
    public void timePassed(double dt) {
        return;
    }
}
