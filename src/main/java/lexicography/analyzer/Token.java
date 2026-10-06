package lexicography.analyzer;

public class Token {
    private String value;
    private LexemeClass classType;

    public Token(LexemeClass lexemeClass, String value) {
        this.value = value;
        this.classType = lexemeClass;
    }

    public LexemeClass getClassType() {
        return classType;
    }

    public void setClassType(LexemeClass classType) {
        this.classType = classType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%-20s [%s]", value, classType);
    }
}
