package Expressions;

import Tokenizer.ExpressionInfo;

public class Plus extends Operator<Integer>{

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Plus", priority, true, x -> x.equals("+"));
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
