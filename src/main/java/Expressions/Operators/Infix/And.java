package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;
import Util.Scope;

public class And extends Infix<Boolean> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("And", x -> x.equals("&&"), priority);
    }

    @Override
    public Boolean evaluate(Scope scope) {
        return left.evaluate(scope) && right.evaluate(scope);
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
