package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;

public class CondNode {
    // Cond ¡ú LOrExp
    private LOrExpNode lOrExpNode;

    public CondNode(LOrExpNode lOrExpNode) {
        this.lOrExpNode = lOrExpNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        lOrExpNode.export(nodeMap);
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.Cond)+"\n");
    }
}
