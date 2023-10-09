package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;

public class ConstExpNode {
    // ConstExp ¡ú AddExp
    private AddExpNode addExpNode;

    public ConstExpNode(AddExpNode addExpNode) {
        this.addExpNode = addExpNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        addExpNode.export(nodeMap);
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.ConstExp) + "\n");
    }
}
