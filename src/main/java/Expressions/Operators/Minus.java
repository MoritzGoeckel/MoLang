package Expressions.Operators;

import Tokenizer.ExpressionInfo;

public class Minus extends Operator<Integer> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Minus", x -> x.equals("-"), priority, true);
    }

    @Override
    public Integer evaluate() {
        return left.evaluate() - right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
