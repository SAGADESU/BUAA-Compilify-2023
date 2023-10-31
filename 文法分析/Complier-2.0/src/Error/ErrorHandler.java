package Error;

import Lexer.FileOperate;
import Lexer.LexType;
import Parser.Nodes.*;
import Symbol.*;

import java.io.IOException;
import java.util.*;

// 缺少分号";",右小括号")"和右中括号"]"的错误在语法错误中处理，并进行自动补全
public class ErrorHandler {
    private static final ErrorHandler errorHandler = new ErrorHandler();

    public static int isInLoop = 0;
    private Map<String, List<Symbol>> funcParams = new HashMap<>(); // 记录所有函数的参数列表，key为函数名，value为对应的参数符号列表

    private List<ErrorUnit> errorUnits = new ArrayList<>(); // 所有错误节点

    private List<SymbolTable> symbolTableStack = new ArrayList<>();

    public void exportError() throws IOException {
        errorUnits.sort(ErrorUnit::compareTo);
        for (ErrorUnit errorUnit : errorUnits) {
            System.out.println(errorUnit.toString());
            if (true) {
                FileOperate.writeFile("error.txt", errorUnit.toString());
            }

        }
    }

    private void addSymbolInCurrentScope(String ident, Symbol symbol) {
        // 在当前函数作用域下符号表中添加新的符号
        symbolTableStack.get(symbolTableStack.size() - 1).getSymbols().put(ident, symbol);
    }

    public static ErrorHandler getInstance() {
        return errorHandler;
    }

    public boolean checkSymbolTable(String ident) {
        // 检查在当前作用域下(即栈顶)，是否已经定义过名为Ident的符号，返回True 或 False
        return symbolTableStack.get(symbolTableStack.size() - 1).getSymbols().containsKey(ident);
    }

