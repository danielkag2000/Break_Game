package readers;

import java.awt.Color;
import java.awt.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import biuoop.DrawSurface;

import game.GameLevel;

import interfaces.LevelInformation;
import interfaces.Sprite;

import objects.Ball;
import objects.Block;
import objects.BlocksFromSymbolsFactory;
import objects.Point;
import objects.Velocity;

/**
 * Specification of a Level from file.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public class LevelSpecificationReader {

    /**
     * read the levels file and return the levels.
     *
     * @param reader a file reader
     * @return a list of LevelInformation
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        BufferedReader buffReader = new BufferedReader(reader);
        String line = null;

        List<LevelInformation> levelList = new ArrayList<LevelInformation>();

        try {
            line = buffReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Problem with reading the text of the level");
        }

        while (line != null) {

            if (line.startsWith("START_LEVEL")) {

                try {
                    line = buffReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("Problem with reading the text of the level");
                }

                levelList.add(addLevel(line, buffReader));
            }


            try {
                line = buffReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Problem with reading the text of the level");
            }
        }
        return levelList;
    }

    /**
     * add level from the file.
     *
     * @param line the current line
     * @param buffReader the buffer reader
     * @return new level read from the file
     */
    private LevelInformation addLevel(String line, BufferedReader buffReader) {

        BlocksFromSymbolsFactory blocksLevelFactory = null;
        List<Velocity> ballsVelocity = new ArrayList<Velocity>();
        List<Block> blockList = new ArrayList<Block>();
        String levelName = null;
        int paddleSpeed = 0;
        int paddleWidth = 0;
        int xStart = 0;
        int yStart = 0;
        int numOfBlocks = 0;
        int rowHeight = 0;
        Sprite background = null;
        ColorsParser colorParse = new ColorsParser();
        ImageParser imageParse = new ImageParser();

        while (line != null && !line.startsWith("END_LEVEL")) {

            if (line.startsWith("START_BLOCKS")) {

                try {
                    line = buffReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("Problem with reading the text of the level");
                }
                addBlocks(line, buffReader, xStart, yStart, rowHeight, blockList, blocksLevelFactory);
            } else {
                Pattern pattern = Pattern.compile("[_.,a-zA-Z0-9-]+:[\\_ ,/.()a-zA-Z0-9-]+");
                Matcher matchLine = pattern.matcher(line);

                matchLine.find();

                String[] keyVal = new String[2];
                keyVal = line.substring(matchLine.start(), matchLine.end()).split(":");

                if (keyVal[0].equals("level_name")) {
                    levelName = keyVal[1];

                } else if (keyVal[0].equals("paddle_width")) {
                    paddleWidth = Integer.parseInt(keyVal[1]);

                } else if (keyVal[0].equals("paddle_speed")) {
                    paddleSpeed = Integer.parseInt(keyVal[1]);

                } else if (keyVal[0].equals("blocks_start_x")) {
                    xStart = Integer.parseInt(keyVal[1]);

                } else if (keyVal[0].equals("blocks_start_y")) {
                    yStart = Integer.parseInt(keyVal[1]);

                } else if (keyVal[0].equals("row_height")) {
                    rowHeight = Integer.parseInt(keyVal[1]);

                } else if (keyVal[0].equals("num_blocks")) {
                    numOfBlocks = Integer.parseInt(keyVal[1]);

                } else if (keyVal[0].equals("block_definitions")) {
                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(keyVal[1]);
                    blocksLevelFactory = BlocksDefinitionReader.fromReader(new InputStreamReader(is));

                } else if (keyVal[0].equals("background")) {
                    Color fill = colorParse.colorFromString(keyVal[1]);
                    // make the background
                    if (fill == null) {
                        if (imageParse.imgFromString(keyVal[1]) == null) {
                            throw new RuntimeException("Problem with read the background");
                        }
                        Image imgBackground = imageParse.imgFromString(keyVal[1]);
                        background = new Sprite() {
                            @Override
                            public void timePassed(double dt) {
                                return;
                            }
                            @Override
                            public void drawOn(DrawSurface d) {
                                d.drawImage(0, 0, imgBackground);
                            }
                       };
                    } else {
                        background = new Sprite() {
                            @Override
                            public void timePassed(double dt) {
                                return;
                            }
                            @Override
                            public void drawOn(DrawSurface d) {
                                d.setColor(fill);
                                d.fillRectangle(0, 0, 800, 600);
                            }
                        };
                    }

                } else if (keyVal[0].equals("ball_velocities")) {
                    Pattern velPattern = Pattern.compile("[0-9\\.-]+,[0-9\\.-]+");
                    Matcher matchVelocity = velPattern.matcher(keyVal[1]);

                    while (matchVelocity.find()) {
                        String[] velocity = keyVal[1].substring(matchVelocity.start(), matchVelocity.end()).split(",");
                        ballsVelocity.add(Velocity.fromAngleAndSpeed(Double.parseDouble(velocity[0]),
                                          Double.parseDouble(velocity[1])));
                    }
                }
            }

            try {
                line = buffReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Problem with reading the text of the level");
            }
        }

        if (levelName == null) {
            throw new RuntimeException("there is no level name");
        } else if (paddleSpeed == 0) {
            throw new RuntimeException("there is no speed for the paddle");
        } else if (paddleWidth <= 0) {
            throw new RuntimeException("there is no width for the paddle");
        } else if (numOfBlocks <= 0) {
            throw new RuntimeException("there is no blocks to remove");
        } else if (background == null) {
            throw new RuntimeException("there is no background");
        }

        for (Velocity v : ballsVelocity) {
            if (v == null) {
                throw new RuntimeException("there is velocity in at least one ball");
            }
        }
        for (Block b : blockList) {
            if (b == null) {
                throw new RuntimeException("at least one block doesn't exist");
            }
        }

        final String name = levelName;
        final Integer speed = new Integer(paddleSpeed);
        final Integer width = new Integer(paddleWidth);
        final Integer numBlocks = new Integer(numOfBlocks);
        final Sprite backgroundSpirit = background;
        // build the level
        return makeLevel(name, speed, width, numBlocks, backgroundSpirit, ballsVelocity, blockList);
    }

    /**
     * create the level from the file.
     *
     * @param name the level name.
     * @param speed the paddle speed
     * @param width the paddle width
     * @param numBlocks the number of blocks to remove
     * @param backgroundSpirit the background
     * @param blockList the blocks
     * @param ballsVelocity the velocity of the balls
     * @return the level from the file.
     */
    private LevelInformation makeLevel(String name, Integer speed, Integer width, Integer numBlocks,
        Sprite backgroundSpirit, List<Velocity> ballsVelocity, List<Block> blockList) {
        return new LevelInformation() {
            @Override
            public int paddleWidth() {
                return width;
            }
            @Override
            public int paddleSpeed() {
                return speed;
            }
            @Override
            public int numberOfBlocksToRemove() {
                return numBlocks;
            }
            @Override
            public int numberOfBalls() {
                return ballsVelocity.size();
            }
            @Override
            public String levelName() {
                return name;
            }
            @Override
            public List<Velocity> initialBallVelocities() {
                return ballsVelocity;
            }
            @Override
            public Sprite getBackground() {
                return backgroundSpirit;
            }
            @Override
            public List<Block> blocks() {
                return blockList;
            }
            @Override
            public List<Ball> balls() {
                List<Ball> balls = new ArrayList<Ball>();
                for (Velocity v : ballsVelocity) {
                    Ball ball = new Ball(new Point(400, 600 - GameLevel.getDistanceFromEdge() * 3), 5, Color.WHITE);
                    ball.setVelocity(v);
                    ball.setScreenSize(new Point(GameLevel.getDistanceFromEdge(), GameLevel.getDistanceFromEdge() * 2),
                                       new Point(800 - GameLevel.getDistanceFromEdge(), 600));
                    balls.add(ball);
                }
                return balls;
            }
        };
    }

    /**
     * add blocks to the level.
     * @param line the current line
     * @param buffReader the buffer reader
     * @param xStart the x start of the blocks
     * @param yStart the y start of the blocks
     * @param rowHeight the Height of the row
     * @param blockList the list of the blocks
     * @param blocksLevelFactory the block factory
     */
    private void addBlocks(String line, BufferedReader buffReader, int xStart, int yStart,
                           int rowHeight, List<Block> blockList, BlocksFromSymbolsFactory blocksLevelFactory) {
        int y = yStart;

        while (line != null && !line.startsWith("END_BLOCKS")) {

            int x = xStart;

            for (int i = 0; i < line.length(); i++) {

                if (blocksLevelFactory.isSpaceSymbol("" + line.charAt(i))) {
                    x += blocksLevelFactory.getSpaceWidth("" + line.charAt(i));
                } else if (blocksLevelFactory.isBlockSymbol("" + line.charAt(i))) {
                    Block block = blocksLevelFactory.getBlock("" + line.charAt(i), x, y);
                    blockList.add(block);
                    x += block.getCollisionRectangle().getWidth();
                }
            }

            y += rowHeight;

            try {
                line = buffReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Problem with reading the text of the level");
            }
        }
    }


}
