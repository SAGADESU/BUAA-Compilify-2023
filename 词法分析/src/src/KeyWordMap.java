import java.util.HashMap;

public class KeyWordMap {
    private HashMap<String, String> keyWords;

    public KeyWordMap() {
        keyWords = new HashMap<>();
        keyWords.put("Ident", "IDENFR");
        keyWords.put("IntConst", "INTCON");
        keyWords.put("FormatString", "STRCON");
        keyWords.put("main", "MAINTK");
        keyWords.put("const", "CONSTTK");
        keyWords.put("int", "INTTK");
        keyWords.put("break", "BREAKTK");
        keyWords.put("continue", "CONTINUETK");
        keyWords.put("if", "IFTK");
        keyWords.put("else", "ELSETK");
        keyWords.put("!", "NOT");
        keyWords.put("&&", "AND");
        keyWords.put("||", "OR");
        keyWords.put("for", "FORTK");
        keyWords.put("getint", "GETINTTK");
        keyWords.put("printf", "PRINTFTK");
        keyWords.put("return", "RETURNTK");
        keyWords.put("+", "PLUS");
        keyWords.put("-", "MINU");
        keyWords.put("void", "VOIDTK");
        keyWords.put("*", "MULT");
        keyWords.put("/", "DIV");
        keyWords.put("%", "MOD");
        keyWords.put("<", "LSS");
        keyWords.put("<=", "LEQ");
        keyWords.put(">", "GRE");
        keyWords.put(">=", "GEQ");
        keyWords.put("==", "EQL");
        keyWords.put("!=", "NEQ");
        keyWords.put("=", "ASSIGN");
        keyWords.put(";", "SEMICN");
        keyWords.put(",", "COMMA");
        keyWords.put("(", "LPARENT");
        keyWords.put(")", "RPARENT");
        keyWords.put("[", "LBRACK");
        keyWords.put("]", "RBRACK");
        keyWords.put("{", "LBRACE");
        keyWords.put("}", "RBRACE");
    }

    //get the type of ident
    public String getType(String ident) {
        return keyWords.get(ident);
    }

    //judge if the token is a keyword, return true or false
    public boolean isKeyWord(String str) {
        return keyWords.containsKey(str);
    }

}