    public boolean checkStack(String ident) {
        // 检查在所有符号表中是否已经定义过ident的符号，返回 TRUE 或 FALSE
        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
            if (symbolTableStack.get(i).getSymbols().containsKey(ident)) {
                return true;
            }
        }
        return false;
    }

    public void addError(ErrorUnit errorUnit) {
        for (ErrorUnit errorUnit1 : errorUnits) {
            if (errorUnit1.getLineNum() == errorUnit.getLineNum()) {
                return;
            }
        }
        errorUnits.add(errorUnit);
    }

    public void popSymbolTable() { // 编译完成的符号表从栈中弹出
        symbolTableStack.remove(symbolTableStack.size() - 1);
    }

    public void CompUnitErrorHandler(CompUnitNode compUnitNode) {
        SymbolTable symbolTable = new SymbolTable(new HashMap<String, Symbol>(), false, null);
        symbolTableStack.add(symbolTable);
        // CompUnit -> {Decl} {FuncDef} MainFuncDef
        for (DeclNode declNode : compUnitNode.getDeclNodes()) {
            DeclErrorHandler(declNode);
        }
        for (FuncDefNode funcDefNode : compUnitNode.getFuncDefNodes()) {
            FuncDefErrorHandler(funcDefNode);
        }
        MainFuncDefErrorHandler(compUnitNode.getMainFuncDefNode());
    }

    public void DeclErrorHandler(DeclNode declNode) {
        // Decl → ConstDecl | VarDecl
        if (declNode.getConstDeclNode() != null) {
            ConstDeclErrorHandler(declNode.getConstDeclNode());
        } else {
            VarDeclErrorHandler(declNode.getVarDeclNode());
        }
    }

    public void FuncDefErrorHandler(FuncDefNode funcDefNode) {
        // FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
        if (checkSymbolTable(funcDefNode.getIdent().getValue())) {
            // 检查当前作用域符号表发现已定义过对应符号，产生b类型错误
            ErrorHandler.getInstance().addError(new ErrorUnit(funcDefNode.getIdent().getLineNumber(), ErrorType.b));
            return;
        }
        List<Symbol> params = new ArrayList<>(); //
        if (funcDefNode.getFuncParamsNode() == null) {
            // 没有参数的函数，将符号加入符号表中
            funcParams.put(funcDefNode.getIdent().getValue(), new ArrayList<>());
            addSymbolInCurrentScope(funcDefNode.getIdent().getValue(), new Symbol(funcDefNode.getIdent().getValue(), Kind.FUNC, funcDefNode.getFuncTypeNode().getType(), 0, 0));
        } else {
            int count = 0;
            count = funcDefNode.getFuncParamsNode().getFuncFParamNodes().size();
            List<Symbol> temp = new ArrayList<>();
            for (FuncFParamNode funcFParamNode : funcDefNode.getFuncParamsNode().getFuncFParamNodes()) {
                temp.add(new Symbol(funcFParamNode.getIdent().getValue(), Kind.PARAM, Type.INT, funcFParamNode.getLeftBrackets().size(), -1));
            }
            funcParams.put(funcDefNode.getIdent().getValue(), temp); // 将函数的名称以及参数列表存入map中
            addSymbolInCurrentScope(funcDefNode.getIdent().getValue(), new Symbol(funcDefNode.getIdent().getValue(), Kind.FUNC, funcDefNode.getFuncTypeNode().getType(), 0, count));
        }
        symbolTableStack.add(new SymbolTable(new HashMap<String, Symbol>(), true, funcDefNode.getFuncTypeNode().getType()));
        if (funcDefNode.getFuncParamsNode() != null) {
            FuncFParamsErrorHandler(funcDefNode.getFuncParamsNode());
        }
        BlockErrorHandler(funcDefNode.getBlockNode());
        popSymbolTable(); // 处理完之后将当前函数从符号表栈中弹出
    }

    public void MainFuncDefErrorHandler(MainFuncDefNode mainFuncDefNode) {
        // MainFuncDef → 'int' 'main' '(' ')' Block
        addSymbolInCurrentScope("main", new Symbol("main", Kind.FUNC, Type.INT, 0, 0));
        // 将main函数加入到符号表栈中
        symbolTableStack.add(new SymbolTable(new HashMap<String, Symbol>(), true, Type.INT));
        BlockErrorHandler(mainFuncDefNode.getBlockNode());
        popSymbolTable();
    }

    public void ConstDeclErrorHandler(ConstDeclNode constDeclNode) {
        // ConstDecl → 'const' BType ConstDef { ',' ConstDef } ';'
        for (ConstDefNode constDefNode : constDeclNode.getConstDefNodes()) {
            ConstDefErrorHandler(constDefNode);
        }
    }

    public void VarDeclErrorHandler(VarDeclNode varDeclNode) {
        // VarDecl → BType VarDef { ',' VarDef } ';'
        for (VarDefNode varDefNode : varDeclNode.getVarDefNodes()) {
            VarDefErrorHandler(varDefNode);
        }
    }

    public void FuncFParamsErrorHandler(FuncFParamsNode funcFParamsNode) {
        // FuncFParams → FuncFParam { ',' FuncFParam }
        for (FuncFParamNode funcFParamNode : funcFParamsNode.getFuncFParamNodes()) {
            FuncFParamErrorHandler(funcFParamNode);
        }
    }

    public void BlockErrorHandler(BlockNode blockNode) {
        // Block → '{' { BlockItem } '}'
        for (BlockItemNode blockItemNode : blockNode.getBlockItemNodes()) {
            BlockItemErrorHandler(blockItemNode);
        }
        SymbolTable symbolTable = symbolTableStack.get(symbolTableStack.size() - 1);
        if (symbolTable.isFunc()) { // 此时的block是函数，接下来看是否需要返回值
            if (symbolTable.getType() == Type.INT) { // 为int类型的函数，则需要返回语句return
                if (blockNode.getBlockItemNodes().isEmpty()
                        || blockNode.getBlockItemNodes().get(blockNode.getBlockItemNodes().size() - 1).getStmtNode() == null
                        || blockNode.getBlockItemNodes().get(blockNode.getBlockItemNodes().size() - 1).getStmtNode().getReturnTK() == null) {
                    // 对于int类型的函数，return会且仅会出现在最后一个BlockItem中，只需要检查最后BlockItem有没有Return语句就可以了
                    ErrorHandler.getInstance().addError(new ErrorUnit(blockNode.getRightBrace().getLineNumber(), ErrorType.g));

                }

            }
        }
    }

    public void ConstDefErrorHandler(ConstDefNode constDefNode) {
        // ConstDef → Ident { '[' ConstExp ']' } '=' ConstInitVal
        if (checkSymbolTable(constDefNode.getIdent().getValue())) {
            ErrorHandler.getInstance().addError(new ErrorUnit(constDefNode.getIdent().getLineNumber(), ErrorType.b));
        }
        if (!constDefNode.getConstExpNodes().isEmpty()) {
            for (ConstExpNode constExpNode : constDefNode.getConstExpNodes()) {
                ConstExpErrorHandler(constExpNode);
            }
        }
        addSymbolInCurrentScope(constDefNode.getIdent().getValue(), new Symbol(constDefNode.getIdent().getValue(), Kind.CONST, Type.INT, constDefNode.getConstExpNodes().size(), -1));
        ConstInitValErrorHandler(constDefNode.getConstInitValNode());
    }

    public void VarDefErrorHandler(VarDefNode varDefNode) {
        // VarDef → Ident { '[' ConstExp ']' }
        // | Ident { '[' ConstExp ']' } '=' InitVal
        if (checkSymbolTable(varDefNode.getIdent().getValue())) {
            ErrorHandler.getInstance().addError(new ErrorUnit(varDefNode.getIdent().getLineNumber(), ErrorType.b));
        }
        if (!varDefNode.getConstExpNodes().isEmpty()) {
            for (ConstExpNode constExpNode : varDefNode.getConstExpNodes()) {
                ConstExpErrorHandler(constExpNode);
            }
        }
        addSymbolInCurrentScope(varDefNode.getIdent().getValue(), new Symbol(varDefNode.getIdent().getValue(), Kind.VAR, Type.INT, varDefNode.getConstExpNodes().size(), -1));
        if (varDefNode.getInitValNode() != null) {
            InitValErrorHandler(varDefNode.getInitValNode());
        }
    }

    public void FuncFParamErrorHandler(FuncFParamNode funcFParamNode) {
        // FuncFParam → BType Ident ['[' ']' { '[' ConstExp ']' }]
        if (checkSymbolTable(funcFParamNode.getIdent().getValue())) {
            ErrorHandler.getInstance().addError(new ErrorUnit(funcFParamNode.getIdent().getLineNumber(), ErrorType.b));
        }
        addSymbolInCurrentScope(funcFParamNode.getIdent().getValue(), new Symbol(funcFParamNode.getIdent().getValue(), Kind.PARAM, Type.INT, funcFParamNode.getLeftBrackets().size(), -1));
    }

    public void BlockItemErrorHandler(BlockItemNode blockItemNode) {
        // BlockItem → Decl | Stmt
        if (blockItemNode.getDeclNode() != null) {
            DeclErrorHandler(blockItemNode.getDeclNode());
        } else {
            StmtErrorHandler(blockItemNode.getStmtNode());
        }
    }

    public void ConstExpErrorHandler(ConstExpNode constExpNode) {
        // ConstExp → AddExp
        AddExpErrorHandler(constExpNode.getAddExpNode());
    }

    public void ConstInitValErrorHandler(ConstInitValNode constInitValNode) {
        // ConstInitVal → ConstExp
        // | '{' [ ConstInitVal { ',' ConstInitVal } ] '}'
        if (constInitValNode.getConstExpNode() != null) {
            ConstExpErrorHandler(constInitValNode.getConstExpNode());
        } else {
            for (ConstInitValNode constInitValNode1 : constInitValNode.getConstInitValNodes()) {
                ConstInitValErrorHandler(constInitValNode1);
            }
        }
    }

    public void InitValErrorHandler(InitValNode initValNode) {
        // InitVal → Exp | '{' [ InitVal { ',' InitVal } ] '}'
        if (initValNode.getExpNode() != null) {
            ExpErrorHandler(initValNode.getExpNode());
        } else {
            for (InitValNode initValNode1 : initValNode.getInitValNodes()) {
                InitValErrorHandler(initValNode1);
            }
        }
    }

    public void StmtErrorHandler(StmtNode stmtNode) {
        // Stmt → LVal '=' Exp ';'
        // | [Exp] ';'
        // | Block
        // | 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
        // | 'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt
        // | 'break' ';' | 'continue' ';'
        // | 'return' [Exp] ';'
        // | LVal '=' 'getint''('')'';'
        // | 'printf''('FormatString{','Exp}')'';'
        switch (stmtNode.getStmtType()) {
            case IF:
                CondErrorHandler(stmtNode.getCondNode());
                StmtErrorHandler(stmtNode.getStmtNodes().get(0));
                if (stmtNode.getElseTK() != null) {
                    StmtErrorHandler(stmtNode.getStmtNodes().get(1));
                }
                break;
            case FOR:
                if (stmtNode.getCondNode() != null) {
                    CondErrorHandler(stmtNode.getCondNode());
                }
                if (stmtNode.getForStmtNode1() != null) {
                    ForStmtErrorHandler(stmtNode.getForStmtNode1());
                }
                if (stmtNode.getForStmtNode2() != null) {
                    ForStmtErrorHandler(stmtNode.getForStmtNode2());
                }
                ErrorHandler.isInLoop++;
                StmtErrorHandler(stmtNode.getStmtNode());
                ErrorHandler.isInLoop--;
                break;
            case Block:
                symbolTableStack.add(new SymbolTable(new HashMap<String, Symbol>(), false, null));
                BlockErrorHandler(stmtNode.getBlockNode());
                popSymbolTable();
                break;
            case BREAK:
            case CONTINUE:
                if (isInLoop == 0) {
                    ErrorHandler.getInstance().addError(new ErrorUnit(stmtNode.getBreakOrContinue().getLineNumber(), ErrorType.m));
                }
                break;
            case PRINTF:
                int formatStringCnt = 0;
                for (int i = 0; i < stmtNode.getFormatString().getValue().length() - 1; i++) {
                    if (stmtNode.getFormatString().getValue().charAt(i) == '%') {
                        if (stmtNode.getFormatString().getValue().charAt(i + 1) == 'd') {
                            formatStringCnt++;
                        }
                    }
                }
                if (formatStringCnt != stmtNode.getExpNodes().size()) {
                    ErrorHandler.getInstance().addError(new ErrorUnit(stmtNode.getPrintfTK().getLineNumber(), ErrorType.l));
                }
                for (ExpNode expNode : stmtNode.getExpNodes()) {
                    ExpErrorHandler(expNode);
                }
                break;
            case RETURN:
                SymbolTable symbolTable = symbolTableStack.get(symbolTableStack.size() - 1);
                if (symbolTable.isFunc()) {
                    if (symbolTable.getType() == Type.VOID && stmtNode.getExpNode() != null) {
                        ErrorHandler.getInstance().addError(new ErrorUnit(stmtNode.getReturnTK().getLineNumber(), ErrorType.f));
                    }
                    if (stmtNode.getExpNode() != null) {
                        ExpErrorHandler(stmtNode.getExpNode());
                    }
                }
                break;
            case ExpSemicn:
                if (stmtNode.getExpNode() != null) {
                    ExpErrorHandler(stmtNode.getExpNode());
                }
                break;
            case LValEqualExp:
                LValErrorHandler(stmtNode.getlValNode());
                for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                    for (Symbol symbol : symbolTableStack.get(i).getSymbols().values()) {
                        if (symbol.getName().equals(stmtNode.getlValNode().getIdent().getValue())) {
                            if (symbol.getKind() == Kind.CONST) {
                                ErrorHandler.getInstance().addError(new ErrorUnit(stmtNode.getlValNode().getIdent().getLineNumber(), ErrorType.h));
                            }
                            break;
                        }
                    }
                }
                ExpErrorHandler(stmtNode.getExpNode());
                break;
            case LValEqualGetint:
                LValErrorHandler(stmtNode.getlValNode());
                for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                    for (Symbol symbol : symbolTableStack.get(i).getSymbols().values()) {
                        if (symbol.getName().equals(stmtNode.getlValNode().getIdent().getValue())) {
                            if (symbol.getKind() == Kind.CONST) {
                                ErrorHandler.getInstance().addError(new ErrorUnit(stmtNode.getlValNode().getIdent().getLineNumber(), ErrorType.h));
                            }
                            break;
                        }
                    }
                }
                break;
        }
    }

    public void AddExpErrorHandler(AddExpNode addExpNode) {
        // AddExp → MulExp | AddExp ('+' | '−') MulExp
        // 按照改写之后的文法来
        for (MulExpNode mulExpNode : addExpNode.getMulExpNodes()) {
            MulExpErrorHandler(mulExpNode);
        }
    }

    public void ExpErrorHandler(ExpNode expNode) {
        // Exp → AddExp
        AddExpErrorHandler(expNode.getAddExpNode());

    }

    public void MulExpErrorHandler(MulExpNode mulExpNode) {
        // MulExp → UnaryExp | MulExp ('*' | '/' | '%') UnaryExp
        // 按照改写之后的文法来
        for (UnaryExpNode unaryExpNode : mulExpNode.getUnaryExpNodes()) {
            UnaryExpErrorHandler(unaryExpNode);
        }
    }

    public Symbol getDimensionInMul(MulExpNode mulExpNode) {
        if (mulExpNode.getUnaryExpNodes().size() > 1) {
            List<Integer> dimensions = new ArrayList<>();
            for (UnaryExpNode unaryExpNode : mulExpNode.getUnaryExpNodes()) {
                Symbol symbol = getDimensionInUnary(unaryExpNode);
                if (symbol == null) {
                    dimensions.add(-1);
                } else {
                    if (symbol.getName() == null) {
                        dimensions.add(symbol.getDimension());
                    } else {
                        boolean flag = false;
                        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                            for (Symbol symbol1 : symbolTableStack.get(i).getSymbols().values()) {
                                if (symbol1.getName().equals(symbol.getName())) {
                                    if (symbol1.getKind() == Kind.VAR || symbol1.getKind() == Kind.CONST || symbol1.getKind() == Kind.PARAM) {
                                        dimensions.add(symbol1.getDimension() - symbol.getDimension());
                                    } else if (symbol1.getKind() == Kind.FUNC) {
                                        dimensions.add(symbol1.getType() == Type.VOID ? -1 : 0);
                                    }
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                break;
                            }
                        }
                    }
                }
            }
            boolean flag = false;
            for (int i = 0; i < dimensions.size() - 1; i++) {
                if (dimensions.get(i) != dimensions.get(i + 1)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return new Symbol(null, null, null, -1, -1);
            } else {
                return new Symbol(null, null, null, dimensions.get(0), -1);
            }

        } else {
            System.out.println("Mul->Unary");
            return getDimensionInUnary(mulExpNode.getUnaryExpNodes().get(0));
        }
    }

    public Symbol getDimensionInUnary(UnaryExpNode unaryExpNode) {
        if (unaryExpNode.getPrimaryExpNode() != null) {
            System.out.println("PrimaryExp");
            return getDimensionInPrimary(unaryExpNode.getPrimaryExpNode());
        } else if (unaryExpNode.getIdent() != null) {
            // 存在函数调用，判断是否是调用的合法函数，如果是合法的调用函数，则返回维数0，否则返回-1
//            for (SymbolTable symbolTable : symbolTableStack) {
//                for (Symbol symbol : symbolTable.getSymbols().values()) {
//                    if (symbol.getName().equals(unaryExpNode.getIdent().getValue())) {
//                        if (symbol.getType() == Type.VOID) {
//                            return new Symbol(symbol.getName(), null, null, -1, -1);
//                        } else {
//                            return new Symbol(symbol.getName(), null, null, 0, -1);
//                        }
//                    }
//                }
//            }
            for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                for (Symbol symbol : symbolTableStack.get(i).getSymbols().values()) {
                    if (symbol.getName().equals(unaryExpNode.getIdent().getValue())) {
                        if (symbol.getKind() == Kind.FUNC) {
//                            if (symbol.getType() == Type.INT) {
//                                return new Symbol(unaryExpNode.getIdent().getValue(), null, null, 0, -1);
//                            } else {
//                                return new Symbol(unaryExpNode.getIdent().getValue(), null, null, -1, -1);
//                            }
                            return new Symbol(unaryExpNode.getIdent().getValue(), null, null, 0, -1);

                        }
                    }
                }
            }
            return null;
        } else {
            return getDimensionInUnary(unaryExpNode.getUnaryExpNode());
        }
    }

    public Symbol getDimensionInPrimary(PrimaryExpNode primaryExpNode) {
        if (primaryExpNode.getExpNode() != null) {
            System.out.println("Pri->Exp");
            return getDimensionInExp(primaryExpNode.getExpNode());
        } else if (primaryExpNode.getlValNode() != null) {
            System.out.println("Pri->LVal");
            return getDimensionInLVal(primaryExpNode.getlValNode());
        } else {
            // number为0维
            return new Symbol(null, null, null, 0, -1);
        }

    }

    public Symbol getDimensionInExp(ExpNode expNode) {
        return getDimensionInAdd(expNode.getAddExpNode());
    }

    public Symbol getDimensionInLVal(LValNode lValNode) {
        System.out.println(lValNode.getExpNodes().size() + "@ @");
        // exp的数量即对应变量、一维、二维数组
        return new Symbol(lValNode.getIdent().getValue(), null, null, lValNode.getExpNodes().size(), -1);
    }

    public Symbol getDimensionInAdd(AddExpNode addExpNode) {
        if (addExpNode.getMulExpNodes().size() > 1) {
            List<Integer> dimensions = new ArrayList<>();
            for (MulExpNode mulExpNode : addExpNode.getMulExpNodes()) {
                Symbol symbol = getDimensionInMul(mulExpNode);
                if (symbol == null) {
                    dimensions.add(-1);
                } else {
                    if (symbol.getName() == null) {
                        dimensions.add(symbol.getDimension());
                    } else {
                        boolean flag = false;
                        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                            for (Symbol symbol1 : symbolTableStack.get(i).getSymbols().values()) {
                                if (symbol1.getName().equals(symbol.getName())) {
                                    if (symbol1.getKind() == Kind.VAR || symbol1.getKind() == Kind.CONST || symbol1.getKind() == Kind.PARAM) {
                                        dimensions.add(symbol1.getDimension() - symbol.getDimension());
                                    } else if (symbol1.getKind() == Kind.FUNC) {
                                        dimensions.add(symbol1.getType() == Type.VOID ? -1 : 0);
                                    }
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                break;
                            }
                        }
                    }
                }
            }
            boolean flag = false;
            for (int i = 0; i < dimensions.size() - 1; i++) {
                if (dimensions.get(i) != dimensions.get(i + 1)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return new Symbol(null, null, null, -1, -1);
            } else {
                return new Symbol(null, null, null, dimensions.get(0), -1);
            }

        } else {
            System.out.println("Add->Mul");
            return getDimensionInMul(addExpNode.getMulExpNodes().get(0));
        }
    }

    public void UnaryExpErrorHandler(UnaryExpNode unaryExpNode) {
        // UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')'
        // | UnaryOp UnaryExp
        if (unaryExpNode.getPrimaryExpNode() != null) {
            PrimaryExpErrorHandler(unaryExpNode.getPrimaryExpNode());
        } else if (unaryExpNode.getUnaryOpNode() != null) {
            UnaryExpErrorHandler(unaryExpNode.getUnaryExpNode());
        } else {
            if (!checkStack(unaryExpNode.getIdent().getValue())) { // 使用了未定义的变量
                ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.c));
                return;
            }
            Symbol symbol = null;
            for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                boolean flag = false;
                for (Symbol symbol1 : symbolTableStack.get(i).getSymbols().values()) {
                    if (symbol1.getName().equals(unaryExpNode.getIdent().getValue())) {
                        symbol = symbol1;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            if (symbol.getKind() != Kind.FUNC) {
                ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.e));
                return;
            }
            List<Symbol> paramSymbols = funcParams.get(unaryExpNode.getIdent().getValue());
            int paramsCnt = -1;
            paramsCnt = paramSymbols.size();
            System.out.println(unaryExpNode.getIdent().getValue());
            System.out.println(paramsCnt);
            // 函数参数个数不匹配
            if (unaryExpNode.getFuncRParamsNode() == null) {
                if (paramsCnt != 0) {
                    ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.d));
//                    ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.e));
                }
            } else {
                System.out.println(unaryExpNode.getFuncRParamsNode().getExpNodes().size() + "@");
                if (paramsCnt != unaryExpNode.getFuncRParamsNode().getExpNodes().size()) {
                    ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.d));
                    return;
