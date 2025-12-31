package com.othello.backend.strategy;

import java.util.ArrayList;
import java.util.Random;

import com.othello.backend.engine.Othello;
import com.othello.backend.engine.Move;
import com.othello.backend.engine.OthelloBoard;
/**
 * The RandomStrategy class represents a strategy that makes random valid moves in Othello.
 * It finds all possible moves and picks one at random.
 */

public class RandomStrategy extends Strategy {
	
	private final Random rand = new Random();

    /**
     * Builds a new random strategy in the given Othello game.
     *
     * @param othello the current Othello game
     * @param player the character representing this player (P1 or P2)
     */

    public RandomStrategy(Othello othello, char player) {
        super(othello, player);
    }

    /**
     * Picks and returns a random valid move for this player.
     * If no valid moves are available, it returns null.
     *
     * @return a random Move for this player, or null if no moves are possible
     */
	public Move getMove() {
        ArrayList<int[]> moves = new ArrayList<>();

        for (int row = 0; row < othello.othelloBoard.getDimension(); row++) {
            for (int col = 0; col < othello.othelloBoard.getDimension(); col++) {
                int tokens = GreedyStrategy.getToken(othello, player, row, col);
                if (tokens > 0) moves.add(new int[]{row, col});
            }
        }
        if (moves.isEmpty()) {
            return null;
        }
        int randInt = rand.nextInt(moves.size());
        int[] cell = moves.get(randInt);
        return new Move(cell[0], cell[1]);
	}
}
