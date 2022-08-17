package de.bbht.codedojo.bjoeil.bitboardai;

import de.bbht.codedojo.bjoeil.Board;

import java.util.List;

public class Position {

    private int moves;
    private long mask;
    private long currentPosition;

    public Position() {
        moves = 0;
        mask = 0L;
        currentPosition = 0L;
    }

    public static Position from(Position p) {
        Position result = new Position();
        result.moves = p.moves;
        result.mask = p.mask;
        result.currentPosition = p.currentPosition;
        return result;
    }

    static boolean alignment(long pos) {
        // horizontal
        long m = pos & (pos >> (Board.MAX_ROWS + 1));
        if ((m & (m >> (2 * (Board.MAX_ROWS + 1)))) != 0) return true;

        // diagonal 1
        m = pos & (pos >> Board.MAX_ROWS);
        if ((m & (m >> (2 * Board.MAX_ROWS))) != 0) return true;

        // diagonal 2
        m = pos & (pos >> (Board.MAX_ROWS + 2));
        if ((m & (m >> (2 * (Board.MAX_ROWS + 2)))) != 0) return true;

        // vertical;
        m = pos & (pos >> 1);
        if ((m & (m >> 2)) != 0) return true;

        return false;
    }

    // return a bitmask containg a single 1 corresponding to the top cel of a given column
    private static long topMask(int col) {
        return ((1L) << (Board.MAX_ROWS - 1)) << col * (Board.MAX_ROWS + 1);
    }

    // return a bitmask containg a single 1 corresponding to the bottom cell of a given column
    private static long bottomMask(int col) {
        return 1L << col * (Board.MAX_ROWS + 1);
    }

    // return a bitmask 1 on all the cells of a given column
    private static long columnMask(int col) {
        return ((1L << Board.MAX_ROWS) - 1) << col * (Board.MAX_ROWS + 1);
    }

    public int nbMoves() {
        return moves;
    }

    long key() {
        return currentPosition + mask;
    }
    public boolean canPlay(int col) {
         return (mask & topMask(col)) == 0;
    }

    public boolean isWinningMove(int col) {
        long pos = currentPosition;
        pos |= (mask + bottomMask(col)) & columnMask(col);
        return alignment(pos);
    }

    public void play(int col) {
        currentPosition ^= mask;
        mask |= mask + bottomMask(col);
        moves++;
    }

    public int play(List<Integer> sequence) {
        for (int i = 0; i < sequence.size(); i++) {
            int col = sequence.get(i);
            if (col < 0 || col >= Board.MAX_COLS || !canPlay(col) || isWinningMove(col)) {
                return i;
            }
            play(col);
        }
        return sequence.size();
    }
}
