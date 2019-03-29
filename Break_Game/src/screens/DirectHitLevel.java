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
 * the Direct Hit Level is a level with 1 block and 1 ball.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-19
 */
public class DirectHitLevel implements LevelInformation, Sprite {

    private Screen screen;

    /**
     * Create the Direct Hit Level.
     */
    public DirectHitLevel() {
        this.screen = new Screen(new Point(GameLevel.getDistanceFromEdge(), GameLevel.getDistanceFromEdge() * 2),
                                 new Point(800 - GameLevel.getDistanceFromEdge(), 600));
        this.screen.setColor(Color.BLACK);
    }

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        list.add(new Velocity(0, -300));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 400;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return this;
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<Block>();
        list.add(new Block(new Point(800 / 2 - 20, 600 / 4 - 20), 40, 40, 1, Color.RED));
        return list;
    }

    @Override
    public List<Ball> balls() {
        List<Ball> list = new ArrayList<Ball>();
        Ball b = new Ball(800 / 2, (600 * 3) / 4, 5, Color.WHITE);
        b.setVelocity(this.initialBallVelocities().get(0));
        b.setScreenSize(this.screen.startPoint(), this.screen.endPoint());
        list.add(b);

        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }

    @Override
    public void drawOn(DrawSurface d) {
        screen.drawOn(d);

        // the center of the block
        Point p = new Point(d.getWidth() / 2, d.getHeight() / 4);

        d.setColor(Color.BLUE);
        d.drawCircle((int) p.getX(), (int) p.getY(), 40);
        d.drawCircle((int) p.getX(), (int) p.getY(), 65);
        d.drawCircle((int) p.getX(), (int) p.getY(), 90);

        // right line
        d.drawLine((int) p.getX() + 20 + 5, (int) p.getY(), (int) p.getX() + 100, (int) p.getY());
        // left line
        d.drawLine((int) p.getX() - 20 - 5, (int) p.getY(), (int) p.getX() - 100, (int) p.getY());
        // upper line
        d.drawLine((int) p.getX(), (int) p.getY() - 20 - 5, (int) p.getX(), (int) p.getY() - 100);
        // down line
        d.drawLine((int) p.getX(), (int) p.getY() + 20 + 5, (int) p.getX(), (int) p.getY() + 100);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }
}
