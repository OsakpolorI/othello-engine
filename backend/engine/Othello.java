import java.util.Random;

/**
 * Manages an Othello game session, tracking turns, move counts,
 * and identifying the winner.
 */
public class Othello {
    public static final int DIMENSION = 8;
    private char whosTurn = OthelloBoard.P1;
    private int numMoves = 0;
    public OthelloBoard othelloBoard;

    public Othello() {
        othelloBoard = new OthelloBoard(DIMENSION);
    }

    public char getWhosTurn() { return whosTurn; }

    /**
     * Makes a move for the current player. Updates turn and move count on success.
     * Includes logic to handle the "Pass" rule (skipping a player with no moves).
     */
    public boolean move(int row, int col) {
        if (othelloBoard.move(row, col, whosTurn)) {
            numMoves++;

            // Determine who can move next
            char opponent = OthelloBoard.otherPlayer(whosTurn);
            char canMove = othelloBoard.hasMove();

            // Othello Turn Logic:
            // 1. If the opponent can move (BOTH or just Opponent), it's their turn.
            // 2. If the opponent CANNOT move but the current player CAN, skip the opponent.
            // 3. If neither can move, the game is over.
            if (canMove == OthelloBoard.BOTH || canMove == opponent) {
                whosTurn = opponent;
            } else if (canMove == whosTurn) {
                // Opponent skipped: whosTurn stays as is.
                System.out.println(opponent + " has no moves and is skipped!");
            } else {
                // No moves left for anyone
                whosTurn = OthelloBoard.EMPTY;
            }
            return true;
        }
        return false;
    }

    public int getCount(char player) {
        return othelloBoard.getCount(player);
    }

    public int getNumMoves() {
        return numMoves;
    }

    /**
     * Returns P1 or P2 if the game is over, otherwise EMPTY.
     */
    public char getWinner() {
        if (!isGameOver()) return OthelloBoard.EMPTY;
        int p1 = getCount(OthelloBoard.P1);
        int p2 = getCount(OthelloBoard.P2);
        // Returns P1, P2, or EMPTY in case of a tie
        return (p1 > p2) ? OthelloBoard.P1 : (p2 > p1) ? OthelloBoard.P2 : OthelloBoard.EMPTY;
    }

    public boolean isGameOver() {
        return othelloBoard.hasMove() == OthelloBoard.EMPTY;
    }

    public String getBoardString() {
        return othelloBoard.toString();
    }

    public OthelloBoard getBoard() {
        return othelloBoard;
    }

    public void switchTurn() {
        whosTurn = OthelloBoard.otherPlayer(whosTurn);
    }

    /**
     * run this to test the current class. We play a completely random game.
     */
    public static void main(String[] args) {
        /*
        Random rand = new Random();
        Othello o = new Othello();

        System.out.println(o.getBoardString());
        while (!o.isGameOver()) {
            // Using DIMENSION instead of hardcoded 8 for robustness
            int row = rand.nextInt(DIMENSION);
            int col = rand.nextInt(DIMENSION);

            if (o.move(row, col)) {
                System.out.println("makes move (" + row + "," + col + ")");
                System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
            }
        }

        System.out.println("Game Over! Winner is: " + o.getWinner());
        System.out.println("Final Score - P1: " + o.getCount(OthelloBoard.P1) +
                " | P2: " + o.getCount(OthelloBoard.P2));
     */
    }

    // Make new game
    Othello o = new Othello();

    // Make a move



}