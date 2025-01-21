/**
 * Represents a 'clever' player type.
 * an automatic player that has at least 55% to wim 'whatever' type of player.
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class CleverPlayer implements Player {

    /**
     * Constructor. similar to default but public.
     */
    public CleverPlayer() {
    }

    /**
     * Puts the player mark on the board according to 'clever' strategy (as explained in 'README')
     * @param board - the given game board
     * @param mark - the player mark for the current game
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int placement = 0;
        while (!board.putMark(mark, placement / 10, placement % 10)) {
            placement++;
        }
    }
}
