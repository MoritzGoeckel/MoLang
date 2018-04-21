package Expressions;

import Tokenizer.ExpressionInfo;

public class Numliteral extends RightValue<Integer> {

    private Integer value;

    public Numliteral(String value){
        this.value = Integer.parseInt(value);
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Numliteral", 0, false, x -> x.matches("[0-9]+"));
    }

    @Override
    public Integer evaluate() {
        return value;
    }
}
