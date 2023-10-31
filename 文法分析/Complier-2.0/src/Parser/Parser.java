package Parser;

import Lexer.LexType;
import Lexer.Token;
import Parser.Nodes.*;
import Error.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private NodeMap nodeMap;
    private List<Token> tokens;

    private CompUnitNode compUnitNode;
    private int index = 0;

    public CompUnitNode getCompUnitNode() {
        return compUnitNode;
    }

    public Parser(List<Token> tokens) {
        this.nodeMap = new NodeMap();
        this.tokens = tokens;
    }

    public void runParser() {
        this.compUnitNode = CompUnit();
    }

    public void exportParser() throws IOException {
        compUnitNode.export(nodeMap);
    }

    public boolean isExp() {
        return tokens.get(index).getType() == LexType.IDENFR || tokens.get(index).getType() == LexType.PLUS || tokens.get(index).getType() == LexType.MINU || tokens.get(index).getType() == LexType.NOT || tokens.get(index).getType() == LexType.LPARENT || tokens.get(index).getType() == LexType.INTCON;
    }

    private Token match(LexType lexType) {
        if (tokens.get(index).getType() == lexType) {
            return tokens.get(index++);
        } else if (lexType == LexType.SEMICN) { // 缺少分号的情况，对应错误类型i
            ErrorHandler.getInstance().addError(new ErrorUnit(tokens.get(index - 1).getLineNumber(), ErrorType.i));
            return new Token(LexType.SEMICN, ";", tokens.get(index - 1).getLineNumber());
        } else if (lexType == LexType.RPARENT) { // 缺少右小括号的情况，对应错误类型j
            ErrorHandler.getInstance().addError(new ErrorUnit(tokens.get(index - 1).getLineNumber(), ErrorType.j));
            return new Token(LexType.RPARENT, ")", tokens.get(index - 1).getLineNumber());
        } else if (lexType == LexType.RBRACK) { // 缺少右中括号的情况，对应错误类型k
            ErrorHandler.getInstance().addError(new ErrorUnit(tokens.get(index - 1).getLineNumber(), ErrorType.k));
            return new Token(LexType.RBRACK, "]", tokens.get(index - 1).getLineNumber());
        } else {
            System.out.println(tokens.get(index - 1).getType());
            throw new RuntimeException("Syntax error: " + tokens.get(index).toString() + " at line " + tokens.get(index).getLineNumber() + " expecting " + lexType);
        }
    }

    private CompUnitNode CompUnit() {
        List<DeclNode> declNodes = new ArrayList<>();
        List<FuncDefNode> funcDefNodes = new ArrayList<>();
        MainFuncDefNode mainFuncDefNode;
        while (tokens.get(index + 1).getType() != LexType.MAINTK && tokens.get(index + 2).getType() != LexType.LPARENT) {
            DeclNode declNode = Decl();
            declNodes.add(declNode);
        }
        while (tokens.get(index + 1).getType() != LexType.MAINTK) {
            FuncDefNode funcDefNode = FuncDef();
            funcDefNodes.add(funcDefNode);
        }
        mainFuncDefNode = MainFuncDef();
        return new CompUnitNode(declNodes, funcDefNodes, mainFuncDefNode);
//        return new CompUnitNode();
    }

    private DeclNode Decl() {
        ConstDeclNode constDeclNode = null;
        VarDeclNode varDeclNode = null;
        if (tokens.get(index).getType() == LexType.CONSTTK) {
            constDeclNode = ConstDecl();
        } else {
            varDeclNode = VarDecl();
        }
        return new DeclNode(constDeclNode, varDeclNode);
    }


    private ConstDeclNode ConstDecl() {
        Token constTK = match(LexType.CONSTTK);
        BTypeNode bTypeNode = BType();
        List<ConstDefNode> constDefNodes = new ArrayList<>();
        List<Token> commas = new ArrayList<>();
        Token semicn;
        constDefNodes.add(ConstDef());
        while (tokens.get(index).getType() == LexType.COMMA) {
            commas.add(match(LexType.COMMA));
            constDefNodes.add(ConstDef());
        }
        semicn = match(LexType.SEMICN);
        return new ConstDeclNode(constTK, bTypeNode, constDefNodes, commas, semicn);
    }

    private BTypeNode BType() {
        Token bType = match(LexType.INTTK);
        return new BTypeNode(bType);
    }

    private ConstDefNode ConstDef() {
        Token ident = match(LexType.IDENFR);
        List<Token> leftBrackets = new ArrayList<>();
        List<ConstExpNode> constExpNodes = new ArrayList<>();
        List<Token> rightBrackets = new ArrayList<>();
        Token equal = null;
        while (tokens.get(index).getType() == LexType.LBRACK) {
            leftBrackets.add(match(LexType.LBRACK));
            constExpNodes.add(ConstExp());
            rightBrackets.add(match(LexType.RBRACK));
        }
        equal = match(LexType.ASSIGN);
        ConstInitValNode constInitValNode = ConstInitVal();
        return new ConstDefNode(ident, leftBrackets, constExpNodes, rightBrackets, equal, constInitValNode);
    }

    private ConstInitValNode ConstInitVal() {
        ConstExpNode constExpNode = null;
        Token leftBrace = null;
        List<ConstInitValNode> constInitValNodes = new ArrayList<>();
        List<Token> commas = new ArrayList<>();
        Token rightBrace = null;
        if (tokens.get(index).getType() == LexType.LBRACE) {
            leftBrace = match(LexType.LBRACE);
            if (tokens.get(index).getType() != LexType.RBRACE) {
                constInitValNodes.add(ConstInitVal());
                while (tokens.get(index).getType() != LexType.RBRACE) {
                    commas.add(match(LexType.COMMA));
                    constInitValNodes.add(ConstInitVal());
                }
            }
            rightBrace = match(LexType.RBRACE);
        } else {
            constExpNode = ConstExp();
        }
        return new ConstInitValNode(constExpNode, leftBrace, constInitValNodes, commas, rightBrace);
    }

    private VarDeclNode VarDecl() {
        BTypeNode bTypeNode = BType();
        List<VarDefNode> varDefNodes = new ArrayList<>();
        List<Token> commas = new ArrayList<>();
        Token semicn = null;
        varDefNodes.add(VarDef());
        while (tokens.get(index).getType() != LexType.SEMICN) {
            commas.add(match(LexType.COMMA));
            varDefNodes.add(VarDef());
        }
        semicn = match(LexType.SEMICN);
        return new VarDeclNode(bTypeNode, varDefNodes, commas, semicn);
    }

    private VarDefNode VarDef() {
        Token ident = match(LexType.IDENFR);
        List<Token> leftBrackets = new ArrayList<>();
        List<ConstExpNode> constExpNodes = new ArrayList<>();
        List<Token> rightBrackets = new ArrayList<>();
        Token equal = null;
        InitValNode initValNode = null;
        while (tokens.get(index).getType() == LexType.LBRACK) {
            leftBrackets.add(match(LexType.LBRACK));
            constExpNodes.add(ConstExp());
            rightBrackets.add(match(LexType.RBRACK));
        }
        if (tokens.get(index).getType() == LexType.ASSIGN) {
            equal = match(LexType.ASSIGN);
            initValNode = InitVal();
        }
        return new VarDefNode(ident, leftBrackets, constExpNodes, rightBrackets, equal, initValNode);
    }

    private InitValNode InitVal() {
        ExpNode expNode = null;
        Token leftBrace = null;
        List<InitValNode> initValNodes = new ArrayList<>();
        List<Token> commas = new ArrayList<>();
        Token rightBrace = null;
        if (tokens.get(index).getType() == LexType.LBRACE) {
            leftBrace = match(LexType.LBRACE);
            if (tokens.get(index).getType() != LexType.RBRACE) {
                initValNodes.add(InitVal());
                while (tokens.get(index).getType() != LexType.RBRACE) {
                    commas.add(match(LexType.COMMA));
                    initValNodes.add(InitVal());
                }
            }
            rightBrace = match(LexType.RBRACE);
        } else {
            expNode = Exp();
        }
        return new InitValNode(expNode, leftBrace, initValNodes, commas, rightBrace);
    }

    private FuncDefNode FuncDef() {
        FuncTypeNode funcTypeNode = FuncType();
        Token ident = match(LexType.IDENFR);
        Token leftParent = match(LexType.LPARENT);
        FuncFParamsNode funcFParamsNode = null;
        // 这里可能会遇到缺少右括号的错误，因此改用INTTK判断
        if (tokens.get(index).getType() == LexType.INTTK) {
            funcFParamsNode = FuncFParams();
        }
        Token rightParent = match(LexType.RPARENT);
        BlockNode blockNode = Block();
        return new FuncDefNode(funcTypeNode, ident, leftParent, funcFParamsNode, rightParent, blockNode);
    }

    private MainFuncDefNode MainFuncDef() {
        Token intTK = match(LexType.INTTK);
        Token mainTK = match(LexType.MAINTK);
        Token leftParent = match(LexType.LPARENT);
        Token rightParent = match(LexType.RPARENT);
        BlockNode blockNode = Block();
        return new MainFuncDefNode(intTK, mainTK, leftParent, rightParent, blockNode);
    }

    private FuncTypeNode FuncType() {
        if (tokens.get(index).getType() == LexType.VOIDTK) {
            Token voidTK = match(LexType.VOIDTK);
            return new FuncTypeNode(voidTK);
        } else {
            Token intTK = match(LexType.INTTK);
            return new FuncTypeNode(intTK);
        }
    }

    private FuncFParamsNode FuncFParams() {
        List<FuncFParamNode> funcFParamNodes = new ArrayList<>();
        List<Token> commas = new ArrayList<>();
        funcFParamNodes.add(FuncFParam());
        while (tokens.get(index).getType() == LexType.COMMA) {
            commas.add(match(LexType.COMMA));
            funcFParamNodes.add(FuncFParam());
        }
        return new FuncFParamsNode(funcFParamNodes, commas);
    }

    private FuncFParamNode FuncFParam() {
        BTypeNode bTypeNode = BType();
        Token ident = match(LexType.IDENFR);
        List<Token> leftBrackets = new ArrayList<>();
        List<Token> rightBrackets = new ArrayList<>();
        List<ConstExpNode> constExpNodes = new ArrayList<>();
        if (tokens.get(index).getType() == LexType.LBRACK) {
            leftBrackets.add(match(LexType.LBRACK));
            rightBrackets.add(match(LexType.RBRACK));
            while (tokens.get(index).getType() == LexType.LBRACK) {
                leftBrackets.add(match(LexType.LBRACK));
                constExpNodes.add(ConstExp());
                rightBrackets.add(match(LexType.RBRACK));
            }
        }
        return new FuncFParamNode(bTypeNode, ident, leftBrackets, rightBrackets, constExpNodes);
    }

    private BlockNode Block() {
        Token leftBrace = match(LexType.LBRACE);
        List<BlockItemNode> blockItemNodes = new ArrayList<>();
        while (tokens.get(index).getType() != LexType.RBRACE) {
            blockItemNodes.add(BlockItem());
        }
        Token rightBrace = match(LexType.RBRACE);
        return new BlockNode(leftBrace, blockItemNodes, rightBrace);
    }

    private BlockItemNode BlockItem() {
        DeclNode declNode = null;
        StmtNode stmtNode = null;
        if (tokens.get(index).getType() == LexType.CONSTTK || tokens.get(index).getType() == LexType.INTTK) {
            declNode = Decl();
        } else {
            stmtNode = Stmt();
        }
        return new BlockItemNode(declNode, stmtNode);
    }

    private StmtNode Stmt() {

        if (tokens.get(index).getType() == LexType.LBRACE) {
            // Block
            BlockNode blockNode = Block();
            return new StmtNode(StmtNode.StmtType.Block, blockNode);
        } else if (tokens.get(index).getType() == LexType.IFTK) {
            // 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
            Token ifTK = match(LexType.IFTK);
            Token leftParent = match(LexType.LPARENT);
            CondNode condNode = Cond();
            Token rightParent = match(LexType.RPARENT);
            List<StmtNode> stmtNodes = new ArrayList<>();
            stmtNodes.add(Stmt());
            Token elseTK = null;
            if (tokens.get(index).getType() == LexType.ELSETK) {
                elseTK = match(LexType.ELSETK);
                stmtNodes.add(Stmt());
            }
            return new StmtNode(StmtNode.StmtType.IF, ifTK, leftParent, condNode, rightParent, stmtNodes, elseTK);
        } else if (tokens.get(index).getType() == LexType.PRINTFTK) {
            // 'printf''('FormatString{','Exp}')'';'
            Token printfTK = match(LexType.PRINTFTK);
            Token leftParent = match(LexType.LPARENT);
            Token formatString = match(LexType.STRCON);
            List<Token> commas = new ArrayList<>();
            List<ExpNode> expNodes = new ArrayList<>();
            while (tokens.get(index).getType() == LexType.COMMA) {
                commas.add(match(LexType.COMMA));
                expNodes.add(Exp());
            }
            Token rightParent = match(LexType.RPARENT);
            Token semicn = match(LexType.SEMICN);
            return new StmtNode(StmtNode.StmtType.PRINTF, printfTK, leftParent, formatString, commas, expNodes, rightParent, semicn);
        } else if (tokens.get(index).getType() == LexType.BREAKTK) {
            // 'break' ';'
            Token breakTK = match(LexType.BREAKTK);
            Token semicn = match(LexType.SEMICN);
            return new StmtNode(StmtNode.StmtType.BREAK, breakTK, semicn);
        } else if (tokens.get(index).getType() == LexType.CONTINUETK) {
            // 'continue' ';'
            Token continueTK = match(LexType.CONTINUETK);
            Token semicn = match(LexType.SEMICN);
            return new StmtNode(StmtNode.StmtType.CONTINUE, continueTK, semicn);
        } else if (tokens.get(index).getType() == LexType.RETURNTK) {
            // 'return' [Exp] ';'
            Token returnTK = match(LexType.RETURNTK);
            ExpNode expNode = null;
            if (isExp()) {
                expNode = Exp();
            }
            Token semicn = match(LexType.SEMICN);
            return new StmtNode(StmtNode.StmtType.RETURN, returnTK, expNode, semicn);
        } else if (tokens.get(index).getType() == LexType.FORTK) {
            // 'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt
            Token forTK = match(LexType.FORTK);
            Token leftParent = match(LexType.LPARENT);
            ForStmtNode forStmtNode1 = null;
            ForStmtNode forStmtNode2 = null;
            List<Token> semicns = new ArrayList<>();
            CondNode condNode = null;
            if (tokens.get(index).getType() != LexType.SEMICN) {
                forStmtNode1 = ForStmt();
            }
            semicns.add(match(LexType.SEMICN));
            if (tokens.get(index).getType() != LexType.SEMICN) {
                condNode = Cond();
            }
            semicns.add(match(LexType.SEMICN));
            if (tokens.get(index).getType() != LexType.RPARENT) {
                forStmtNode2 = ForStmt();
            }
            Token rightParent = match(LexType.RPARENT);
            StmtNode stmtNode = Stmt();
            return new StmtNode(StmtNode.StmtType.FOR, forTK, leftParent, forStmtNode1, forStmtNode2, condNode, semicns, rightParent, stmtNode);
        } else {
            int equal = index, semicn = index;
            for (int i = index; i < tokens.size(); i++) {
                if (tokens.get(i).getType() == LexType.ASSIGN) {
                    equal = i;
                }
                if (tokens.get(i).getType() == LexType.SEMICN) {
                    semicn = i;
                    break;
                }
            }
            if (equal < semicn && equal > index) {
                // LVal '=' Exp ';'
                // LVal '=' 'getint''('')'';'
                LValNode lValNode = LVal();
                Token equalTK = match(LexType.ASSIGN);
                if (tokens.get(index).getType() == LexType.GETINTTK) {
                    Token getintTK = match(LexType.GETINTTK);
                    Token leftParent = match(LexType.LPARENT);
                    Token rightParent = match(LexType.RPARENT);
                    Token semicnTK = match(LexType.SEMICN);
                    return new StmtNode(StmtNode.StmtType.LValEqualGetint, lValNode, equalTK, getintTK, leftParent, rightParent, semicnTK);
                } else {
                    ExpNode expNode = Exp();
                    Token semicnTK = match(LexType.SEMICN);
                    return new StmtNode(StmtNode.StmtType.LValEqualExp, lValNode, equalTK, expNode, semicnTK);
                }
            } else {
                // [Exp] ';'
                ExpNode expNode = null;
                if (isExp()) {
                    expNode = Exp();
                }
                Token semicnTK = match(LexType.SEMICN);
                return new StmtNode(StmtNode.StmtType.ExpSemicn, expNode, semicnTK);
            }

        }
    }

    private ForStmtNode ForStmt() {
        LValNode lValNode = LVal();
        Token equal = match(LexType.ASSIGN);
        ExpNode expNode = Exp();
        return new ForStmtNode(lValNode, equal, expNode);
    }

    private ExpNode Exp() {
        return new ExpNode(AddExp());
    }

    private CondNode Cond() {
        return new CondNode(LOrExp());
    }

    private LValNode LVal() {
        Token ident = match(LexType.IDENFR);
        List<Token> leftBrackets = new ArrayList<>();
        List<ExpNode> expNodes = new ArrayList<>();
        List<Token> rightBrackets = new ArrayList<>();
        while (tokens.get(index).getType() == LexType.LBRACK) {
            leftBrackets.add(match(LexType.LBRACK));
            expNodes.add(Exp());
            rightBrackets.add(match(LexType.RBRACK));
        }
        return new LValNode(ident, leftBrackets, expNodes, rightBrackets);
    }

    private PrimaryExpNode PrimaryExp() {
        if (tokens.get(index).getType() == LexType.LPARENT) {
            Token leftParent = match(LexType.LPARENT);
            ExpNode expNode = Exp();
            Token rightParent = match(LexType.RPARENT);
            return new PrimaryExpNode(leftParent, expNode, rightParent);
        } else if (tokens.get(index).getType() == LexType.INTCON) {
            NumberNode numberNode = Number();
            return new PrimaryExpNode(numberNode);
        } else {
            LValNode lValNode = LVal();
            return new PrimaryExpNode(lValNode);
        }
    }

    private NumberNode Number() {
        return new NumberNode(match(LexType.INTCON));
    }

    private UnaryExpNode UnaryExp() {
        if (tokens.get(index).getType() == LexType.IDENFR && tokens.get(index + 1).getType() == LexType.LPARENT) {
            Token ident = match(LexType.IDENFR);
            Token leftParent = match(LexType.LPARENT);
            FuncRParamsNode funcRParamsNode = null;
            if (isExp()) {
                funcRParamsNode = FuncRParams();
            }
            Token rightParent = match(LexType.RPARENT);
            return new UnaryExpNode(ident, leftParent, funcRParamsNode, rightParent);
        } else if (tokens.get(index).getType() == LexType.PLUS || tokens.get(index).getType() == LexType.MINU || tokens.get(index).getType() == LexType.NOT) {
            UnaryOpNode unaryOpNode = UnaryOp();
            UnaryExpNode unaryExpNode = UnaryExp();
            return new UnaryExpNode(unaryOpNode, unaryExpNode);
        } else {
            PrimaryExpNode primaryExpNode = PrimaryExp();
            return new UnaryExpNode(primaryExpNode);
        }
    }

    private UnaryOpNode UnaryOp() {
        Token token = null;
        if (tokens.get(index).getType() == LexType.PLUS) {
            token = match(LexType.PLUS);
        } else if (tokens.get(index).getType() == LexType.MINU) {
            token = match(LexType.MINU);
        } else {
            token = match(LexType.NOT);
        }
        return new UnaryOpNode(token);
    }

    private FuncRParamsNode FuncRParams() {
        List<ExpNode> expNodes = new ArrayList<>();
        List<Token> commas = new ArrayList<>();
        expNodes.add(Exp());
        while (tokens.get(index).getType() == LexType.COMMA) {
            commas.add(match(LexType.COMMA));
            expNodes.add(Exp());
        }
        return new FuncRParamsNode(expNodes, commas);
    }

    private MulExpNode MulExp() {
        List<UnaryExpNode> unaryExpNodes = new ArrayList<>();
        List<Token> expressions = new ArrayList<>();
        unaryExpNodes.add(UnaryExp());
        while (tokens.get(index).getType() == LexType.MULT || tokens.get(index).getType() == LexType.DIV || tokens.get(index).getType() == LexType.MOD) {
            if (tokens.get(index).getType() == LexType.MULT) {
                expressions.add(match(LexType.MULT));
            } else if (tokens.get(index).getType() == LexType.DIV) {
                expressions.add(match(LexType.DIV));
            } else {
                expressions.add(match(LexType.MOD));
            }
            unaryExpNodes.add(UnaryExp());
        }
        return new MulExpNode(unaryExpNodes, expressions);
    }

    private AddExpNode AddExp() {
        List<MulExpNode> mulExpNodes = new ArrayList<>();
        List<Token> expressions = new ArrayList<>();
        mulExpNodes.add(MulExp());
        while (tokens.get(index).getType() == LexType.PLUS || tokens.get(index).getType() == LexType.MINU) {
            if (tokens.get(index).getType() == LexType.PLUS) {
                expressions.add(match(LexType.PLUS));
            } else if (tokens.get(index).getType() == LexType.MINU) {
                expressions.add(match(LexType.MINU));
            }
            mulExpNodes.add(MulExp());
        }
        return new AddExpNode(mulExpNodes, expressions);
    }

    private RelExpNode RelExp() {
        List<AddExpNode> addExpNodes = new ArrayList<>();
        List<Token> expressions = new ArrayList<>();
        addExpNodes.add(AddExp());
        while (tokens.get(index).getType() == LexType.LSS || tokens.get(index).getType() == LexType.GRE || tokens.get(index).getType() == LexType.LEQ || tokens.get(index).getType() == LexType.GEQ) {
            if (tokens.get(index).getType() == LexType.LSS) {
                expressions.add(match(LexType.LSS));
            } else if (tokens.get(index).getType() == LexType.GRE) {
                expressions.add(match(LexType.GRE));
            } else if (tokens.get(index).getType() == LexType.LEQ) {
                expressions.add(match(LexType.LEQ));
            } else {
                expressions.add(match(LexType.GEQ));
            }
            addExpNodes.add(AddExp());
        }
        return new RelExpNode(addExpNodes, expressions);
    }

    private EqExpNode EqExp() {
        List<RelExpNode> relExpNodes = new ArrayList<>();
        List<Token> expressions = new ArrayList<>();
        relExpNodes.add(RelExp());
        while (tokens.get(index).getType() == LexType.EQL || tokens.get(index).getType() == LexType.NEQ) {
            if (tokens.get(index).getType() == LexType.EQL) {
                expressions.add(match(LexType.EQL));
            } else {
                expressions.add(match(LexType.NEQ));
            }
            relExpNodes.add(RelExp());
        }
        return new EqExpNode(relExpNodes, expressions);
    }

    private LAndExpNode LAndExp() {
        List<EqExpNode> eqExpNodes = new ArrayList<>();
        List<Token> andTK = new ArrayList<>();
        eqExpNodes.add(EqExp());
        while (tokens.get(index).getType() == LexType.AND) {
            andTK.add(match(LexType.AND));
            eqExpNodes.add(EqExp());
        }
        return new LAndExpNode(eqExpNodes, andTK);
    }

    private LOrExpNode LOrExp() {
        List<LAndExpNode> lAndExpNodes = new ArrayList<>();
        List<Token> orTK = new ArrayList<>();
        lAndExpNodes.add(LAndExp());
        while (tokens.get(index).getType() == LexType.OR) {
            orTK.add(match(LexType.OR));
            lAndExpNodes.add(LAndExp());
        }
        return new LOrExpNode(lAndExpNodes, orTK);
    }

    private ConstExpNode ConstExp() {
        return new ConstExpNode(AddExp());
    }
}
