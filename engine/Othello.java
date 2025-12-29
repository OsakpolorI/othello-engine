package ca.utoronto.utm.assignment1.othello;

import org.w3c.dom.DOMImplementation;

import javax.print.attribute.standard.MediaSize;
import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
    public OthelloBoard othelloBoard;

    public Othello() {
        othelloBoard = new OthelloBoard(DIMENSION);
    }

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() { return whosTurn;}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
        boolean result = othelloBoard.move(row, col, getWhosTurn());
        if (result) {
            numMoves += 1;
            whosTurn = OthelloBoard.otherPlayer(getWhosTurn());
        }
        return result;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return othelloBoard.getCount(player);
	}

    /**
     * Returns the total number of moves played in this game.
     * @return the number of moves played.
     */
    public int getNumMoves() {
        return numMoves;
    }

    /**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
        if (!isGameOver()) return OthelloBoard.EMPTY;
        int player1Count = getCount(OthelloBoard.P1);
        int player2Count = getCount(OthelloBoard.P2);
        if (player1Count < player2Count) return OthelloBoard.P2;
        if (player1Count > player2Count) return OthelloBoard.P1;
        return OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
        return othelloBoard.hasMove() == OthelloBoard.EMPTY;
    }

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return othelloBoard.toString();
	}

    public void switchTurn() {
        whosTurn = OthelloBoard.otherPlayer(getWhosTurn());
    }

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}
}