package de.bbht.codedojo.bjoeil.player;

import de.bbht.codedojo.bjoeil.MoveDecider;
import de.bbht.codedojo.bjoeil.Board;
import de.bbht.codedojo.bjoeil.TokenType;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayerMoveDecider implements MoveDecider {
    @Override
    public int getNextMove(Board board, TokenType player) {
        boolean inputDone = false;
        Scanner in = new Scanner(System.in);
        int column = -1;

        do {
            System.out.print(String.format("Player %s, your move! Add token to which column? (1 - %s) => ", player, Board.MAX_COLS));
            column = -1;

            try {
                column = in.nextInt();
            } catch (InputMismatchException e) {
                in.next();
                column = -1;
            }
            if (column == -1) {
                System.out.println("Invalid input, please try again...");
            } else if (column <= 0 || column > Board.MAX_COLS) {
                System.out.println("Please enter valid column number...");
            } else {
                inputDone = true;
            }
        } while (!inputDone);

        return column;
    }
}
