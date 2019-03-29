package interfaces;

/**
 * task to do.
 *
 * @param <T> the return type
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public interface Task<T> {
   /**
    * run the task.
    *
    * @return the return value
    */
    T run();
}
