package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class ConstInitValNode {
    private ConstExpNode constExpNode;
    private Token leftBrace;
    private List<ConstInitValNode> constInitValNodes;
    private List<Token> commas;
    private Token rightBrace;

    public ConstInitValNode(ConstExpNode constExpNode, Token leftBrace, List<ConstInitValNode> constInitValNodes, List<Token> commas, Token rightBrace) {
        this.constExpNode = constExpNode;
        this.leftBrace = leftBrace;
        this.constInitValNodes = constInitValNodes;
        this.commas = commas;
        this.rightBrace = rightBrace;
    }

    public void export(NodeMap nodeMap) throws IOException {
        if (constExpNode != null) {
            constExpNode.export(nodeMap);
        } else {
            FileOperate.writeFile("output.txt", leftBrace.toString());
            if (constInitValNodes.size() > 0) {
                constInitValNodes.get(0).export(nodeMap);
                for (int i = 1; i < constInitValNodes.size(); i++) {
                    FileOperate.writeFile("output.txt", commas.get(i - 1).toString());
                    constInitValNodes.get(i).export(nodeMap);
                }

            }
            FileOperate.writeFile("output.txt", rightBrace.toString());

        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.ConstInitVal) + "\n");
    }
}
