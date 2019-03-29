package objects;

/**
 * count the amount.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-04-17
 */
public class Counter {

    private int count;

    /**
     * A new counter with 0.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * the constructor of the Counter.
     *
     * @param num the number of the counter
     */
    public Counter(int num) {
        this.count = num;
    }

    /**
     * add number to current count.
     *
     * @param number the number to increase with
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * decrease the number from current count.
     *
     * @param number the number to decrease with
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * get the value of counter.
     *
     * @return the count
     */
    public int getValue() {
        return this.count;
    }
}
