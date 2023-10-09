package Lexer;

import Lexer.LexType;

public class Token {
    private LexType type; //token type
    private String value; //token value
    private int lineNumber; //token line number
//    private int columnNumber; // token column number

    public Token(LexType type, String value, int lineNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
//        this.columnNumber = columnNumber;
    }

    public LexType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    @Override
    public String toString() {
        return type.toString() + " " + value + "\n";
    }
}
