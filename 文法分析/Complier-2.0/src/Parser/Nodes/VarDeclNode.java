package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class VarDeclNode {
    private BTypeNode bTypeNode;
    private List<VarDefNode> varDefNodes;
    private List<Token> commas;
    private Token semicn;
    public VarDeclNode(BTypeNode bTypeNode,List<VarDefNode> varDefNodes,List<Token> commas,Token semicn)
    {
        this.bTypeNode = bTypeNode;
        this.varDefNodes = varDefNodes;
        this.commas = commas;
        this.semicn = semicn;
    }
    public void export(NodeMap nodeMap) throws IOException {
        bTypeNode.export(nodeMap);
        varDefNodes.get(0).export(nodeMap);
        for(int i = 1;i<varDefNodes.size();i++){
            FileOperate.writeFile("output.txt",commas.get(i-1).toString());
            varDefNodes.get(i).export(nodeMap);
        }
        FileOperate.writeFile("output.txt",semicn.toString());
        FileOperate.writeFile("output.txt",nodeMap.getNode(NodeType.VarDecl)+"\n");

    }
}
