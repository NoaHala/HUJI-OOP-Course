/**
 * a factory class for creating different players that can play the game
 */
public class PlayerFactory {

    /**
     * Constructor. similar to default but public.
     */
    public PlayerFactory() {
    }

    /**
     * the main factory function.
     * creates the relevant type of player according to the given input
     * @param type - string with a name of a player type
     * @return a player object as requested or null if there is a problem
     */
    public Player buildPlayer(String type) {
        return switch (type.toLowerCase()) {
            case ("human") -> new HumanPlayer();
            case ("clever") -> new CleverPlayer();
            case ("genius") -> new GeniusPlayer();
            case ("whatever") -> new WhateverPlayer();
            default -> null;
        };
    }
}
