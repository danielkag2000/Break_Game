package objects;

/**
 * represent the operation to do.
 *
 * @param <T> the return type
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-07
 */
public class Operation<T> {

    private String operationName;
    private String key;
    private T operationTask;

    /**
     * Create a new operation.
     *
     * @param name the operation name
     * @param key the operation key
     * @param task the operation task
     */
    public Operation(String name, String key, T task) {
        this.operationName = name;
        this.key = key;
        this.operationTask = task;
     }

    /**
     * get the operation name.
     *
     * @return the operation name
     */
    public String getName() {
        return operationName;
    }

    /**
     * get the operation key.
     *
     * @return the operation key
     */
    public String getKey() {
        return key;
    }

    /**
     * get the operation Task.
     *
     * @return the operation Task
     */
    public T getTask() {
        return operationTask;
    }
}
