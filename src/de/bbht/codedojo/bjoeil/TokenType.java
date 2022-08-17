package de.bbht.codedojo.bjoeil;

public enum TokenType {

    UNSET(' '),
    PLAYER_1('\u25cf'),
    PLAYER_2('\u25c6');

    private char representation;

    private TokenType(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return this.representation;
    }
}
