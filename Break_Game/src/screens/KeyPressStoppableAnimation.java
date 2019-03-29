package screens;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * Waiting for key to be pressed.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public class KeyPressStoppableAnimation implements Animation {

    private String key;
    private KeyboardSensor keyboard;
    private boolean stop;
    private Animation animation;
    private boolean isAlreadyPressed;

    /**
     * the constructor.
     *
     * @param k the user's keyboard
     * @param key the key to press
     * @param animation the animation to run
     */
    public KeyPressStoppableAnimation(KeyboardSensor k, String key, Animation animation) {
        this.key = key;
        this.stop = false;
        this.keyboard = k;
        this.animation = animation;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        if (this.keyboard.isPressed(key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
        this.animation.doOneFrame(d, dt);
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }

}
