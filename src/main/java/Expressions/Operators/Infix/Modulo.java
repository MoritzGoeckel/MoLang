package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Modulo extends Infix<Integer> {

    private static int priority = 2;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Modulo", x -> x.equals("%"), priority);
    }

    @Override
    public Integer evaluate(Scope scope) {
        return left.evaluate(scope) % right.evaluate(scope);
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
