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
 * the Final Four Level is a level with 105 block and 3 ball,
 * take place between the clouds.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-20
 */
public class FinalFourLevel implements LevelInformation, Sprite {

    private Screen screen;
    private List<Color> colorArray;  // the colors of the lines

    /**
     * Create the Final Four Level.
     */
    public FinalFourLevel() {
        this.screen = new Screen(new Point(GameLevel.getDistanceFromEdge(), GameLevel.getDistanceFromEdge() * 2),
                                 new Point(800 - GameLevel.getDistanceFromEdge(), 600));
        this.screen.setColor(new Color(51, 130, 225));

        colorArray = new ArrayList<Color>();
        addColors(colorArray);
    }

    @Override
    public int numberOfBalls() {
        return 3;
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
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return this;
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<Block>();

        double width = (800 - GameLevel.getDistanceFromEdge() * 2) / 15.0;
        int height = 20;

        double x;
        int y = GameLevel.getDistanceFromEdge() + height * 4;

        for (int i = 0; i < 7; i++) {
            x = GameLevel.getDistanceFromEdge();
            int hitPoints = 1;

            if (i == 1) {
                hitPoints = 3;
            } else if ((i + 1) % 2 == 0) {
                hitPoints = 2;
            }

            for (int j = 0; j < 15; j++) {

                Block b = new Block(new Point(x, y), width, height, hitPoints, this.colorArray.get(i));
                list.add(b);
                x += width;
            }
            y += height;
        }

        return list;
    }

    @Override
    public List<Ball> balls() {
        List<Ball> list = new ArrayList<Ball>();

        double yBall = 600 - 100;
        int xBall = 300;

        Velocity v1, v2, v3;
        double angle;
        int speed = 420;

        Ball b1 = new Ball(xBall, (int) yBall, 5, Color.WHITE);
        angle = -50;
        v1 = Velocity.fromAngleAndSpeed(angle, speed);
        b1.setVelocity(v1);
        b1.setScreenSize(this.screen.startPoint(), this.screen.endPoint());

        Ball b2 = new Ball(800 - xBall, (int) yBall, 5, Color.WHITE);
        angle = 50;
        v2 = Velocity.fromAngleAndSpeed(angle, speed);
        b2.setVelocity(v2);
        b2.setScreenSize(this.screen.startPoint(), this.screen.endPoint());

        Ball b3 = new Ball(400, (int) yBall - 50, 5, Color.WHITE);
        v3 = new Velocity(0, -1 * speed);
        b3.setVelocity(v3);
        b3.setScreenSize(this.screen.startPoint(), this.screen.endPoint());

        list.add(b1);
        list.add(b2);
        list.add(b3);

        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 105;
    }

    @Override
    public void drawOn(DrawSurface d) {
        screen.drawOn(d);

        // all the "magic" numbers are custom to a screen of 800 X 600

        // draw the rain
        d.setColor(Color.WHITE);
        int y = 600 - 150;
        int x = 90;
        int xFactor = 9;
        for (int i = 0; i < 10; i++) {
            d.drawLine(x, y, x - 30, 600);
            x += xFactor;
        }

        y = 520;
        x = 800 - 200;
        for (int i = 0; i < 10; i++) {
            d.drawLine(x, y, x - 30, 600);
            x += xFactor;
        }

        // draw the clouds
        Color[] colors = new Color[3];
        colors[0] = new Color(219, 219, 219);
        colors[1] = (new Color(145, 145, 145)).brighter();
        colors[2] = new Color(190, 190, 190);

        // first cloud
        d.setColor(colors[0]);
        d.fillCircle(80, 600 - 180, 25);
        d.fillCircle(80 + 20, 600 - 160, 35);

        d.setColor(colors[1]);
        d.fillCircle(80 + 50, 600 - 180, 35);

        d.setColor(colors[2]);
        d.fillCircle(80 + 60, 600 - 150, 25);
        d.fillCircle(80 + 90, 600 - 170, 35);

        // second cloud
        d.setColor(colors[0]);
        d.fillCircle(600, 670 - 190, 25);
        d.fillCircle(600 + 20, 670 - 150, 35);

        d.setColor(colors[1]);
        d.fillCircle(600 + 50, 670 - 180, 35);

        d.setColor(colors[2]);
        d.fillCircle(600 + 65, 670 - 150, 25);
        d.fillCircle(600 + 90, 670 - 170, 35);
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
        colorArr.add(Color.GREEN);
        colorArr.add(Color.WHITE);
        colorArr.add(Color.PINK);
        colorArr.add(Color.CYAN);
    }

    @Override
    public void timePassed(double dt) {
        return;
    }
}
