/**
 * Represents a 'genius' player type.
 * an automatic player that has at least 55% to wim 'clever' type of player.
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class GeniusPlayer implements Player {

    /**
     * Constructor. similar to default but public.
     */
    public GeniusPlayer() {
    }

    /**
     * Puts the player mark on the board according to 'genius' strategy (as explained in 'README')
     * @param board - the given game board
     * @param mark - the player mark for the current game
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int row = 0, col = 1;
        while (!board.putMark(mark, row, col)) {
            if (row == board.getSize() - 1) {
                col = (col + 1) % board.getSize();
            }
            row = (row + 1) % board.getSize();
        }
    }
}
