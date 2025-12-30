import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TODO: Document this class and make minimal changes as necessary.
 * 
 * @author arnold
 *
 */

/**
 * The PlayerHuman class represents a human player in the Othello game.
 * It lets the player enter their move by typing a row and column.
 */
public class PlayerHuman extends Player{
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Builds a new human player in the given Othello game.
     *
     * @param othello the current Othello game
     * @param player the character representing this player (P1 or P2)
     */
	public PlayerHuman(Othello othello, char player) {
		super(othello, player);
	}

    /**
     * Gets the move from the player by asking for a row and column input.
     *
     * @return the Move chosen by the player
     */
	public Move getMove() {
		
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}

    /**
     * Asks the player for a row or column number.
     * Keeps asking until a valid number between 0 and 7 is entered.
     *
     * @param message the prompt shown to the player
     * @return the number entered, or -1 if an input error happens
     */
	private int getMove(String message) {
		
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