//                    ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.e));
                }
                List<Integer> funcFParamsDimensions = new ArrayList<>();
                for (Symbol symbol1 : paramSymbols) {
                    funcFParamsDimensions.add(symbol1.getDimension());
                }
                List<Integer> funcRParamsDimension = new ArrayList<>();
                FuncRParamsErrorHandler(unaryExpNode.getFuncRParamsNode());

                for (ExpNode expNode : unaryExpNode.getFuncRParamsNode().getExpNodes()) {
                    Symbol symbol1 = getDimensionInExp(expNode);
                    if (symbol1 != null) {
                        if (symbol1.getName() == null) {
                            funcRParamsDimension.add(symbol1.getDimension());
                        } else {
                            boolean flag = false;
                            for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
                                for (Symbol symbol2 : symbolTableStack.get(i).getSymbols().values()) {
                                    if (symbol2.getName().equals(symbol1.getName())) {
                                        if (symbol2.getKind() == Kind.VAR || symbol2.getKind() == Kind.CONST || symbol2.getKind() == Kind.PARAM) {
                                            funcRParamsDimension.add(symbol2.getDimension() - symbol1.getDimension());
                                        } else if (symbol2.getKind() == Kind.FUNC) {
                                            funcRParamsDimension.add(symbol2.getType() == Type.VOID ? -1 : 0);
                                        }
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) {
                                    break;
                                }
                            }
                        }
                    }
                }
