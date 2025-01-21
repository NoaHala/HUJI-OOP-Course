/**
 * Represents the tic-tac-toe board.
 * In charge of tracking the board status and making any
 * changes in ist marks
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class Board {
    private static final int DEFAULT_BOARD_SIZE = 4;
    private int size;
    private Mark[][] boardMarksArray;

    /**
     * Constructor.
     *
     * @param size - the size of each row/column in the board
     */
    public Board(int size) {
        this.size = size;
        this.boardMarksArray = new Mark[size][size];
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.boardMarksArray[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * Default constructor.
     * Creating a board with default size 4*4
     */
    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    /**
     * Get method for the private 'size' field
     *
     * @return this.size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Checks if the given coordinate is in the board range and not
     * occupied, and if so, puts the given mark in the board array in
     * that coordinate.
     *
     * @param mark - the player's game mark ('X' or 'O')
     * @param row  - the given row coordinate
     * @param col  - the given column coordinate
     * @return true if succeeded or false otherwise
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (row >= 0 && row < this.size &&
                col >= 0 && col < this.size &&
                this.boardMarksArray[row][col] == Mark.BLANK) {
            this.boardMarksArray[row][col] = mark;
            return true;
        }
        return false;
    }

    /**
     * Get method for the Marks at a certain coordination in
     * the private field 'boardMarksArray'
     *
     * @return this.boardMarksArray
     */
    public Mark getMark(int row, int col) {
        if (row >= 0 && row < this.size && col >= 0 && col < this.size){
            return this.boardMarksArray[row][col];
        }
        return Mark.BLANK;
    }

}
