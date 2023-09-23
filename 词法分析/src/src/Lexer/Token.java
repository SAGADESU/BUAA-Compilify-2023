package Lexer;

import Lexer.LexType;

public class Token {
    private LexType type; //token type
    private String value; //token value
    private int lineNumber; //token line number
    private int columnNumber; // token column number

    public Token(LexType type, String value, int lineNumber, int columnNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

}
