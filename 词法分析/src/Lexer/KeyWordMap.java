package Lexer;

import java.util.HashMap;

public class KeyWordMap {
    private HashMap<String, LexType> keyWords;

    public KeyWordMap() {
        keyWords = new HashMap<>();
        keyWords.put("Ident", LexType.IDENFR);
        keyWords.put("IntConst", LexType.INTCON);
        keyWords.put("FormatString", LexType.STRCON);
        keyWords.put("main", LexType.MAINTK);
        keyWords.put("const", LexType.CONSTTK);
        keyWords.put("int", LexType.INTTK);
        keyWords.put("break", LexType.BREAKTK);
        keyWords.put("continue", LexType.CONTINUETK);
        keyWords.put("if", LexType.IFTK);
        keyWords.put("else", LexType.ELSETK);
        keyWords.put("!", LexType.NOT);
        keyWords.put("&&", LexType.AND);
        keyWords.put("||", LexType.OR);
        keyWords.put("for", LexType.FORTK);
        keyWords.put("getint", LexType.GETINTTK);
        keyWords.put("printf", LexType.PRINTFTK);
        keyWords.put("return", LexType.RETURNTK);
        keyWords.put("+", LexType.PLUS);
        keyWords.put("-", LexType.MINU);
        keyWords.put("void", LexType.VOIDTK);
        keyWords.put("*", LexType.MULT);
        keyWords.put("/", LexType.DIV);
        keyWords.put("%", LexType.MOD);
        keyWords.put("<", LexType.LSS);
        keyWords.put("<=", LexType.LEQ);
        keyWords.put(">", LexType.GRE);
        keyWords.put(">=", LexType.GEQ);
        keyWords.put("==", LexType.EQL);
        keyWords.put("!=", LexType.NEQ);
        keyWords.put("=", LexType.ASSIGN);
        keyWords.put(";", LexType.SEMICN);
        keyWords.put(",", LexType.COMMA);
        keyWords.put("(", LexType.LPARENT);
        keyWords.put(")", LexType.RPARENT);
        keyWords.put("[", LexType.LBRACK);
        keyWords.put("]", LexType.RBRACK);
        keyWords.put("{", LexType.LBRACE);
        keyWords.put("}", LexType.RBRACE);
    }

    //get the type of ident
    public LexType getType(String ident) {
        return keyWords.get(ident);
    }

    //judge if the token is a keyword, return true or false
    public boolean isKeyWord(String str) {
        return keyWords.containsKey(str);
    }

}
