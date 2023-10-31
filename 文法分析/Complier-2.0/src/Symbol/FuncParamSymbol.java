package Symbol;

public class FuncParamSymbol extends SymbolF {
    private int dimension;
    public FuncParamSymbol(String name, int dimension){
        super(name);
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }
}
