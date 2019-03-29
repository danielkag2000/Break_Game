package interfaces;

/**
 * the menu of the game.
 *
 * @param <T> the return type
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public interface Menu<T> extends Animation {

    /**
     * add selection to the menu.
     *
     * @param key the key to press
     * @param message the message
     * @param returnVal the return value
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * return the status.
     *
     * @return the status
     */
    T getStatus();

    /**
     * add a sub Menu.
     *
     * @param key the key to press
     * @param message the message
     * @param subMenu the sub Menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
