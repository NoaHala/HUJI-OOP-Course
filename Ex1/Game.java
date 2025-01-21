/**
 * Represents the tic-tac-toe game.
 * In charge of creating a single game with given board-size and players.
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class Game {
    private int boardSize, winStreak;
    private Player[] players = new Player[2];
    private Mark[] playerMark = {Mark.X, Mark.O};
    private Renderer renderer;
    private Board board, boardBeforeLastMove;
    private static final int DEFAULT_BOARD_SIZE = 4;
    private static final int DEFAULT_WIN_STREAK = 3;

    /**
     * Constructor.
     *
     * @param playerX - first player with X mark
     * @param playerO - second player with O mark
     * @param size - size of requested board
     * @param winStreak - the length of a sequence for winning
     * @param renderer - the requested render type
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.players[0] = playerX;
        this.players[1] = playerO;
        this.boardSize = size;
        if (winStreak > size || winStreak < 2){
            this.winStreak = size;
        }
        else {
            this.winStreak = winStreak;
        }
        this.renderer = renderer;
        this.board = new Board(size);
        this.boardBeforeLastMove = new Board(size);

    }

    /**
     * Constructor
     * @param playerX - first player with X mark
     * @param playerO - second player with O mark
     * @param renderer - the requested render type
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this(playerX, playerO, DEFAULT_BOARD_SIZE, DEFAULT_WIN_STREAK, renderer);
    }

    /**
     * Get method for the private 'winStreak' field
     * @return this.winStreak
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    /**
     * Get method for the private 'boardSize' field
     * @return this.boardSize
     */
    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * runs a full game round
     * @return the mark of the winner player
     */
    public Mark run() {
        int currentPlayerIndex = 0;
        int[] lastMove;

        for (int turns = 0; turns < this.boardSize * this.boardSize; turns++) {
            // playing a turn
            Player curPlayer = this.players[currentPlayerIndex];
            Mark curPlayerMark = this.playerMark[currentPlayerIndex];
            curPlayer.playTurn(this.board, curPlayerMark);
            lastMove = getLastMove();
            this.renderer.renderBoard(this.board);

            if (playerWon(curPlayerMark, lastMove[0], lastMove[1])) {
                return curPlayerMark;
            }

            // if no one won yet, change player
            currentPlayerIndex = 1 - currentPlayerIndex;
        }
        return Mark.BLANK;
    }

    /*
    helper function for run
     */
    private int[] getLastMove() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                Mark markAtCurBoard = this.board.getMark(row, col);
                if (markAtCurBoard != this.boardBeforeLastMove.getMark(row, col)) {
                    // update the before board
                    this.boardBeforeLastMove.putMark(markAtCurBoard, row, col);
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    /*
    helper function for run
     */
    private boolean playerWon(Mark mark, int row, int col) {
        int[] diagonalFirstCoords = getFirstCoordinateOfDiagonal(row, col, true);
        int[] antiDiagonalFirstCoords = getFirstCoordinateOfDiagonal(row, col, false);

        return isThereSequence(mark, row, 0, 0, 1) ||
                isThereSequence(mark, 0, col, 1, 0) ||
                isThereSequence(mark, diagonalFirstCoords[0], diagonalFirstCoords[1],
                        1, 1) ||
                isThereSequence(mark, antiDiagonalFirstCoords[0], antiDiagonalFirstCoords[1],
                        1, -1);
    }

    /*
    helper function for run
     */
    private boolean isThereSequence(Mark mark, int row, int col, int rowIncrement, int colIncrement) {
        int sequenceCounter = 0;
        while (row >= 0 && row < this.boardSize &&
                col >= 0 && col < this.boardSize &&
                sequenceCounter < this.winStreak) {
            if (this.board.getMark(row, col) == mark) {
                sequenceCounter++;
            } else {
                sequenceCounter = 0;
            }
            row += rowIncrement;
            col += colIncrement;
        }
        return sequenceCounter == this.winStreak;
    }

    /*
    helper function for run
     */
    private int[] getFirstCoordinateOfDiagonal(int row, int col, boolean diagonalDirection) {
        if (diagonalDirection) {
            while (row > 0 && col > 0) {
                row--;
                col--;
            }
        } else {
            while (row > 0 && col < (this.boardSize - 1)) {
                row--;
                col++;
            }
        }
        int[] firstCoordinate = {row, col};
        return firstCoordinate;
    }
}
