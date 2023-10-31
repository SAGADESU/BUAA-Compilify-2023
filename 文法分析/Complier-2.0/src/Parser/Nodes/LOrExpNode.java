package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class LOrExpNode {
    // LOrExp → LAndExp | LOrExp '||' LAndExp
    private List<LAndExpNode> lAndExpNodes;
    private List<Token> orTK;

    public LOrExpNode(List<LAndExpNode> lAndExpNodes, List<Token> orTK) {
        this.lAndExpNodes = lAndExpNodes;
        this.orTK = orTK;
    }

    public void export(NodeMap nodeMap) throws IOException {
        for (int i = 0; i < lAndExpNodes.size(); i++) {
            lAndExpNodes.get(i).export(nodeMap);
            FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.LOrExp) + "\n");
            if (i < orTK.size()) {
                FileOperate.writeFile("output.txt", orTK.get(i).toString());
            }
        }
    }

    public List<LAndExpNode> getlAndExpNodes() {
        return lAndExpNodes;
    }

    public List<Token> getOrTK() {
        return orTK;
    }
}
