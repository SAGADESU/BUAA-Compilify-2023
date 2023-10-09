package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class FuncFParamsNode {

    private List<FuncFParamNode> funcFParamNodes;
    private List<Token> commas;

    public FuncFParamsNode(List<FuncFParamNode> funcFParamNodes, List<Token> commas) {
        this.funcFParamNodes = funcFParamNodes;
        this.commas = commas;
    }

    public void export(NodeMap nodeMap) throws IOException {
        funcFParamNodes.get(0).export(nodeMap);
        for (int i = 1; i < funcFParamNodes.size(); i++) {
            FileOperate.writeFile("output.txt", commas.get(i - 1).toString());
            funcFParamNodes.get(i).export(nodeMap);
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncFParams) + "\n");
    }
}
