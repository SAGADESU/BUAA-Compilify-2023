package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;

public class ExpNode {
    // Exp ¡ú AddExp
    private AddExpNode addExpNode;
    public ExpNode(AddExpNode addExpNode)
    {
        this.addExpNode = addExpNode;
    }
    public void export(NodeMap nodeMap) throws IOException {
        addExpNode.export(nodeMap);
        FileOperate.writeFile("output.txt",nodeMap.getNode(NodeType.Exp)+"\n");
    }
}
