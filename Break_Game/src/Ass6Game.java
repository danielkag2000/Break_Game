import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import game.GameFlow;

import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;

import objects.AnimationRunner;
import objects.HighScoresTable;
import objects.Operation;

import readers.LevelSpecificationReader;

import screens.HighScoresAnimation;
import screens.KeyPressStoppableAnimation;
import screens.MenuAnimation;

/**
 * the ass6game is the main that run the game.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-10
 */
public class Ass6Game {

    /**
     * the main method.
     *
     * @param args the relative path to the level sets.
     *             if arg.length == 0 it uses the default.
     */
    public static void main(String[] args) {

        if (args.length >= 2) {
            System.out.println("args should contain 1 path or not contain pathes at all");
            System.exit(1);
        }

        String path = null;

        try {
            path = args[0];
        } catch (Exception e) {
            path = "level_sets.txt";
        }

        GUI gui = new GUI("Arkanoid", 800, 600);
        AnimationRunner animationRunner = new AnimationRunner(gui, 60);
        GameFlow game = new GameFlow(animationRunner, gui.getKeyboardSensor());

        Menu<Task<Void>> menu = getMenu(animationRunner, gui.getKeyboardSensor(), path, game);
        game.runGame(menu);
    }

    /**
     * get the menu of the game.
     *
     * @param animationRunner the animation runner
     * @param k the user's keyboard
     * @param path the relative path in the resources directory
     * @param game the game.
     * @return the menu of the game
     */
    private static Menu<Task<Void>> getMenu(AnimationRunner animationRunner,
                                            KeyboardSensor k, String path, GameFlow game) {

        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", k);
        menu.setAnimationRunner(animationRunner);

        menu.addSubMenu("s", "start a new game", getSubMenu("Select Level", k, path, game));

        menu.addSelection("h", "see the high scores", new Task<Void>() {

            @Override
            public Void run() {
                animationRunner.run(new KeyPressStoppableAnimation(k, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(HighScoresTable.loadFromFile(new File("highscores")), "space")));
                return null;
            }

        });

        menu.addSelection("q", "quit", new Task<Void>() {

            @Override
            public Void run() {
                System.exit(0);
                return null;
            }

        });

        return menu;
    }

    /**
     * get the sub menu of the menu of the game.
     *
     * @param titel the title of the sub menu
     * @param k the user's keyboard
     * @param path the relative path in the resources directory
     * @param game the game.
     * @return the sub menu of the menu of the game
     */
    private static Menu<Task<Void>> getSubMenu(String titel, KeyboardSensor k, String path, GameFlow game) {

        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(titel, k);

        InputStreamReader is = null;
        try {
            is = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            System.out.println("Enable to read the file");
            System.exit(1);
        }
        LineNumberReader reader = new LineNumberReader(is);

        List<Operation<Task<Void>>> tasks = getTasksFromLevelSet(reader, game);

        for (Operation<Task<Void>> operation : tasks) {
            menu.addSelection(operation.getKey(), operation.getName(), operation.getTask());
        }

        return menu;
    }

    /**
     * get the tasks of the sub menu.
     *
     * @param reader the reader of the setting file
     * @param game the game
     * @return the tasks of the sub menu.
     */
    private static List<Operation<Task<Void>>> getTasksFromLevelSet(LineNumberReader reader, GameFlow game) {
        String key = null;
        String name = null;
        List<Operation<Task<Void>>> list = new ArrayList<Operation<Task<Void>>>();
        LevelSpecificationReader levelSepc = new LevelSpecificationReader();

        String line = null;
        reader.setLineNumber(-1);

        try {
            line = reader.readLine();

        } catch (IOException e) {
            System.out.println("Enable to read the file");
            System.exit(1);
        }

        while (line != null) {
            if (reader.getLineNumber() % 2 == 0) {

                String[] arr = line.split(":");

                if (arr[0].length() > 1 || arr[0].length() == 0) {
                    System.out.println("illegal key");
                    System.exit(1);
                }
                key = arr[0];
                name = arr[1];
            } else {

                Reader r = null;
                try {
                    r = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(line));
                } catch (Exception e) {
                    System.out.println("there is a problem with the path");
                    System.exit(1);
                }

                List<LevelInformation> getLevels = null;
                try {
                    getLevels = levelSepc.fromReader(r);

                    if (getLevels == null) {
                        System.out.println("Problem with read the file");
                        System.exit(1);
                    }
                } catch (Exception e) {
                    System.out.println("Problem with read the file");
                    System.exit(1);
                }

                final List<LevelInformation> levels = getLevels;
                Task<Void> task = new Task<Void>() {
                    @Override
                    public Void run() {
                        game.runLevels(levels);
                        return null;
                    }
                };

                list.add(new Operation<Task<Void>>(name, key, task));
            }

            try {
                line = reader.readLine();

            } catch (IOException e) {
                System.out.println("Enable to read the file");
                System.exit(1);
            }
        }
        return list;
    }
}
