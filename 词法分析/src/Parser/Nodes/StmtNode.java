package Parser.Nodes;

import Lexer.FileOperate;
import Lexer.Token;
import Parser.NodeMap;

import java.io.IOException;
import java.util.List;

public class StmtNode {
    // Stmt → LVal '=' Exp ';'
    // | [Exp] ';'
    // | Block
    // | 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
    // | 'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt
    // | 'break' ';' | 'continue' ';'
    // | 'return' [Exp] ';'
    // | LVal '=' 'getint''('')'';'
    // | 'printf''('FormatString{','Exp}')'';'
    // 表达式的类型枚举类，尚未创建
    // Type 1 & 2
    private StmtType stmtType;
    private LValNode lValNode;
    private Token equal;
    private ExpNode expNode;
    private Token semicn;
    // Type 3
    private BlockNode blockNode;
    // Type 4
    private Token ifTK;
    private Token leftParent;
    private CondNode condNode;
    private Token rightParent;
    private List<StmtNode> stmtNodes;
    private Token elseTK;
    // Type 5
    private Token forTK;
    private ForStmtNode forStmtNode1;
    private ForStmtNode forStmtNode2;
    //    private List<ForStmtNode> forStmtNodes;
    private List<Token> semicns;
    private StmtNode stmtNode;
    // Type 6
    private Token breakOrContinue;
    // Type 7
    private Token returnTK;
    // Type 8
    private Token getIntTK;
    // Type 9
    private Token printfTK;
    private Token formatString;
    private List<Token> commas;
    private List<ExpNode> expNodes;

    public enum StmtType {
        LValEqualExp,
        ExpSemicn,
        Block,
        IF,
        FOR,
        BREAK,
        CONTINUE,
        RETURN,
        LValEqualGetint,
        PRINTF
    }

    public StmtNode(StmtType stmtType, LValNode lValNode, Token equal, ExpNode expNode, Token semicn) {
        //Type 1
        this.stmtType = stmtType;
        this.lValNode = lValNode;
        this.equal = equal;
        this.expNode = expNode;
        this.semicn = semicn;
    }

    public StmtNode(StmtType stmtType, ExpNode expNode, Token semicn) {
        // Type 2
        this.stmtType = stmtType;
        this.expNode = expNode;
        this.semicn = semicn;
    }

    public StmtNode(StmtType stmtType, BlockNode blockNode) {
        // Type 3
        this.stmtType = stmtType;
        this.blockNode = blockNode;
    }

    public StmtNode(StmtType stmtType, Token ifTK, Token leftParent, CondNode condNode, Token rightParent, List<StmtNode> stmtNodes, Token elseTK) {
        // Type 4
        this.stmtType = stmtType;
        this.ifTK = ifTK;
        this.leftParent = leftParent;
        this.condNode = condNode;
        this.rightParent = rightParent;
        this.stmtNodes = stmtNodes;
        this.elseTK = elseTK;
    }

    public StmtNode(StmtType stmtType, Token forTK, Token leftParent, ForStmtNode forStmtNode1, ForStmtNode forStmtNode2, CondNode condNode, List<Token> semicns, Token rightParent, StmtNode stmtNode) {
        // Type 5
        this.stmtType = stmtType;
        this.forTK = forTK;
        this.leftParent = leftParent;
//        this.forStmtNodes = forStmtNodes;
        this.forStmtNode1 = forStmtNode1;
        this.forStmtNode2 = forStmtNode2;
        this.condNode = condNode;
        this.rightParent = rightParent;
        this.semicns = semicns;
        this.stmtNode = stmtNode;
    }

    public StmtNode(StmtType stmtType, Token breakOrContinue, Token semicn) {
        // Type 6
        this.stmtType = stmtType;
        this.breakOrContinue = breakOrContinue;
        this.semicn = semicn;
    }

    public StmtNode(StmtType stmtType, Token returnTK, ExpNode expNode, Token semicn) {
        // Type 7
        this.stmtType = stmtType;
        this.returnTK = returnTK;
        this.expNode = expNode;
        this.semicn = semicn;
    }

