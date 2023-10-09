package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class FuncDefNode {

    private FuncTypeNode funcTypeNode;
    private Token ident;

    private Token leftParent;
    private FuncFParamsNode funcParamsNode;
    private Token rightParent;
    private BlockNode blockNode;

    public FuncDefNode(FuncTypeNode funcTypeNode, Token ident, Token leftParent, FuncFParamsNode funcParamsNode, Token rightParent, BlockNode blockNode) {
        this.funcTypeNode = funcTypeNode;
        this.ident = ident;
        this.leftParent = leftParent;
        this.funcParamsNode = funcParamsNode;
        this.rightParent = rightParent;
        this.blockNode = blockNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        funcTypeNode.export(nodeMap);
        FileOperate.writeFile("output.txt", ident.toString());
        FileOperate.writeFile("output.txt", leftParent.toString());
        if (funcParamsNode != null) {
            funcParamsNode.export(nodeMap);
        }
        FileOperate.writeFile("output.txt", rightParent.toString());
        blockNode.export(nodeMap);
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncDef)+"\n");
    }
}
