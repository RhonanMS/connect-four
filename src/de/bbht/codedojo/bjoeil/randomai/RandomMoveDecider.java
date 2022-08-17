package de.bbht.codedojo.bjoeil.randomai;

import de.bbht.codedojo.bjoeil.Board;
import de.bbht.codedojo.bjoeil.MoveDecider;
import de.bbht.codedojo.bjoeil.TokenType;

import java.util.Random;

public class RandomMoveDecider implements MoveDecider {
    private static final Random random = new Random();
    @Override
    public int getNextMove(Board board, TokenType player) {
        return 1+random.nextInt(Board.MAX_COLS-1);
    }
}
