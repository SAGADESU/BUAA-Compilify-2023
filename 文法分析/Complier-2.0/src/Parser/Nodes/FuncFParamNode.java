package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class FuncFParamNode {
    // FuncFParam → BType Ident ['[' ']' { '[' ConstExp ']' }]
    private BTypeNode bTypeNode;
    private Token ident;
    private List<Token> leftBrackets;
    private List<Token> rightBrackets;
    private List<ConstExpNode> constExpNodes;

    public FuncFParamNode(BTypeNode bTypeNode, Token ident, List<Token> leftBrackets, List<Token> rightBrackets, List<ConstExpNode> constExpNodes) {
        this.bTypeNode = bTypeNode;
        this.leftBrackets = leftBrackets;
        this.rightBrackets = rightBrackets;
        this.ident = ident;
        this.constExpNodes = constExpNodes;
    }

    public void export(NodeMap nodeMap) throws IOException {
        bTypeNode.export(nodeMap);
        FileOperate.writeFile("output.txt", ident.toString());
        if (leftBrackets.size() > 0) {
            FileOperate.writeFile("output.txt", leftBrackets.get(0).toString());
            FileOperate.writeFile("output.txt", rightBrackets.get(0).toString());
            for (int i = 1; i < leftBrackets.size(); i++) {
                FileOperate.writeFile("output.txt", leftBrackets.get(i).toString());
                constExpNodes.get(i - 1).export(nodeMap);
                FileOperate.writeFile("output.txt", rightBrackets.get(i).toString());
            }
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncFParam) + "\n");
    }

    public BTypeNode getbTypeNode() {
        return bTypeNode;
    }

    public Token getIdent() {
        return ident;
    }

    public List<Token> getLeftBrackets() {
        return leftBrackets;
    }

    public List<Token> getRightBrackets() {
        return rightBrackets;
    }

    public List<ConstExpNode> getConstExpNodes() {
        return constExpNodes;
    }
}
