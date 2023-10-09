package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class VarDefNode {
    Token ident;
    private List<Token> leftBrackets;
    private List<ConstExpNode> constExpNodes;
    private List<Token> rightBrackets;
    private Token equal;
    private InitValNode initValNode;

    public VarDefNode(Token ident, List<Token> leftBrackets, List<ConstExpNode> constExpNodes, List<Token> rightBrackets, Token equal, InitValNode initValNode) {
        this.ident = ident;
        this.leftBrackets = leftBrackets;
        this.constExpNodes = constExpNodes;
        this.rightBrackets = rightBrackets;
        this.equal = equal;
        this.initValNode = initValNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", ident.toString());
        for (int i = 0; i < leftBrackets.size(); i++) {
            FileOperate.writeFile("output.txt", leftBrackets.get(i).toString());
            constExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", rightBrackets.get(i).toString());
        }
        if (initValNode != null) {
            FileOperate.writeFile("output.txt", equal.toString());
            initValNode.export(nodeMap);
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.VarDef) + "\n");
    }
}
