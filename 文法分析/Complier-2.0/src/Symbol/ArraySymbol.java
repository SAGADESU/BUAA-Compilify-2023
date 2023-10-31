package Symbol;

public class ArraySymbol extends SymbolF {
    private boolean isConst; // 是否是常量
    private int dimension; // 维数 0是1维，1是二维

    public ArraySymbol(String name, boolean isConst, int dimension) {
        super(name);
        this.isConst = isConst;
        this.dimension = dimension;
    }

    public boolean isConst() {
        return this.isConst;
    }

    public int getDimension() {
        return this.dimension;
    }
}
