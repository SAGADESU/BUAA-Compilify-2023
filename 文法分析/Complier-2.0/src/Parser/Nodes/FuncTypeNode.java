package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class FuncTypeNode {
    private Token token;

    public FuncTypeNode(Token token) {
        this.token = token;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", token.toString());
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncType) + "\n");
    }
}
