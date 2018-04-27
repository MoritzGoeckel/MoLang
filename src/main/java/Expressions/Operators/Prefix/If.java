package Expressions.Operators.Prefix;

import Tokenizer.ExpressionInfo;

public class If extends Conditional {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("If", x -> x.equals("if"), -3);
    }

    @Override
    public void execute() {
        if (condition.evaluate())
            body.execute();
    }
}
