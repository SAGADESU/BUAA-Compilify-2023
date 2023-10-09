package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class ForStmtNode {

    private LValNode lValNode;
    private Token equal;
    private ExpNode expNode;

    public ForStmtNode(LValNode lValNode, Token equal, ExpNode expNode) {
        this.lValNode = lValNode;
        this.equal = equal;
        this.expNode = expNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        lValNode.export(nodeMap);
        FileOperate.writeFile("output.txt", equal.toString());
        expNode.export(nodeMap);
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.ForStmt) + "\n");
    }
}
