package Lexer;

public class StringOperate {
    public StringOperate() {

    }

    public boolean isSpace(char ch) {
        return Character.isWhitespace(ch);
    }

    public boolean isNewline(char ch) {
        return ch == '\n';
    }

    public boolean isTab(char ch) {
        return ch == '\t';
    }

    public boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

    public boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    public boolean isColon(char ch) {
        return ch == ':';
    }

    public boolean isComma(char ch) {
        return ch == ',';
    }

    public boolean isSemi(char ch) {
        return ch == ';';
    }

    public boolean isEqu(char ch) {
        return ch == '=';
    }

    public boolean isPlus(char ch) {
        return ch == '+';
    }

    public boolean isMinus(char ch) {
        return ch == '-';
    }

    public boolean isDiv(char ch) {
        return ch == '/';
    }

    public boolean isMod(char ch) {
        return ch == '%';
    }

    public boolean isStar(char ch) {
        return ch == '*';
    }

    public boolean isNot(char ch) {
        return ch == '!';
    }

    public boolean isOr(char ch) {
        return ch == '|';
    }

    public boolean isAnd(char ch) {
        return ch == '&';
    }

    public boolean isLss(char ch) {
        return ch == '<';
    }

    public boolean isGre(char ch) {
        return ch == '>';
    }

    public boolean isLpa(char ch) {
        return ch == '(';
    }

    public boolean isRpa(char ch) {
        return ch == ')';
    }

    public boolean isLBrack(char ch) {
        return ch == '[';
    }

    public boolean isRBrack(char ch) {
        return ch == ']';
    }

    public boolean isLBrace(char ch) {
        return ch == '{';
    }

    public boolean isRBrace(char ch) {
        return ch == '}';
    }

    public boolean isQuo(char ch) {
        return ch == '"';
    }
}
