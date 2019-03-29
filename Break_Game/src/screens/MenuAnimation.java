package screens;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import interfaces.Menu;

import objects.AnimationRunner;
import objects.Operation;

/**
 * the menu animation.
 *
 * @param <T> the return type
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public class MenuAnimation<T> implements Menu<T> {

    private List<Operation<T>> operationList;
    private List<Operation<Menu<T>>> menuList;
    private String title;
    private KeyboardSensor keyboard;
    private T status;
    private boolean stop;
    private AnimationRunner animationRunner;
    private Menu<T> runMenu;

    /**
     * the constructor create new menu.
     *
     * @param title the title of the menu
     * @param k the user keyboard
     */
    public MenuAnimation(String title, KeyboardSensor k) {
        this.title = title;
        this.keyboard = k;
        this.status = null;
        this.stop = false;
        this.operationList = new ArrayList<Operation<T>>();
        this.menuList = new ArrayList<Operation<Menu<T>>>();
        this.animationRunner = null;
        this.runMenu = null;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 800);

        d.setColor(Color.YELLOW.darker());
        d.drawText(50, 80, this.title, 60);

        int y = d.getHeight() / 2 - 50 * ((this.operationList.size() + this.menuList.size()) / 2);

        d.setColor(Color.ORANGE.darker());
        for (Operation<Menu<T>> operationMenu : this.menuList) {
            d.drawText(50, y, "Press " + operationMenu.getKey() + " to " + operationMenu.getName() + ".", 30);
            y += 50;
        }

        for (Operation<T> operation : this.operationList) {
            d.drawText(50, y, "Press " + operation.getKey() + " to " + operation.getName(), 30);
            y += 50;
        }

        boolean found = false;
        for (Operation<T> operation : this.operationList) {
            if (this.keyboard.isPressed(operation.getKey())) {
                this.status = operation.getTask();
                this.stop = true;
                found = true;
                break;
            }
        }

        for (Operation<Menu<T>> operationMenu : this.menuList) {

            if (this.keyboard.isPressed(operationMenu.getKey())) {
                this.runMenu = operationMenu.getTask();
                this.stop = true;
                found = true;
                break;
            }
        }

        if (!found) {
            this.status = null;
            this.stop = false;
        }
    }

    @Override
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.operationList.add(new Operation<T>(message, key, returnVal));
    }

    @Override
    public T getStatus() {

        if (this.runMenu != null) {

            while (this.runMenu != null) {
                this.animationRunner.run(this.runMenu);
                T menuStatus = this.runMenu.getStatus();

                if (menuStatus != null) {
                    this.runMenu = null;
                    return menuStatus;
                }
            }
        }
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.menuList.add(new Operation<Menu<T>>(message, key, subMenu));
    }

    /**
     * set a animation runner for the menu.
     *
     * @param ar the animation runner to set
     */
    public void setAnimationRunner(AnimationRunner ar) {
        this.animationRunner = ar;
    }
}
