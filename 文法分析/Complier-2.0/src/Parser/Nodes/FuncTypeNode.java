package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.LexType;
import Lexer.Token;
import Parser.NodeMap;
import Symbol.Type;

import java.io.IOException;

public class FuncTypeNode {
    // FuncType → 'void' | 'int'
    private Token token;

    public FuncTypeNode(Token token) {
        this.token = token;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", token.toString());
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncType) + "\n");
    }

    public Token getToken() {
        return token;
    }

    public Type getType() {
        if (token.getValue().equals("int")) {
            return Type.INT;
        } else {
            return Type.VOID;
        }
    }
}
