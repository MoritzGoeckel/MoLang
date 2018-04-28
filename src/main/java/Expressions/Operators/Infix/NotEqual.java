package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;
import Util.Scope;

public class NotEqual<T> extends Infix<T> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("NotEqual", x -> x.equals("!="), priority);
    }

    @Override
    public Boolean evaluate(Scope scope) {
        return !left.evaluate(scope).equals(right.evaluate(scope));
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