    public StmtNode(StmtType stmtType, LValNode lValNode, Token equal, Token getIntTK, Token leftParent, Token rightParent, Token semicn) {
        // Type 8
        this.stmtType = stmtType;
        this.lValNode = lValNode;
        this.equal = equal;
        this.getIntTK = getIntTK;
        this.leftParent = leftParent;
        this.rightParent = rightParent;
        this.semicn = semicn;
    }

    public StmtNode(StmtType stmtType, Token printfTK, Token leftParent, Token formatString, List<Token> commas, List<ExpNode> expNodes, Token rightParent, Token semicn) {
        // Type 9
        this.stmtType = stmtType;
        this.printfTK = printfTK;
        this.leftParent = leftParent;
        this.formatString = formatString;
        this.commas = commas;
        this.expNodes = expNodes;
        this.rightParent = rightParent;
        this.semicn = semicn;
    }

    public void export(NodeMap nodeMap) throws IOException {
        switch (stmtType) {
            case LValEqualExp:
                lValNode.export(nodeMap);
                FileOperate.writeFile("output.txt", equal.toString());
                expNode.export(nodeMap);
                FileOperate.writeFile("output.txt", semicn.toString());
                break;
            case ExpSemicn:
                if (expNode != null) expNode.export(nodeMap);
                FileOperate.writeFile("output.txt", semicn.toString());
                break;
            case Block:
                blockNode.export(nodeMap);
                break;
            case IF:
                FileOperate.writeFile("output.txt", ifTK.toString());
                FileOperate.writeFile("output.txt", leftParent.toString());
                condNode.export(nodeMap);
                FileOperate.writeFile("output.txt", rightParent.toString());
                stmtNodes.get(0).export(nodeMap);
                if (elseTK != null) {
                    FileOperate.writeFile("output.txt", elseTK.toString());
                    stmtNodes.get(1).export(nodeMap);
                }
                break;
            case FOR:
                FileOperate.writeFile("output.txt", forTK.toString());
                FileOperate.writeFile("output.txt", leftParent.toString());
                if (forStmtNode1 != null) {
                    forStmtNode1.export(nodeMap);
                }
                FileOperate.writeFile("output.txt", semicns.get(0).toString());
                if (condNode != null) {
                    condNode.export(nodeMap);
                }
                FileOperate.writeFile("output.txt", semicns.get(1).toString());
                if (forStmtNode2 != null) {
                    forStmtNode2.export(nodeMap);
                }
                FileOperate.writeFile("output.txt", rightParent.toString());
                stmtNode.export(nodeMap);
                break;
            case BREAK:
            case CONTINUE:
                FileOperate.writeFile("output.txt", breakOrContinue.toString());
                FileOperate.writeFile("output.txt", semicn.toString());
                break;
            case RETURN:
                FileOperate.writeFile("output.txt", returnTK.toString());
                if (expNode != null) {
                    expNode.export(nodeMap);
                }
                FileOperate.writeFile("output.txt", semicn.toString());
                break;
            case LValEqualGetint:
                lValNode.export(nodeMap);
                FileOperate.writeFile("output.txt", equal.toString());
                FileOperate.writeFile("output.txt", getIntTK.toString());
                FileOperate.writeFile("output.txt", leftParent.toString());
                FileOperate.writeFile("output.txt", rightParent.toString());
                FileOperate.writeFile("output.txt", semicn.toString());
                break;
            case PRINTF:
                FileOperate.writeFile("output.txt", printfTK.toString());
                FileOperate.writeFile("output.txt", leftParent.toString());
                FileOperate.writeFile("output.txt", formatString.toString());
                for (int i = 0; i < commas.size(); i++) {
                    FileOperate.writeFile("output.txt", commas.get(i).toString());
                    expNodes.get(i).export(nodeMap);
                }
                FileOperate.writeFile("output.txt", rightParent.toString());
                FileOperate.writeFile("output.txt", semicn.toString());
                break;

        }
        FileOperate.writeFile("output.txt", nodeMap.getNode(NodeType.Stmt) + "\n");
    }
}
