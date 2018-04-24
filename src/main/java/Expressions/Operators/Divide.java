package Expressions.Operators;

import Tokenizer.ExpressionInfo;

public class Divide extends Operator<Integer> {

    private static int priority = 2;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Divide", x -> x.equals("/"), priority, true);
    }

    @Override
    public Integer evaluate() {
        return left.evaluate() / right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}