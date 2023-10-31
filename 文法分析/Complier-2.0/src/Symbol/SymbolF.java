package Symbol;

public abstract class SymbolF {
    private String name;

    //    private int lineNum;
    public SymbolF(String name) {
        this.name = name;
//        this.lineNum = lineNum;
    }

    public String getName() {
        return this.name;
    }
//    public int getLineNum(){return this.lineNum;}

}
