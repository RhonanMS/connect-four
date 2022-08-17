package de.bbht.codedojo.bjoeil.bitboardai;

import de.bbht.codedojo.bjoeil.Board;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BitboardSolver {

    private static final int MIN_SCORE = -(Board.MAX_COLS * Board.MAX_ROWS) / 2 + 3;
    private static final int MAX_SCORE = (Board.MAX_COLS * Board.MAX_ROWS + 1) / 2 - 3;

    private long nodeCount = 0;
    private int[] columnOrder = new int[Board.MAX_COLS];

    private Map<Long, Integer> transpositionTable;

    public BitboardSolver() {
        transpositionTable = new MaxMemLinkedHashMap<>(524288); // about 64 MB size.
        for (int i = 0; i < Board.MAX_COLS; i++) {
            columnOrder[i] = Board.MAX_COLS / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
        }
    }

    public int solve(Position p, boolean weak) {
        nodeCount = 0;
        if (weak) {
            return negaMax(p, -1, 1);
        } else {
            return negaMax(p, -(Board.MAX_COLS * Board.MAX_ROWS) / 2, (Board.MAX_COLS * Board.MAX_ROWS) / 2);
        }
    }

    private int negaMax(Position p, int alpha, int beta) {
        assert (alpha < beta);
        nodeCount++;

        if (p.nbMoves() == Board.MAX_COLS * Board.MAX_ROWS) {
            // unentschieden
            return 0;
        }

        for (int col = 0; col < Board.MAX_COLS; col++) { // kann Spieler mit nächstem Zug gewinnen?
            if (p.canPlay(col) && p.isWinningMove(col)) {
                return (Board.MAX_COLS * Board.MAX_ROWS + 1 - p.nbMoves()) / 2;
            }
        }

        int max = (Board.MAX_COLS * Board.MAX_ROWS - 1 - p.nbMoves()) / 2;    // Obergrenze für Score, da wir nicht direkt gewinnen können.
        int val = 0;
        if (transpositionTable.containsKey(p.key())) {
            val = transpositionTable.get(p.key());
        }
        if (val != 0) {
            max = val + MIN_SCORE - 1;
        }
        if (beta > max) {
            beta = max;                     // there is no need to keep beta above our max possible score.
            if (alpha >= beta) return beta;  // prune the exploration if the [alpha;beta] window is empty.
        }

        for (int x = 0; x < Board.MAX_COLS; x++) // compute the score of all possible next move and keep the best one
            if (p.canPlay(columnOrder[x])) {
                Position p2 = Position.from(p);
                p2.play(columnOrder[x]);               // It's opponent turn in P2 position after current player plays x column.
                int score = -negaMax(p2, -beta, -alpha); // explore opponent's score within [-beta;-alpha] windows:
                // no need to have good precision for score better than beta (opponent's score worse than -beta)
                // no need to check for score worse than alpha (opponent's score worse better than -alpha)

                if (score >= beta)
                    return score;  // prune the exploration if we find a possible move better than what we were looking for.
                if (score > alpha) alpha = score; // reduce the [alpha;beta] window for next exploration, as we only
                // need to search for a position that is better than the best so far.
            }

        transpositionTable.put(p.key(), alpha - MIN_SCORE + 1); // save the upper bound of the position
        return alpha;
    }

    public long getNodeCount() {
        return this.nodeCount;
    }
}
