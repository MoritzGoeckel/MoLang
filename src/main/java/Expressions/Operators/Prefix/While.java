package Expressions.Operators.Prefix;

import Tokenizer.ExpressionInfo;

public class While extends Conditional {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("While", x -> x.equals("while"), -3);
    }

    @Override
    public Object evaluate() {
        while (condition.evaluate())
            body.execute();

        return null;
    }
}
