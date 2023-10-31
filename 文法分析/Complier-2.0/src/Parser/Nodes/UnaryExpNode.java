package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class UnaryExpNode {
    // UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')'
    // Type 1
    private PrimaryExpNode primaryExpNode;
    // Type 2
    private Token ident;
    private Token leftParent;
    private FuncRParamsNode funcRParamsNode;
    private Token rightParent;
    // Type 3
    private UnaryOpNode unaryOpNode;
    private UnaryExpNode unaryExpNode;

    public UnaryExpNode(PrimaryExpNode primaryExpNode) {
        // Type 1
        this.primaryExpNode = primaryExpNode;
    }

    public UnaryExpNode(Token ident, Token leftParent, FuncRParamsNode funcRParamsNode, Token rightParent) {
        // Type 2
        this.ident = ident;
        this.leftParent = leftParent;
        this.funcRParamsNode = funcRParamsNode;
        this.rightParent = rightParent;
    }

    public UnaryExpNode(UnaryOpNode unaryOpNode, UnaryExpNode unaryExpNode) {
        // Type 3
        this.unaryOpNode = unaryOpNode;
        this.unaryExpNode = unaryExpNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        if (primaryExpNode != null) {
            primaryExpNode.export(nodeMap);
        } else if (ident != null) {
            FileOperate.writeFile("output.txt", ident.toString());
            FileOperate.writeFile("output.txt", leftParent.toString());
            if (funcRParamsNode != null) {
                funcRParamsNode.export(nodeMap);
            }
            FileOperate.writeFile("output.txt", rightParent.toString());
        } else {
            unaryOpNode.export(nodeMap);
            unaryExpNode.export(nodeMap);
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.UnaryExp) + "\n");
    }

    public PrimaryExpNode getPrimaryExpNode() {
        return primaryExpNode;
    }

    public Token getIdent() {
        return ident;
    }

    public Token getLeftParent() {
        return leftParent;
    }

    public FuncRParamsNode getFuncRParamsNode() {
        return funcRParamsNode;
    }

    public Token getRightParent() {
        return rightParent;
    }

    public UnaryOpNode getUnaryOpNode() {
        return unaryOpNode;
    }

    public UnaryExpNode getUnaryExpNode() {
        return unaryExpNode;
    }
}
