package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class EqExpNode {
    // EqExp → RelExp | EqExp ('==' | '!=') RelExp
    private List<RelExpNode> relExpNodes;
    private List<Token> expressions;

    public EqExpNode(List<RelExpNode> relExpNodes, List<Token> expressions) {
        this.relExpNodes = relExpNodes;
        this.expressions = expressions;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for (int i = 0; i < relExpNodes.size(); i++) {
            relExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.EqExp) + "\n");
            if (i < expressions.size()) {
                FileOperate.writeFile("output.txt", expressions.get(i).toString());
            }
        }
    }

    public List<RelExpNode> getRelExpNodes() {
        return relExpNodes;
    }

    public List<Token> getExpressions() {
        return expressions;
    }
}
