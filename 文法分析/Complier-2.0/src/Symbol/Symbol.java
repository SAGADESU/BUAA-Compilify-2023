package Symbol;

import java.util.List;

public class Symbol {
    private String name;
    private Kind kind;
    private Type type;
    private int dimension; // 维数 0 1维; 1 2维

    private int paramCnt; // 参数个数，只在有参数的函数中使用，变量设置为-1，函数设置为函数的参数个数


    public Symbol(String name, Kind kind, Type type, int dimension, int paramCnt) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.dimension = dimension;
        this.paramCnt = paramCnt;
    }

    public boolean isConst() {
        return kind == Kind.CONST;
    }

    public int getDimension() {
        return dimension;
    }

    public Kind getKind() {
        return kind;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getParamCnt() {
        return paramCnt;
    }
}
