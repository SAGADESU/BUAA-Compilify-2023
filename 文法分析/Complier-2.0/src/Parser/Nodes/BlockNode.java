package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class BlockNode {

    private Token leftBrace;
    private List<BlockItemNode> blockItemNodes;

    private Token rightBrace;

    public BlockNode(Token leftBrace, List<BlockItemNode> blockItemNodes, Token rightBrace) {
        this.leftBrace = leftBrace;
        this.blockItemNodes = blockItemNodes;
        this.rightBrace = rightBrace;
    }
    public void export(NodeMap nodeMap) throws IOException {
        FileOperate.writeFile("output.txt",leftBrace.toString());
        for (BlockItemNode blockItemNode:blockItemNodes){
            blockItemNode.export(nodeMap);
        }
        FileOperate.writeFile("output.txt",rightBrace.toString());
        FileOperate.writeFile("output.txt",nodeMap.getNode(NodeType.Block)+"\n");
    }
}
