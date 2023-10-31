package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class ConstDeclNode {
    // ConstDecl → 'const' BType ConstDef { ',' ConstDef } ';'
    private Token constToken;
    private BTypeNode bTypeNode;
    private List<ConstDefNode> constDefNodes;
    private List<Token> commas;
    private Token semicn;

    public ConstDeclNode(Token constToken, BTypeNode bTypeNode, List<ConstDefNode> constDefNodes, List<Token> commas, Token semicn) {
        this.constToken = constToken;
        this.bTypeNode = bTypeNode;
        this.constDefNodes = constDefNodes;
        this.commas = commas;
        this.semicn = semicn;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", constToken.toString());
        bTypeNode.export(nodeMap);
        constDefNodes.get(0).export(nodeMap);
        for (int i = 1; i < constDefNodes.size(); i++) {
            FileOperate.writeFile("output.txt", commas.get(i - 1).toString());
            constDefNodes.get(i).export(nodeMap);
        }
        FileOperate.writeFile("output.txt", semicn.toString());
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.ConstDecl) + "\n");
    }

    public Token getConstToken() {
        return constToken;
    }

    public BTypeNode getbTypeNode() {
        return bTypeNode;
    }

    public List<ConstDefNode> getConstDefNodes() {
        return constDefNodes;
    }

    public List<Token> getCommas() {
        return commas;
    }

    public Token getSemicn() {
        return semicn;
    }
}
