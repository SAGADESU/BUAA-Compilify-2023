package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class FuncTypeNode {
    // FuncType ¡ú 'void' | 'int'
    private Token token;

    public FuncTypeNode(Token token) {
        this.token = token;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", token.toString());
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncType) + "\n");
    }
}
