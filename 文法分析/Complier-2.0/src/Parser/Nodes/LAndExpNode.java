package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class LAndExpNode {
    // LAndExp → EqExp | LAndExp '&&' EqExp
    private List<EqExpNode> eqExpNodes;
    private List<Token> andTK;

    public LAndExpNode(List<EqExpNode> eqExpNodes, List<Token> andTK) {
        this.eqExpNodes = eqExpNodes;
        this.andTK = andTK;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for (int i = 0; i < eqExpNodes.size(); i++) {
            eqExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.LAndExp) + "\n");
            if (i < andTK.size()) {
                FileOperate.writeFile("output.txt", andTK.get(i).toString());
            }
        }
    }

    public List<EqExpNode> getEqExpNodes() {
        return eqExpNodes;
    }

    public List<Token> getAndTK() {
        return andTK;
    }
}
