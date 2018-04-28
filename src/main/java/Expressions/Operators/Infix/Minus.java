package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Minus extends Infix<Integer> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Minus", x -> x.equals("-"), priority);
    }

    @Override
    public Integer evaluate(Scope scope) {
        return left.evaluate(scope) - right.evaluate(scope);
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
