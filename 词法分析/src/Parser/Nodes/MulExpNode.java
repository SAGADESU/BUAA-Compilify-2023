package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class MulExpNode {
    // MulExp ¡ú UnaryExp | MulExp ('*' | '/' | '%') UnaryExp
    private List<UnaryExpNode> unaryExpNodes;
    private List<Token> expressions;

    public MulExpNode(List<UnaryExpNode> unaryExpNodes, List<Token> expressions) {
        this.unaryExpNodes = unaryExpNodes;
        this.expressions = expressions;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for (int i = 0; i < unaryExpNodes.size(); i++) {
            unaryExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.MulExp) + "\n");
            if (i < expressions.size()) {
                FileOperate.writeFile("output.txt", expressions.get(i).toString());
            }
        }
    }
}
