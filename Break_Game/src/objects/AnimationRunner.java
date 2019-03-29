package objects;

import biuoop.DialogManager;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import interfaces.Animation;

/**
 * Run the Animation.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-05-18
 */
public class AnimationRunner {

    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * create the AnimationRunner.
     *
     * @param gui the GUI
     * @param framesPerSecond the frames Per Second
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * open a dialog that ask for the user name.
     *
     * @return the user name
     */
    public String askForUserName() {
        DialogManager dialog = gui.getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "Anonymous");

        return name;
    }

    /**
     * Run the game -- start the animation loop.
     *
     * @param animation the animation to run
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        while (!animation.shouldStop()) {
           long startTime = System.currentTimeMillis(); // timing
           DrawSurface d = gui.getDrawSurface();

           animation.doOneFrame(d, 1.0 / ((double) framesPerSecond));

           gui.show(d);
           long usedTime = System.currentTimeMillis() - startTime;
           long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
           if (milliSecondLeftToSleep > 0) {
               this.sleeper.sleepFor(milliSecondLeftToSleep);
           }
        }
     }
}
