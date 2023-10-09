package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class BTypeNode {

    private Token token;

    public BTypeNode(Token token) {
        this.token = token;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", token.toString());
    }
}
