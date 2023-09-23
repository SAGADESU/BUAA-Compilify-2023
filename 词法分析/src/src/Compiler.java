import Lexer.Lexer;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        Lexer lexer = Lexer.getLexer();
        lexer.getFileContent("testfile.txt");
        lexer.runLexer("output.txt");
//        System.out.println("test");
    }
}
