package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;

public class BlockItemNode {
    private DeclNode declNode;
    private StmtNode stmtNode;

    public BlockItemNode(DeclNode declNode, StmtNode stmtNode) {
        this.declNode = declNode;
        this.stmtNode = stmtNode;

    }

    public void export(NodeMap nodeMap) throws IOException {
        if (declNode != null) {
            declNode.export(nodeMap);
        } else {
            stmtNode.export(nodeMap);
        }
//        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.BlockItem) + "\n");
    }
}
