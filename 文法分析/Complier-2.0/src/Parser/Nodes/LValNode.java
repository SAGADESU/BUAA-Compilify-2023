package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class LValNode {
    // LVal → Ident {'[' Exp ']'}
    private Token ident;
    private List<Token> leftBrackets;
    private List<ExpNode> expNodes;
    private List<Token> rightBrackets;

    public LValNode(Token ident, List<Token> leftBrackets, List<ExpNode> expNodes, List<Token> rightBrackets) {
        this.ident = ident;
        this.leftBrackets = leftBrackets;
        this.expNodes = expNodes;
        this.rightBrackets = rightBrackets;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", ident.toString());
        for (int i = 0; i < leftBrackets.size(); i++) {
            FileOperate.writeFile("output.txt", leftBrackets.get(i).toString());
            expNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", rightBrackets.get(i).toString());
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.LVal) + "\n");
    }

    public Token getIdent() {
        return ident;
    }

    public List<Token> getLeftBrackets() {
        return leftBrackets;
    }

    public List<ExpNode> getExpNodes() {
        return expNodes;
    }

    public List<Token> getRightBrackets() {
        return rightBrackets;
    }
}