//                boolean flag = false;
//                for (int i = 0; i < funcRParamsDimension.size(); i++) {
//                    System.out.println(funcRParamsDimension.get(i) + "@@");
//                    System.out.println(funcFParamsDimensions.get(i)+ "@@@");
//                    if (funcRParamsDimension.get(i) != funcFParamsDimensions.get(i)) {
//                        flag = true;
//                        break;
//                    }
//                }
                if (!Objects.equals(funcRParamsDimension, funcFParamsDimensions)) {
                    ErrorHandler.getInstance().addError(new ErrorUnit(unaryExpNode.getIdent().getLineNumber(), ErrorType.e));
                }
            }
        }
    }

    public void PrimaryExpErrorHandler(PrimaryExpNode primaryExpNode) {
        // PrimaryExp → '(' Exp ')' | LVal | Number
        if (primaryExpNode.getExpNode() != null) {
            ExpErrorHandler(primaryExpNode.getExpNode());
        } else if (primaryExpNode.getlValNode() != null) {
            LValErrorHandler(primaryExpNode.getlValNode());
        }
    }

    public void LValErrorHandler(LValNode lValNode) {
        // LVal → Ident {'[' Exp ']'}
        if (!checkStack(lValNode.getIdent().getValue())) {
            ErrorHandler.getInstance().addError(new ErrorUnit(lValNode.getIdent().getLineNumber(), ErrorType.c));
            return;
        }
        for (ExpNode expNode : lValNode.getExpNodes()) {
            ExpErrorHandler(expNode);
        }
    }

    public void FuncRParamsErrorHandler(FuncRParamsNode funcRParamsNode) {
        // FuncRParams → Exp { ',' Exp }
        for (ExpNode expNode : funcRParamsNode.getExpNodes()) {
            ExpErrorHandler(expNode);
        }
    }

    public void CondErrorHandler(CondNode condNode) {
        // Cond → LOrExp
        LOrExpErrorHandler(condNode.getlOrExpNode());
    }

    public void LOrExpErrorHandler(LOrExpNode lOrExpNode) {
        // LOrExp → LAndExp | LOrExp '||' LAndExp
        for (LAndExpNode lAndExpNode : lOrExpNode.getlAndExpNodes()) {
            LAndExpErrorHandler(lAndExpNode);
        }
    }

    public void ForStmtErrorHandler(ForStmtNode forStmtNode) {
        // ForStmt → LVal '=' Exp
        LValErrorHandler(forStmtNode.getlValNode());
        for (int i = symbolTableStack.size() - 1; i >= 0; i--) {
            boolean flag = false;
            for (Symbol symbol : symbolTableStack.get(i).getSymbols().values()) {
                if (symbol.getName().equals(forStmtNode.getlValNode().getIdent().getValue())) {
                    if (symbol.getKind() == Kind.CONST) {
                        ErrorHandler.getInstance().addError(new ErrorUnit(forStmtNode.getlValNode().getIdent().getLineNumber(), ErrorType.h));
                    }
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        ExpErrorHandler(forStmtNode.getExpNode());
    }

    public void LAndExpErrorHandler(LAndExpNode lAndExpNode) {
        // LAndExp → EqExp | LAndExp '&&' EqExp
        for (EqExpNode eqExpNode : lAndExpNode.getEqExpNodes()) {
            EqExpErrorHandler(eqExpNode);
        }
    }

    public void EqExpErrorHandler(EqExpNode eqExpNode) {
        // EqExp → RelExp | EqExp ('==' | '!=') RelExp
        for (RelExpNode relExpNode : eqExpNode.getRelExpNodes()) {
            RelExpErrorHandler(relExpNode);
        }
    }

    public void RelExpErrorHandler(RelExpNode relExpNode) {
        // RelExp → AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp
        for (AddExpNode addExpNode : relExpNode.getAddExpNodes()) {
            AddExpErrorHandler(addExpNode);
        }
    }
}
