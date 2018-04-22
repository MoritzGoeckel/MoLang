package Tokenizer;

public class Token {
    private String value;
    private ExpressionInfo type;

    public Token(ExpressionInfo type, String value){
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ExpressionInfo getType() {
        return type;
    }

    public Integer getPriority(){ return type.getPriority(); }

    @Override
    public String toString() {
        return "Token["+getType()+ ": "+ value+"]";
    }
}
