import java.util.Random;

/**
 * Represents a 'whatever' player type.
 * an automatic player that puts its mark in random available location each turn.
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class WhateverPlayer implements Player {
    private Random rand = new Random();

    /**
     * Constructor. similar to default but public.
     */
    public WhateverPlayer() {
    }

    /**
     * Puts the player mark in a random coordinate on the game board
     * @param board - the given game board
     * @param mark - the player mark for the current game
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        ;
        int randomRow, randomCol;
        do {
            randomRow = rand.nextInt(board.getSize());
            randomCol = rand.nextInt(board.getSize());
        }
        while (!board.putMark(mark, randomRow, randomCol));
    }
}
