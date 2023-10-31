package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;

public class DeclNode {
    // Decl → ConstDecl | VarDecl
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

    public ConstDeclNode getConstDeclNode() {
        return constDeclNode;
    }

    public VarDeclNode getVarDeclNode() {
        return varDeclNode;
    }
}
