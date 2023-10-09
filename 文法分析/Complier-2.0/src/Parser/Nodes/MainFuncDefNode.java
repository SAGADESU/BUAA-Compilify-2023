package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;

public class MainFuncDefNode {
    private Token intTK;
    private Token mainTK;
    private Token leftParent;
    private Token rightParent;
    private BlockNode blockNode;

    public MainFuncDefNode(Token intTK, Token mainTK, Token leftParent, Token rightParent, BlockNode blockNode) {
        this.intTK = intTK;
        this.mainTK = mainTK;
        this.leftParent = leftParent;
        this.rightParent = rightParent;
        this.blockNode = blockNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt", intTK.toString());
        FileOperate.writeFile("output.txt", mainTK.toString());
        FileOperate.writeFile("output.txt", leftParent.toString());
        FileOperate.writeFile("output.txt", rightParent.toString());
        blockNode.export(nodeMap);
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.MainFuncDef) + "\n");
    }
}
