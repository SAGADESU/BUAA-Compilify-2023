package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class AddExpNode {
    // AddExp -> MulExp { ('+' | '?') MulExp }
    private List<MulExpNode> mulExpNodes;
    private List<Token> expressions;

    public AddExpNode(List<MulExpNode> mulExpNodes, List<Token> expressions) {
        this.mulExpNodes = mulExpNodes;
        this.expressions = expressions;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for (int i = 0; i < mulExpNodes.size(); i++) {
            mulExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.AddExp) + "\n");
            if (i < expressions.size()) {
                FileOperate.writeFile("output.txt", expressions.get(i).toString());
            }
        }
    }

    public List<MulExpNode> getMulExpNodes() {
        return mulExpNodes;
    }

    public List<Token> getExpressions() {
        return expressions;
    }
}
