package Expressions.Operators;

import Tokenizer.ExpressionInfo;

public class More extends Operator<Integer> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("More", x -> x.equals(">"), priority, true);
    }

    @Override
    public Boolean evaluate() {
        return left.evaluate() > right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
