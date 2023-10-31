package Error;

public class ErrorUnit implements Comparable<ErrorUnit> {
    private int lineNum; // 行号，用于输出与排序
    private ErrorType errorType; // 错误类型

    public ErrorUnit(int lineNum, ErrorType errorType) {
        this.lineNum = lineNum;
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int compareTo(ErrorUnit errorUnit) {
        if (errorUnit.lineNum == lineNum) {
            return 0;
        } else if (lineNum < errorUnit.lineNum) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return lineNum + " " + errorType.toString() + "\n";
    }
}
