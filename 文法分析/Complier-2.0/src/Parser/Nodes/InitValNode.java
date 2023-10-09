package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class InitValNode {
    private ExpNode expNode;
    private Token leftBrace;
    private List<InitValNode> initValNodes;
    private List<Token> commas;
    private Token rightBrace;

    public InitValNode(ExpNode expNode, Token leftBrace, List<InitValNode> initValNodes, List<Token> commas, Token rightBrace) {
        this.expNode = expNode;
        this.leftBrace = leftBrace;
        this.initValNodes = initValNodes;
        this.commas = commas;
        this.rightBrace = rightBrace;
    }

    public void export(NodeMap nodeMap) throws IOException {
        if (expNode != null) {
            expNode.export(nodeMap);
        } else {
            FileOperate.writeFile("output.txt", leftBrace.toString());
            if (initValNodes.size() > 0) {
                for (int i = 0; i < initValNodes.size(); i++) {
                    initValNodes.get(i).export(nodeMap);
                    if (i != initValNodes.size() - 1) {
                        FileOperate.writeFile("output.txt", commas.get(i).toString());
                    }
                }
                FileOperate.writeFile("output.txt", rightBrace.toString());
            }
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.InitVal) + "\n");
    }
}
