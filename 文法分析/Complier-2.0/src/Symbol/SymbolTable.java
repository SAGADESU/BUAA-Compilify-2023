package Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private HashMap<String, Symbol> symbols;
    private boolean isFunc; // 是否在函数中

    private Type type; // 若在函数中则值为对应函数的返回值类型,不在函数中则为null

    public SymbolTable(HashMap<String, Symbol> symbols, boolean isFunc, Type type) {
        this.symbols = symbols;
        this.isFunc = isFunc;
        this.type = type;
    }

    public HashMap<String, Symbol> getSymbols() {
        return symbols;
    }

    public boolean isFunc() {
        return isFunc;
    }

    public Type getType() {
        return type;
    }
}
