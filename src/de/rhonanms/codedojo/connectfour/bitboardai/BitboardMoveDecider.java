package de.rhonanms.codedojo.connectfour.bitboardai;

import de.rhonanms.codedojo.connectfour.MoveDecider;
import de.rhonanms.codedojo.connectfour.Board;
import de.rhonanms.codedojo.connectfour.TokenType;

public class BitboardMoveDecider implements MoveDecider {
    @Override
    public int getNextMove(Board board, TokenType player) {
        BitboardSolver solver = new BitboardSolver();
        Position initialPosition = new Position();
        initialPosition.play(board.getMoves());
        int maxScore = Integer.MIN_VALUE;
        int score = Integer.MIN_VALUE;
        int bestColumn = -1;
        for (int i = 0; i < 7; i++) {
            long start = System.nanoTime();
            System.out.println("Testing column " + (i+1));
            Position test = Position.from(initialPosition);
            if (test.canPlay(i)) {
                test.play(i);
                score = -solver.solve(test, true); // negativer Score, da wir für jeden simulierten Zug von uns
                                                         // schauen müssen, wie die Reaktion des Gegners darauf aussieht
                System.out.println("Score is "+score);
                if (score > maxScore) {
                    maxScore = score;
                    bestColumn = i;
                    System.out.println("Best score is "+ maxScore + ", best column is " + bestColumn);
                }
            }
            long end = System.nanoTime();
            long durationMs = (end-start)/1000000L;
            System.out.println("Target column is " + bestColumn + ", took "+durationMs+" ms");
        }
        return bestColumn+1;
    }
}
