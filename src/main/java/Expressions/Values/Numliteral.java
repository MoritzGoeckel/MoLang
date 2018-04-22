package Expressions.Values;

import Expressions.RightValue;
import Tokenizer.ExpressionInfo;

public class Numliteral extends RightValue<Integer> {

    private Integer value;

    public Numliteral(String value){
        this.value = Integer.parseInt(value);
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Numliteral", x -> x.matches("[0-9]+"));
    }

    @Override
    public Integer evaluate() {
        return value;
    }
}
