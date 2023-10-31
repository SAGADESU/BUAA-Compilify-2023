package Parser.Nodes;

import Lexer.FileOperate;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class CompUnitNode {
    // CompUnit => {Decl} {FuncDef} MainFuncDef
    private List<DeclNode> declNodes;
    private List<FuncDefNode> funcDefNodes;
    private MainFuncDefNode mainFuncDefNode;

    public CompUnitNode(List<DeclNode> declNodes, List<FuncDefNode> funcDefNodes, MainFuncDefNode mainFuncDefNode) {
        this.declNodes = declNodes;
        this.funcDefNodes = funcDefNodes;
        this.mainFuncDefNode = mainFuncDefNode;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for(DeclNode declNode:declNodes){
            declNode.export(nodeMap);
        }
        for (FuncDefNode funcDefNode:funcDefNodes){
            funcDefNode.export(nodeMap);
        }
        mainFuncDefNode.export(nodeMap);
        FileOperate.writeFile("output.txt",nodeMap.getNode(NodeType.CompUnit)+"\n");
    }

    public List<DeclNode> getDeclNodes() {
        return declNodes;
    }

    public List<FuncDefNode> getFuncDefNodes() {
        System.out.println("funcDef");
        return funcDefNodes;
    }

    public MainFuncDefNode getMainFuncDefNode() {
        return mainFuncDefNode;
    }
}
