package Parser;

import Parser.Nodes.NodeType;

import java.util.HashMap;

public class NodeMap {
    private HashMap<NodeType,String> nodeMap;
    public NodeMap()
    {
        nodeMap = new HashMap<>();
        nodeMap.put(NodeType.CompUnit,"<CompUnit>");
        nodeMap.put(NodeType.Decl,"<Decl>");
        nodeMap.put(NodeType.ConstDecl,"<ConstDecl>");
        nodeMap.put(NodeType.BType,"<BType>");
        nodeMap.put(NodeType.ConstDef,"<ConstDef>");
        nodeMap.put(NodeType.ConstInitVal,"<ConstInitVal>");
        nodeMap.put(NodeType.VarDecl,"<VarDecl>");
        nodeMap.put(NodeType.VarDef,"<VarDef>");
        nodeMap.put(NodeType.InitVal,"<InitVal>");
        nodeMap.put(NodeType.FuncDef,"<FuncDef>");
        nodeMap.put(NodeType.MainFuncDef,"<MainFuncDef>");
        nodeMap.put(NodeType.FuncType,"<FuncType>");
        nodeMap.put(NodeType.FuncFParams,"<FuncFParams>");
        nodeMap.put(NodeType.FuncFParam,"<FuncFParam>");
        nodeMap.put(NodeType.Block,"<Block>");
        nodeMap.put(NodeType.BlockItem,"<BlockItem>");
        nodeMap.put(NodeType.Stmt,"<Stmt>");
        nodeMap.put(NodeType.ForStmt,"<ForStmt>");
        nodeMap.put(NodeType.Exp,"<Exp>");
        nodeMap.put(NodeType.Cond,"<Cond>");
        nodeMap.put(NodeType.LVal,"<LVal>");
        nodeMap.put(NodeType.PrimaryExp,"<PrimaryExp>");
        nodeMap.put(NodeType.Number,"<Number>");
        nodeMap.put(NodeType.UnaryExp,"<UnaryExp>");
        nodeMap.put(NodeType.UnaryOp,"<UnaryOp>");
        nodeMap.put(NodeType.FuncRParams,"<FuncRParams>");
        nodeMap.put(NodeType.MulExp,"<MulExp>");
        nodeMap.put(NodeType.AddExp,"<AddExp>");
        nodeMap.put(NodeType.RelExp,"<RelExp>");
        nodeMap.put(NodeType.EqExp,"<EqExp>");
        nodeMap.put(NodeType.LAndExp,"<LAndExp>");
        nodeMap.put(NodeType.LOrExp,"<LOrExp>");
        nodeMap.put(NodeType.ConstExp,"<ConstExp>");
    }
    public String getNode(NodeType nodeType)
    {
        return nodeMap.get(nodeType);
    }
}
