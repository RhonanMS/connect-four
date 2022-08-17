package de.rhonanms.codedojo.connectfour;

public interface MoveDecider {

    int getNextMove(Board board, TokenType player);
}
