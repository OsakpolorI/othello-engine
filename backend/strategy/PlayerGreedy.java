/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 *
 *
 */

/**
 * The PlayerGreedy class represents a player that always chooses
 * the move that flips the most opponent tokens.
 * It checks all possible moves and picks the one with the highest gain.
 */

public class PlayerGreedy extends Player {

    /**
     * Creates a new greedy player.
     *
     * @param othello the Othello game the player is part of
     * @param player the character representing the player (P1 or P2)
     */

    public PlayerGreedy(Othello othello, char player) {
        super(othello, player);
    }

    /**
     * Finds and returns the best move for this player.
     * The best move is the one that flips the most opponent tokens.
     * If there are no possible moves, it returns null.
     *
     * @return the best Move for the player, or null if none are available
     */

	public Move getMove() {
        int maxTokens = 0;
        for (int row = 0; row < othello.othelloBoard.getDimension(); row++) {
           for (int col = 0; col < othello.othelloBoard.getDimension(); col++) {
                int tokens = getToken(othello, player, row, col);
                if (maxTokens < tokens) maxTokens = tokens;
           }
        }
        if  (maxTokens == 0) return null;
        for (int row = 0; row < othello.othelloBoard.getDimension(); row++) {
            for (int col = 0; col < othello.othelloBoard.getDimension(); col++) {
                if (getToken(othello, player, row, col) != maxTokens) continue;
                return new Move(row, col);
            }
        }
        return null;
	}

    /**
     * Counts how many tokens would be flipped if the player
     * placed a token at the given position.
     *
     * @param othello the Othello game being played
     * @param player the character representing the current player
     * @param row the row position being checked
     * @param col the column position being checked
     * @return the number of opponent tokens that would be flipped
     */

    public static int getToken(Othello othello, char player, int row, int col) {
        int maxTokens = 0;
        if (othello.othelloBoard.get(row, col) != OthelloBoard.EMPTY) return maxTokens;
        for (int[] dir : OthelloBoard.directions) {
            int dirTokens = 0;
            char first = othello.othelloBoard.get(row + dir[0], col + dir[1]);
            if (first != OthelloBoard.otherPlayer(player)) continue;
            int dx = dir[0]; int dy = dir[1];
            while (first == OthelloBoard.otherPlayer(player)){
                dirTokens++;
                dx += dir[0];
                dy += dir[1];
                first = othello.othelloBoard.get(row + dx, col + dy);
            }
         if (first == OthelloBoard.EMPTY) continue;
         if (maxTokens < dirTokens) maxTokens = dirTokens;
        }
        return maxTokens;
    }
}
