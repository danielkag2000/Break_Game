package objects;

import interfaces.Animation;
import interfaces.Task;

/**
 * show the player Scores task.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-07
 */
public class ShowHiScoresTask implements Task<Void> {

    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Create new ShowHiScoresTask.
     *
     * @param runner the Animation Runner
     * @param highScoresAnimation the Animation
     *                            of high score to run
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    @Override
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }

}
