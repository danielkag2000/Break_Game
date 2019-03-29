package game;

import screens.CountdownAnimation;
import screens.KeyPressStoppableAnimation;
import screens.PauseScreen;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import indicators.LevelIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;

import interfaces.Animation;
import interfaces.Sprite;
import interfaces.Collidable;
import interfaces.LevelInformation;

import objects.AnimationRunner;
import objects.Ball;
import objects.Block;
import objects.Counter;
import objects.GameEnvironment;
import objects.Paddle;
import objects.Point;
import objects.Screen;
import objects.SpriteCollection;

import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

/**
 * The GameLevel objects
 * have all the information of the levels,
 * and run them.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-19
 */
public class GameLevel implements Animation  {

    private LevelInformation levelInfo;  // the level information

    private SpriteCollection sprites;  // the Spirits collection
    private GameEnvironment environment;  // the Game Environment

    private biuoop.KeyboardSensor keyboard;  // the players keyboard

    private Screen screen;  // the screen of the game

    private static final int DISTANCE_FROM_EDGE = 20; // the distance from the edge

    private Paddle paddle;
    private Block deathRegion;  // the death region

    // Indicators
    private ScoreIndicator showScore;
    private LivesIndicator showLives;
    private LevelIndicator showLevel;

    // counters
    private Counter ballsCounter;
    private Counter remainingBlocks;
    private Counter scoreCounter;
    private Counter live;

    // Listeners
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private ScoreTrackingListener scoreListener;

    // Animation
    private AnimationRunner runner;
    private boolean running;

    /**
     * Create a new Game with the levels.
     *
     * @param levelInfo the order of the level to run
     * @param ks the player's keyboard
     * @param runner the animation runner
     * @param scoreCounter the score of the player
     * @param live the number of lives of the player
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor ks, AnimationRunner runner,
                     Counter scoreCounter, Counter live) {
        this.levelInfo = levelInfo;
        this.keyboard = ks;
        this.runner = runner;

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        this.screen = new Screen(800, 600);  // the Screen of the game

        this.scoreCounter = scoreCounter;
        this.live = live;
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {

        // initialize runing
        this.running = false;

        // initialize counters
        this.ballsCounter = new Counter();
        this.remainingBlocks = new Counter(this.levelInfo.numberOfBlocksToRemove());

        // initialize Listeners
        blockRemover = new BlockRemover(this, this.remainingBlocks);
        ballRemover = new BallRemover(this, this.ballsCounter);
        this.scoreListener = new ScoreTrackingListener(this.scoreCounter);

        // initialize Indicator
        this.showScore = new ScoreIndicator(this.scoreCounter);
        this.showLives = new LivesIndicator(this.live);
        this.showLevel = new LevelIndicator(this.levelInfo.levelName());

        this.sprites.addSprite(this.levelInfo.getBackground());

        // initialize for the death region
        this.deathRegion = new Block(new Point(0, this.screen.height() + 10), this.screen.width(),
                                     100, 0, java.awt.Color.GRAY);
        this.deathRegion.addToGame(this);
        this.deathRegion.addHitListener(ballRemover);

        // create the level objects
        this.createBorderBlocks();
        this.createBlocks();

        this.paddle = new Paddle(this.keyboard, new Point(this.screen.width() / 2 - this.levelInfo.paddleWidth() / 2,
                                 this.screen.height() - 20 - DISTANCE_FROM_EDGE),
                                 this.levelInfo.paddleWidth(), 20, DISTANCE_FROM_EDGE,
                                 this.screen.width() - DISTANCE_FROM_EDGE);

        this.paddle.setPaddleMove(this.levelInfo.paddleSpeed());
        this.paddle.addToGame(this);

        // add the indicators spirits
        this.sprites.addSprite(this.showScore);
        this.sprites.addSprite(this.showLives);
        this.sprites.addSprite(this.showLevel);
    }

    /**
     * Run one turn the game -- start the animation loop.
     */
    public void playOneTurn() {

        this.createBalls();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites)); // count down before turn starts.

        this.running = true;
        this.runner.run(this);
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        if (this.ballsCounter.getValue() <= 0) {
            this.running = false;
        }

        if (this.remainingBlocks.getValue() <= 0) {
            this.running = false;
        }

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        while ((this.remainingBlocks.getValue() > 0)
                && this.live.getValue() > 0) {
            this.playOneTurn();

            if (this.remainingBlocks.getValue() <= 0) {
                this.scoreCounter.increase(100);
                break;

            } else {
                this.paddle.alignToCenter();
                this.live.decrease(1);
            }
        }
    }

    /**
     * A get function of the Game Environment of the game.
     *
     * @return the Game Environment of the game
     */
    public GameEnvironment getGameEnvironment() {
        return this.environment;
    }

    /**
     * A get function of the Sprite Collection of the game.
     *
     * @return the SpriteCollection of the game
     */
    public SpriteCollection getSpriteCollection() {
        return this.sprites;
    }

    /**
     * remove from the game the Collidable object.
     *
     * @param c the Collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove from the game the Spirit object.
     *
     * @param s the Spirit object to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * create the Blocks.
     */
    public void createBlocks() {
        for (Block block : this.levelInfo.blocks()) {
            Block b = new Block(block);
            b.addToGame(this);
            b.addHitListener(this.blockRemover);
            b.addHitListener(this.scoreListener);
        }
    }

    /**
     * create the Balls.
     */
    public void createBalls() {
        for (Ball ball : this.levelInfo.balls()) {
            ball.addToGame(this);
            ball.setGameEnvironment(this.environment);
        }
        this.ballsCounter.increase(this.levelInfo.numberOfBalls());
    }

    /**
     * create the Blocks of the border.
     */
    public void createBorderBlocks() {
        Block b;  // the blocks that closes the game area

        // upper block
        b = new Block(new Point(0, DISTANCE_FROM_EDGE), this.screen.width(),
                      DISTANCE_FROM_EDGE, 0, java.awt.Color.GRAY);
        b.addToGame(this);

        // right block
        b = new Block(new Point(this.screen.width() - DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE + DISTANCE_FROM_EDGE),
                       DISTANCE_FROM_EDGE, this.screen.height(), 0, java.awt.Color.GRAY);
        b.addToGame(this);

        // left block
        b = new Block(new Point(0, DISTANCE_FROM_EDGE + DISTANCE_FROM_EDGE), DISTANCE_FROM_EDGE,
                        this.screen.height(), 0, java.awt.Color.GRAY);
        b.addToGame(this);
/*
        // down block
        b = new Block(new Point(DISTANCE_FROM_EDGE, this.screen.height() - DISTANCE_FROM_EDGE),
                        this.screen.width() - DISTANCE_FROM_EDGE,
                        DISTANCE_FROM_EDGE, 0, java.awt.Color.GRAY);
        b.addToGame(this);*/
    }

    /**
     * get the DISTANCE FROM EDGE.
     *
     * @return the DISTANCE FROM EDGE.
     */
    public static int getDistanceFromEdge() {
        return GameLevel.DISTANCE_FROM_EDGE;
    }
}
