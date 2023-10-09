package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class PrimaryExpNode {
    // Type 1
    private Token leftParent;
    private ExpNode expNode;
    private Token rightParent;
    // Type 2
    private LValNode lValNode;
    // Type 3
    private NumberNode numberNode;

    public PrimaryExpNode(Token leftParent, ExpNode expNode, Token rightParent) {
        // Type 1
        this.leftParent = leftParent;
        this.expNode = expNode;
        this.rightParent = rightParent;
    }

    public PrimaryExpNode(LValNode lValNode) {
        // Type 2
        this.lValNode = lValNode;
    }

    public PrimaryExpNode(NumberNode numberNode) {
        // Type 3
        this.numberNode = numberNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        if (expNode != null) {
            FileOperate.writeFile("output.txt", leftParent.toString());
            expNode.export(nodeMap);
            FileOperate.writeFile("output.txt", rightParent.toString());
        } else if (lValNode != null) {
            lValNode.export(nodeMap);
        } else {
            numberNode.export(nodeMap);
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.PrimaryExp) + "\n");
    }
}
