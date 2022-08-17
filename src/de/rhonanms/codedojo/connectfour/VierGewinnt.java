package de.rhonanms.codedojo.connectfour;

import de.rhonanms.codedojo.connectfour.bitboardai.BitboardMoveDecider;
import de.rhonanms.codedojo.connectfour.randomai.RandomMoveDecider;

import java.util.InputMismatchException;
import java.util.Scanner;

public class VierGewinnt {

    private Board board;
    private MoveDecider player1;
    private MoveDecider player2;


    public VierGewinnt() {
        this.board = new Board();
        this.player1 = new  BitboardMoveDecider();// RandomMoveDecider(); // // PlayerInputBot();
        this.player2 = new BitboardMoveDecider(); // RandomMoveDecider();
    }

    public void gameLoop() {
        TokenType currentPlayer = TokenType.PLAYER_1;
        boolean hasWon = false;
        do {
            board.printBoard();
            MoveDecider playerBotNow;
            if (currentPlayer == TokenType.PLAYER_1 || player2 == null) {
                playerBotNow = player1;
            } else {
                playerBotNow = player2;
            }
            boolean success = false;
            do {
                int column = playerBotNow.getNextMove(board, currentPlayer);
                success = board.play(currentPlayer, column);
                if (success) {
                    System.out.println(String.format("Player %s added token at column %s", currentPlayer, column));
                } else {
                    System.out.println(String.format("Invalid move %s for player %s", column, currentPlayer));
                }
            } while (!success);

            if (board.wins(currentPlayer)) {
                hasWon = true;
            } else {
                if (currentPlayer == TokenType.PLAYER_1) {
                    currentPlayer = TokenType.PLAYER_2;
                } else {
                    currentPlayer = TokenType.PLAYER_1;
                }
            }
        } while (!board.boardFull() && !hasWon);
        board.printBoard();
        if (hasWon) {
            System.out.println(String.format("Player %s has won!", currentPlayer));
        } else {
            System.out.println("The board is full - it's a draw!");
        }
    }

    private void readPlayerInput(TokenType player) {
        boolean inputDone = false;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print(String.format("Player %s, your move! Add token to which column? (1 - %s) => ", player, Board.MAX_COLS));
            int column = -1;
            try {
                column = in.nextInt();
            } catch (InputMismatchException e) {
                in.reset();
                in = new Scanner(System.in);
                column = -1;
            }
            if (column == -1) {
                System.out.println("Invalid input, please try again...");
            } else {
                inputDone = board.play(player, column);
            }
        } while (!inputDone);
    }

    public static void main(String[] args) {
        new VierGewinnt().gameLoop();
    }
}
