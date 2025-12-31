package com.othello.backend.engine;

/**
 * Manages the Othello board state, coordinate validation, and piece flipping logic.
 */
public class OthelloBoard {

    public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
    public static final int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
    private int dim;
    private char[][] board;

    /**
     * Initializes an empty board of size dim and places starting tokens.
     */
    public OthelloBoard(int dim) {
        this.dim = dim;
        this.board = new char[dim][dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) board[r][c] = EMPTY;
        }
        int mid = dim / 2;
        board[mid - 1][mid - 1] = board[mid][mid] = P1;
        board[mid][mid - 1] = board[mid - 1][mid] = P2;
    }

    /**
     * Create a new clone of an Othelloboard.
     * @param other
     */
    public OthelloBoard(OthelloBoard other) {
        this.dim = other.dim;
        this.board = new char[dim][dim];

        for (int r = 0; r < dim; r++) {
            System.arraycopy(other.board[r], 0, this.board[r], 0, dim);
        }
    }

    public int getDimension() { return dim; }

    /**
     * Returns the opponent of the given player.
     */
    public static char otherPlayer(char player) {
        return (player == P1) ? P2 : (player == P2) ? P1 : EMPTY;
    }

    /**
     * Returns the token at (row, col) or EMPTY if invalid.
     */
    public char get(int row, int col) {
        return validCoordinate(row, col) ? board[row][col] : EMPTY;
    }

    /**
     * Manually sets a token at (row, col). Returns true if successful.
     */
    public boolean set(int row, int col, char player) {
        if (!validCoordinate(row, col)) return false;
        board[row][col] = player;
        return true;
    }

    /**
     * Validates if a coordinate is within board boundaries.
     */
    private boolean validCoordinate(int row, int col) {
        return row >= 0 && row < dim && col >= 0 && col < dim;
    }

    /**
     * Identifies the player character that ends a sequence of opponent pieces
     * in a specific direction, starting from (row, col).
     */
    private char alternation(int row, int col, int drow, int dcol) {
        if (!validCoordinate(row, col) || (drow == 0 && dcol == 0)) return EMPTY;
        char startPlayer = get(row, col);
        if (startPlayer == EMPTY) return EMPTY;

        char opponent = otherPlayer(startPlayer);
        int r = row + drow, c = col + dcol;
        while (validCoordinate(r, c)) {
            char current = board[r][c];
            if (current == EMPTY) return EMPTY;
            if (current == opponent) return opponent;
            r += drow; c += dcol;
        }
        return EMPTY;
    }

    /**
     * Flips opponent tokens to the specified player's color along a direction.
     * @return Number of flips made, or -1 if the move is invalid.
     */
    private int flip(int row, int col, int drow, int dcol, char player) {
        if (get(row, col) == player || get(row, col) == EMPTY) return -1;
        if (alternation(row, col, drow, dcol) != player) return -1;

        int count = 0;
        while (validCoordinate(row, col) && board[row][col] != player) {
            board[row][col] = player;
            row += drow; col += dcol;
            count++;
        }
        return count;
    }

    /**
     * Checks if placing a piece at (row, col) results in a valid move in a specific direction.
     */
    private char hasMove(int row, int col, int drow, int dcol) {
        return (get(row, col) == EMPTY) ? alternation(row + drow, col + dcol, drow, dcol) : EMPTY;
    }

    /**
     * Returns which players (P1, P2, BOTH, or EMPTY) have a valid move remaining.
     */
    public char hasMove() {
        boolean p1 = false, p2 = false;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                for (int[] d : directions) {
                    char res = hasMove(r, c, d[0], d[1]);
                    if (res == P1) p1 = true;
                    if (res == P2) p2 = true;
                }
            }
        }
        return (p1 && p2) ? BOTH : p1 ? P1 : p2 ? P2 : EMPTY;
    }

    /**
     * Checks if (row, col) is a valid move for player.
     */
    public boolean isValidMove(char player, Move move) {
        int row = move.getRow();
        int col = move.getCol();

        if (get(row, col) != EMPTY) return false;
        for (int[] d : directions) {
            if (hasMove(row, col, d[0], d[1]) == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executes a move for a player at (row, col) if valid, flipping all relevant pieces.
     * @return true if the move was successful.
     */

    public boolean move(int row, int col, char player) {
        return move(player, new Move(row, col));
    }

    public boolean move(char player, Move move) {
        int row = move.getRow();
        int col = move.getCol();
        if (get(row, col) != EMPTY) return false;
        boolean moved = false;
        for (int[] d : directions) {
            if (hasMove(row, col, d[0], d[1]) == player) {
                flip(row + d[0], col + d[1], d[0], d[1], player);
                moved = true;
            }
        }
        if (moved) board[row][col] = player;
        return moved;
    }

    /**
     * Returns the total token count for a specific player.
     */
    public int getCount(char player) {
        int count = 0;
        for (char[] row : board) {
            for (char cell : row) if (cell == player) count++;
        }
        return count;
    }

    /**
     * Returns a string representation of the current board state.
     */
    public String toString() {
        StringBuilder s = new StringBuilder("  ");
        for (int i = 0; i < dim; i++) s.append(i).append(" ");
        s.append("\n +");
        for (int i = 0; i < dim; i++) s.append("-+");
        s.append("\n");

        for (int r = 0; r < dim; r++) {
            s.append(r).append("|");
            for (int c = 0; c < dim; c++) s.append(board[r][c]).append("|");
            s.append(r).append("\n +");
            for (int i = 0; i < dim; i++) s.append("-+");
            s.append("\n");
        }
        s.append("  ");
        for (int i = 0; i < dim; i++) s.append(i).append(" ");
        return s.append("\n").toString();
    }

    public static void main(String[] args) {
        // Standard test logic (kept exactly as requested)
        OthelloBoard ob = new OthelloBoard(8);
        System.out.println(ob.toString());
        System.out.println("getCount(P1)=" + ob.getCount(P1));
        System.out.println("getCount(P2)=" + ob.getCount(P2));
        for (int row = 0; row < ob.dim; row++) {
            for (int col = 0; col < ob.dim; col++) {
                ob.board[row][col] = P1;
            }
        }
        System.out.println(ob.toString());
        System.out.println("getCount(P1)=" + ob.getCount(P1));
        System.out.println("getCount(P2)=" + ob.getCount(P2));

        for (int drow = -1; drow <= 1; drow++) {
            for (int dcol = -1; dcol <= 1; dcol++) {
                System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
            }
        }

        for (int row = 0; row < ob.dim; row++) {
            for (int col = 0; col < ob.dim; col++) {
                if (row == 0 || col == 0) ob.board[row][col] = P2;
            }
        }
        System.out.println(ob.toString());

        for (int drow = -1; drow <= 1; drow++) {
            for (int dcol = -1; dcol <= 1; dcol++) {
                System.out.println("direction=(" + drow + "," + dcol + ")");
                System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
            }
        }

        System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
        ob.board[4][4] = EMPTY;
        ob.board[2][4] = EMPTY;
        System.out.println(ob.toString());

        for (int drow = -1; drow <= 1; drow++) {
            for (int dcol = -1; dcol <= 1; dcol++) {
                System.out.println("direction=(" + drow + "," + dcol + ")");
                System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
            }
        }
        System.out.println("who has a move=" + ob.hasMove());
        System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
        System.out.println(ob.toString());
    }
}