package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;

public class Plus extends Infix<Integer> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Plus", x -> x.equals("+"), priority);
    }

    @Override
    public Integer evaluate() {
        return left.evaluate() + right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
