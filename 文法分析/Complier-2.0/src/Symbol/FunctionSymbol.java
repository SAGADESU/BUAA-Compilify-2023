package Symbol;

import java.util.List;

public class FunctionSymbol extends SymbolF {
    private String funcType; // INT 和 VOID
    private List<FuncParamSymbol> funcParamSymbols;

    public FunctionSymbol(String name, String funcType, List<FuncParamSymbol> funcParamSymbols) {
        super(name);
        this.funcType = funcType;
        this.funcParamSymbols = funcParamSymbols;
    }

    public String getFuncType() {
        return this.funcType;
    }

    public List<FuncParamSymbol> getFuncParamSymbols() {
        return funcParamSymbols;
    }
}
