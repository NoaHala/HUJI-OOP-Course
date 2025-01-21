/**
 * Represents the tic-tac-toe Tournament.
 * In charge of creating a tournament with given number of game rounds
 *
 * @author Noa Halaly (according to the HUJI OOP course requirments)
 */
public class Tournament {
    private int rounds;
    private Renderer renderer;
    private Player[] players = new Player[2];

    /**
     * Constructor
     *
     * @param rounds - number of game rounds
     * @param renderer - the requested render type
     * @param player1 - first player type
     * @param player2 - second player type
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players[0] = player1;
        this.players[1] = player2;
    }

    /**
     * runs a full tournament with game rounds as given
     *
     * @param size - the board size
     * @param winStreak - e length of a sequence for winning
     * @param playerName1 - first player's name
     * @param playerName2 - second player's name
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        int[] playersWinCount = {0, 0};
        int tiesCount = 0;

        for (int rounds = 0; rounds < this.rounds; rounds++) {
            Game game = new Game(this.players[rounds % 2], this.players[(rounds + 1) % 2],
                    size, winStreak, this.renderer);
            Mark winnerMark = game.run();

            if (winnerMark == Mark.X) {
                playersWinCount[rounds % 2]++;
            } else if (winnerMark == Mark.O) {
                playersWinCount[(rounds + 1) % 2]++;
            } else {
                tiesCount++;
            }
        }

        System.out.printf("######### Results #########\n" +
                "Player 1, %s won: %d rounds\n" +
                "Player 2, %s won: %d rounds\n" +
                "Ties: %d", playerName1, playersWinCount[0], playerName2, playersWinCount[1], tiesCount);
    }

    /**
     * the main function that run the entire project
     *
     * @param args - the args given by the user
     */
    public static void main(String[] args) {
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Math.min(Integer.parseInt(args[2]), size);
        String rendererName = args[3];
        String playerName1 = args[4].toLowerCase();
        String playerName2 = args[5].toLowerCase();

        Renderer renderer = new RendererFactory().buildRenderer(rendererName, size);
        if (renderer == null) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        }

        PlayerFactory playerFactory = new PlayerFactory();
        Player player1 = playerFactory.buildPlayer(playerName1);
        Player player2 = playerFactory.buildPlayer(playerName2);
        if (player1 == null || player2 == null) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        }

        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, playerName1, playerName2);
    }
}
