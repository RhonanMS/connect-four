package de.rhonanms.codedojo.connectfour.randomai;

import de.rhonanms.codedojo.connectfour.Board;
import de.rhonanms.codedojo.connectfour.MoveDecider;
import de.rhonanms.codedojo.connectfour.TokenType;

import java.util.Random;

public class RandomMoveDecider implements MoveDecider {
    private static final Random random = new Random();
    @Override
    public int getNextMove(Board board, TokenType player) {
        return 1+random.nextInt(Board.MAX_COLS-1);
    }
}
