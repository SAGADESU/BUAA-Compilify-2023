package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class NumberNode {
    // Number → IntConst
    Token token;

    public NumberNode(Token token) {
        this.token = token;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", token.toString());
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.Number) + "\n");
    }
}
