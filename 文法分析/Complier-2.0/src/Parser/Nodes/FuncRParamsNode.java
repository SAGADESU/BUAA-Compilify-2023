package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class FuncRParamsNode {
    // FuncRParams → Exp { ',' Exp }
    private List<ExpNode> expNodes;
    private List<Token> commas;

    public FuncRParamsNode(List<ExpNode> expNodes, List<Token> commas) {
        this.expNodes = expNodes;
        this.commas = commas;
    }

    public void export(NodeMap nodeMap) throws IOException {
        expNodes.get(0).export(nodeMap);
        for (int i = 1; i < expNodes.size(); i++) {
            FileOperate.writeFile("output.txt", commas.get(i - 1).toString());
            expNodes.get(i).export(nodeMap);
        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.FuncRParams) + "\n");
    }

    public List<ExpNode> getExpNodes() {
        return expNodes;
    }

    public List<Token> getCommas() {
        return commas;
    }
}
