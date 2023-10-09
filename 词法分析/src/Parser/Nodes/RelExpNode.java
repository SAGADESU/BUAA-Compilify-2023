package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class RelExpNode {
    // RelExp ¡ú AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp
    private List<AddExpNode> addExpNodes;
    private List<Token> expressions;

    public RelExpNode(List<AddExpNode> addExpNodes, List<Token> expressions) {
        this.addExpNodes = addExpNodes;
        this.expressions = expressions;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for (int i = 0; i < addExpNodes.size(); i++) {
            addExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.RelExp) + "\n");
            if (i < expressions.size()) {
                FileOperate.writeFile("output.txt", expressions.get(i).toString());
            }
        }
    }
}
