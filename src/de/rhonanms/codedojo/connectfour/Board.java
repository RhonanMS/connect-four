package de.rhonanms.codedojo.connectfour;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private enum LineType {
        TOP('\u250c', '\u252c', '\u2510'),
        MIDDLE('\u251c', '\u253c', '\u2524'),
        BOTTOM('\u2514', '\u2534', '\u2518');

        private final char firstChar;
        private final char middleChar;
        private final char lastChar;

        private LineType(char firstChar, char middleChar, char lastChar) {
            this.firstChar = firstChar;
            this.middleChar = middleChar;
            this.lastChar = lastChar;
        }

        public char getFirstChar() {
            return firstChar;
        }

        public char getMiddleChar() {
            return middleChar;
        }

        public char getLastChar() {
            return lastChar;
        }
    }

    // 5 rows, 7 cols
    public static final int MAX_ROWS = 5;
    public static final int MAX_COLS = 7;
    private static final int WIN_TOKENS = 4;
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private char[][] board;
    private List<Integer> moves = new ArrayList<>();

    public Board() {
        this.board = new char[MAX_ROWS][MAX_COLS];
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLS; col++) {
                board[row][col] = TokenType.UNSET.getRepresentation();
            }
        }
    }

    public boolean play(TokenType tokenType, int col) {
        if (col >= 1 && col <= MAX_COLS) {
            for (int row = MAX_ROWS - 1; row >= 0; row--) {
                if (board[row][col - 1] == TokenType.UNSET.getRepresentation()) {
                    board[row][col - 1] = tokenType.getRepresentation();
                    moves.add(col-1);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> getMoves() {
        return moves;
    }

    public void printBoard() {
        drawLine(LineType.TOP);
        // for (char[] row : board) {
        for (int y = 0; y < MAX_ROWS; y++) {
            char[] row = board[y];
            System.out.print("\u2502");
            boolean first = true;
            for (char col : row) {
                if (first) {
                    first = false;
                } else {
                    System.out.print("\u2502");
                }
                if (col == TokenType.PLAYER_2.getRepresentation()) {
                    System.out.print(ANSI_YELLOW);
                } else if (col == TokenType.PLAYER_1.getRepresentation()) {
                    System.out.print(ANSI_RED);
                }
                System.out.print(" " + ((char) col) + " ");
                System.out.print(ANSI_RESET);
            }
            System.out.println("\u2502");
            if (y == MAX_ROWS - 1) {

                drawLine(LineType.BOTTOM);
            } else {
                drawLine(LineType.MIDDLE);
            }
        }
        System.out.println();
    }

    private void drawLine(LineType lineType) {
        StringBuilder line = new StringBuilder();
        line.append(lineType.getFirstChar());
        for (int i = 0; i < MAX_COLS; i++) {
            line.append("\u2500\u2500\u2500");
            if (i == MAX_COLS - 1) {
                line.append(lineType.getLastChar());
            } else {
                line.append(lineType.getMiddleChar());
            }
        }
        System.out.println(line.toString());
    }

    public boolean wins(TokenType tokenType) {
        char token = tokenType.getRepresentation();

        // horizontal prüfen
        for (int x = 0; x < MAX_COLS - WIN_TOKENS + 1; x++) {
            for (int y = 0; y < MAX_ROWS; y++) {
                boolean isWin = true;
                for (int c = 0; c < WIN_TOKENS; c++) {
                    isWin = isWin && (board[y][x + c] == token);
                }
                if (isWin) return true;
            }
        }

        // vertikal prüfen
        for (int y = 0; y < MAX_ROWS - WIN_TOKENS + 1; y++) {
            for (int x = 0; x < MAX_COLS; x++) {
                boolean isWin = true;
                for (int c = 0; c < WIN_TOKENS; c++) {
                    isWin = isWin && (board[y + c][x] == token);
                }
                if (isWin) return true;
            }
        }

        // diagonal von oben links nach unten rechts prüfen
        for (int x = 0; x < MAX_COLS - WIN_TOKENS + 1; x++) {
            for (int y = 0; y < MAX_ROWS - WIN_TOKENS + 1; y++) {
                boolean isWin = true;
                for (int c = 0; c < WIN_TOKENS; c++) {
                    isWin = isWin && (board[y + c][x + c] == token);
                }
                if (isWin) return true;
            }
        }

        // diagonal von unten links nach oben rechts prüfen
        for (int x = WIN_TOKENS - 1; x < MAX_COLS; x++) {
            for (int y = 0; y < MAX_ROWS - WIN_TOKENS + 1; y++) {
                boolean isWin = true;
                for (int c = 0; c < WIN_TOKENS; c++) {
                    isWin = isWin && (board[y + c][x - c] == token);
                }
                if (isWin) return true;
            }
        }

        return false;
    }

    public boolean boardFull() {
        boolean result = true;
        for (int x = 0; x < MAX_COLS; x++) {
            for (int y = 0; y < MAX_ROWS; y++) {
                if (board[y][x] == TokenType.UNSET.getRepresentation()) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
