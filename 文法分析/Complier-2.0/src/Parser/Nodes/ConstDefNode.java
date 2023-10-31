package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class ConstDefNode {
    // ConstDef → Ident { '[' ConstExp ']' } '=' ConstInitVal
    private Token ident;
    private List<Token> leftBrackets;
    private List<ConstExpNode> constExpNodes;

    private List<Token> rightBrackets;
    private Token equal;
    private ConstInitValNode constInitValNode;

    public ConstDefNode(Token ident, List<Token> leftBrackets, List<ConstExpNode> constExpNodes, List<Token> rightBrackets, Token equal, ConstInitValNode constInitValNode) {
        this.ident = ident;
        this.leftBrackets = leftBrackets;
        this.constExpNodes = constExpNodes;
        this.rightBrackets = rightBrackets;
        this.equal = equal;
        this.constInitValNode = constInitValNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", ident.toString());
        for (int i = 0; i < constExpNodes.size(); i++) {
            FileOperate.writeFile("output.txt", leftBrackets.get(i).toString());
            constExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", rightBrackets.get(i).toString());
        }
        FileOperate.writeFile("output.txt", equal.toString());
        constInitValNode.export(nodeMap);
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.ConstDef) + "\n");
    }

    public Token getIdent() {
        return ident;
    }

    public List<Token> getLeftBrackets() {
        return leftBrackets;
    }

    public List<ConstExpNode> getConstExpNodes() {
        return constExpNodes;
    }

    public List<Token> getRightBrackets() {
        return rightBrackets;
    }

    public Token getEqual() {
        return equal;
    }

    public ConstInitValNode getConstInitValNode() {
        return constInitValNode;
    }
}
