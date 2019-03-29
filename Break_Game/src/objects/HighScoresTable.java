package objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * the table of the best player's score.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-06
 */
public class HighScoresTable implements Serializable {

    /**
     * the serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private int size;
    private static final int DEFULT_SIZE = 5;
    private ScoreInfo[] table;

    /**
     * Create an empty high-scores table with the specified size.
     *
     * @param size means that the table holds up to size top scores.
     *        size should be bigger than 0.
     */
    public HighScoresTable(int size) {
        this.size = size;
        table = new ScoreInfo[size];  // all elements are null now
    }

    /**
     * Add a high-score.
     *
     * @param score the score to add
     */
    public void add(ScoreInfo score) {

        int addIndex = getRank(score.getScore());
        if (addIndex > size) {
            return;
        }

        addIndex -= 1;  // adjust to array

        ScoreInfo temp = score;

        for (int i = addIndex; i < size; i++) {
            ScoreInfo scoreI = this.table[i];

            if (scoreI == null) {
                this.table[i] = temp;
                return;
            }

            this.table[i] = temp;
            temp = scoreI;
        }
    }

    /**
     * Return table size.
     *
     * @return table size
     */
    public int size() {
        return this.size;
    }

    /**
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        List<ScoreInfo> list = new ArrayList<ScoreInfo>();

        for (int i = 0; i < this.size; i++) {
            if (this.table[i] == null) {
                return list;
            }
            list.add(this.table[i]);
        }
        return list;
    }

    /**
     * the function checks the rank of the current score
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     *               be added to the list.
     *
     * @param score the score to check
     * @return the rank of the current score: where will it
     *         be on the list if added?
     */
    public int getRank(int score) {
        for (int i = 0; i < this.size; i++) {

            if (this.table[i] == null) {
                return (i + 1);
            }

            if (score > this.table[i].getScore()) {
                return (i + 1);
            }
        }
        return size + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.table[i] = null;
        }
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename the file name
     * @throws IOException throws if not find or open the File
     */
    public void load(File filename) throws IOException {

        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream inputObjectFile = new ObjectInputStream(file);
        try {
            HighScoresTable obj = (HighScoresTable) inputObjectFile.readObject();

            this.size = obj.size;
            this.table = obj.table;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputObjectFile.close();
            file.close();
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the file name
     * @throws IOException throws if not find or open the File
     */
    public void save(File filename) throws IOException {

        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream outputObjectFile = new ObjectOutputStream(file);

        try {
            outputObjectFile.writeObject(this);

        } finally {

            outputObjectFile.close();
            file.close();
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the file name
     * @return new table from the file
     */
    public static HighScoresTable loadFromFile(File filename) {

        HighScoresTable table = new HighScoresTable(HighScoresTable.DEFULT_SIZE);

        try {
            table.load(filename);
        } catch (IOException e) {
            return new HighScoresTable(HighScoresTable.DEFULT_SIZE);
        }

        return table;
    }
}
