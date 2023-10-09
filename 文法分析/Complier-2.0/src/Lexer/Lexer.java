package Lexer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Lexer {

    private StringOperate sOp; //string operate instance
    private String token; // save the string already read
    private char c; // save the char that is reading
    private int num; // save the number value already read
    private LexType tokenType;

    private String fileContent;
    private KeyWordMap keyWordMap;
    private int lineNum; // save the line number
    private int cursor;//string cursor position

    private Lexer() {
        sOp = new StringOperate();
        cursor = 0;
        lineNum = 1;
        keyWordMap = new KeyWordMap();
    }

    private static volatile Lexer lexer = null;

    public static Lexer getLexer() {
        if (lexer == null) {
            synchronized (Lexer.class) {
                if (lexer == null) {
                    lexer = new Lexer();
                }
            }
        }
        return lexer;
    }

    public void getFileContent(String filePath) throws IOException {//init the fileContent
        this.fileContent = new FileOperate().readSourceFile(filePath);
    }

    private void clearToken() {
        this.token = "";
        this.num = 0;
        this.tokenType = null;
    }

    private void reserve() {
        if (keyWordMap.isKeyWord(token)) {
            tokenType = keyWordMap.getType(token);
        } else {
            tokenType = LexType.IDENFR;
        }
    }

    public void runLexer(String filePath, List<Token> tokens) throws IOException {
        FileOperate.createFile(filePath);
        FileWriter fileWriter = new FileWriter(filePath);
        while (getSymbol() != -1) {
            Token tokenUnit = new Token(tokenType,token,lineNum);
            tokens.add(tokenUnit);
//            String buf = tokenType + " " + token + '\n';
//            fileWriter.write(buf);
//            System.out.println(buf);
        }
        fileWriter.flush();
        fileWriter.close();
    }

    private int getSymbol() {
        clearToken();
        while (cursor < this.fileContent.length()) {
            c = this.fileContent.charAt(cursor);
            if (sOp.isSpace(c) || sOp.isTab(c) || sOp.isNewline(c)) {
                if (sOp.isNewline(c)) {
                    lineNum++;
                }
                cursor++;
            } else {
                break;
            }
        }

        if (cursor < fileContent.length()) {
            c = fileContent.charAt(cursor++);

            if (sOp.isLetter(c) || c == '_') {
                token += c;
                while (cursor < fileContent.length() && (sOp.isLetter(fileContent.charAt(cursor)) || fileContent.charAt(cursor) == '_' || sOp.isDigit(fileContent.charAt(cursor)))) {
                    c = fileContent.charAt(cursor++);
                    token += c;
                }
                reserve();
            } else if (sOp.isDigit(c)) {
                token += c;
                while (cursor < fileContent.length() && sOp.isDigit(fileContent.charAt(cursor))) {
                    c = fileContent.charAt(cursor++);
                    token += c;
                }
                tokenType = LexType.INTCON;
                num = Integer.parseInt(token);
            } else if (sOp.isDiv(c)) { //first '/'
                token += c;
                if (cursor < fileContent.length() && sOp.isDiv(fileContent.charAt(cursor))) { // second '/'
                    c = fileContent.charAt(cursor++);
                    token += c;
                    while (cursor < fileContent.length()) //current line doesn't end
                    {
                        c = fileContent.charAt(cursor++);
                        token += c;
                        if (sOp.isNewline(c)) {
                            lineNum++;
                            break;
                        }
                    }
                    return getSymbol();
                } else if (cursor < fileContent.length() && sOp.isStar(fileContent.charAt(cursor))) { // the /* tip
                    c = fileContent.charAt(cursor++);
                    token += c;
                    while (cursor < fileContent.length()) {
                        while (cursor < fileContent.length() && !sOp.isStar(fileContent.charAt(cursor))) {
                            c = fileContent.charAt(cursor++);
                            token += c;
                            if (sOp.isNewline(c)) {
                                lineNum++;
                            }
//                            if (sOp.isNewline(c)) {
//                                lineNum++;
//                                c = fileContent.charAt(cursor++);
//                                token += c;
//                                continue;
//                            }
//                            c = fileContent.charAt(cursor++);
//                            token += c;
                        }
                        while (cursor < fileContent.length() && sOp.isStar(fileContent.charAt(cursor))) {
                            c = fileContent.charAt(cursor++);
                            token += c;
                        }
                        if (cursor < fileContent.length() && sOp.isDiv(fileContent.charAt(cursor))) {
                            c = fileContent.charAt(cursor++);
                            token += c;
                            return getSymbol();
                        }
                    }
                } else {
                    reserve();
                }

            } else if (sOp.isNot(c)) { // first '!'
                token += c;
                if (cursor < fileContent.length() && sOp.isEqu(fileContent.charAt(cursor))) // '='
                {
                    c = fileContent.charAt(cursor++);
                    token += c;
                    reserve();
                } else {
                    reserve();
                }
            } else if (sOp.isAnd(c)) { // first '&'
                token += c;
                if (cursor < fileContent.length() && sOp.isAnd(fileContent.charAt(cursor))) { // second '&'
                    c = fileContent.charAt(cursor++);
                    token += c;
                    reserve();
                } else {
                    // handle error
                    return getSymbol();
                }
            } else if (sOp.isOr(c)) { // first '|'
                token += c;
                if (cursor < fileContent.length() && sOp.isOr(fileContent.charAt(cursor))) { // second '|'
                    c = fileContent.charAt(cursor++);
                    token += c;
                    reserve();
                } else {
                    // handle error
                    return getSymbol();
                }
            } else if (sOp.isPlus(c) || sOp.isMinus(c) || sOp.isStar(c) || sOp.isMod(c) || sOp.isSemi(c) || sOp.isComma(c) || sOp.isLpa(c) || sOp.isRpa(c) || sOp.isLBrack(c) || sOp.isRBrack(c) || sOp.isRBrace(c) || sOp.isLBrace(c)) {
                token += c;
                reserve();
            } else if (sOp.isLss(c)) { // '<'
                token += c;
                if (cursor < fileContent.length() && sOp.isEqu(fileContent.charAt(cursor))) {
                    c = fileContent.charAt(cursor++);
                    token += c;
                    reserve();
                } else {
                    reserve();
                }
            } else if (sOp.isGre(c)) { // '>'
                token += c;

                if (cursor < fileContent.length() && sOp.isEqu(fileContent.charAt(cursor))) {
                    c = fileContent.charAt(cursor++);
                    token += c;
                    reserve();
                } else {
                    reserve();
                }
            } else if (sOp.isEqu(c)) { // first '='
                token += c;

                if (cursor < fileContent.length() && sOp.isEqu(fileContent.charAt(cursor))) {
                    c = fileContent.charAt(cursor++);
                    token += c;
                    reserve();
                } else {
                    reserve();
                }
            } else if (sOp.isQuo(c)) { // first '"'
                token += c;
                while (cursor < fileContent.length()) {
                    c = fileContent.charAt(cursor++);
                    token += c;
                    if (sOp.isQuo(c)) {
                        break;
                    }
                }
                tokenType = LexType.STRCON;
            } else {
                //handle error
                return getSymbol();
            }
            return 0;
        }
        return -1;
    }

}
