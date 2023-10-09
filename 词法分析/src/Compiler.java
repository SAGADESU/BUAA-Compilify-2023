import Lexer.*;
import Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Compiler {
    private static List<Token> tokens = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Lexer lexer = Lexer.getLexer();
        lexer.getFileContent("testfile.txt");
        lexer.runLexer("output.txt",tokens);
//        for (Token token:tokens)
//        {
//            System.out.println(token.getType());
//        }
        Parser parser = new Parser(tokens);
        parser.runParser();
        parser.exportParser();
//        System.out.println(tokens.get(2).getType());
//        System.out.println("test");
    }
}
