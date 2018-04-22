package Expressions;

import Tokenizer.ExpressionInfo;

public class Less extends Operator<Integer>{

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Less", priority, true, x -> x.equals("<"));
    }

    @Override
    public Boolean evaluate() {
        return left.evaluate() < right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
