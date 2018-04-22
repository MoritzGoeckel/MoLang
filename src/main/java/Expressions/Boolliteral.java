package Expressions;

import Tokenizer.ExpressionInfo;

public class Boolliteral extends RightValue<Boolean> {

    private Boolean value;

    public Boolliteral(String value){
        this.value = value.equals("true");
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Boolliteral", 0, false, x -> x.equals("true") || x.equals("false"));
    }

    @Override
    public Boolean evaluate() {
        return value;
    }
}
