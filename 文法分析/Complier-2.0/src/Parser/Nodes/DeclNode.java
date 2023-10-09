package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;

public class DeclNode {
    private ConstDeclNode constDeclNode;
    private VarDeclNode varDeclNode;

    public DeclNode(ConstDeclNode constDeclNode, VarDeclNode varDeclNode) {
        this.constDeclNode = constDeclNode;
        this.varDeclNode = varDeclNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        if (constDeclNode != null) {
            constDeclNode.export(nodeMap);
        } else {
            varDeclNode.export(nodeMap);
        }
//        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.Decl) + "\n");
    }
}
