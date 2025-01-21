/**
 * Represents a human player type.
 * Allowing a human that executes the game to play by writing inputs in the terminal.
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class HumanPlayer implements Player{

    /**
     * Constructor. similar to default but public
     */
    public HumanPlayer(){}

    /**
     * Puts the player mark on the board according to the user choices
     * @param board - the given game board
     * @param mark - the player mark for the current game
     */
    @Override
    public void playTurn(Board board, Mark mark){
        int input = playerInputParser(board,mark);
        int row = input/10;
        int col = input%10;

        board.putMark(mark, row, col);
    }

    /*
      helper function for 'playTurn'. manages the interaction with the user until it archives valid values.
     */
    private int playerInputParser(Board board, Mark mark){
        int input, row, col;
        System.out.printf(Constants.playerRequestInputString(String.valueOf(mark)));
        while (true){
            input = KeyboardInput.readInt();
            row = input/10;
            col = input%10;
            // checks if the coordinates aren't in the board range
            if (row < 0 || row >= board.getSize() ||
            col < 0 || col >= board.getSize()){
                System.out.printf(Constants.INVALID_COORDINATE);
                continue;
            }
            // checks if the coordinate is occupied
            if(board.getMark(row,col) != Mark.BLANK){
                System.out.printf(Constants.OCCUPIED_COORDINATE);
                continue;
            }
            break;
        }
        return input;
    }
}
