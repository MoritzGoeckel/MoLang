package Expressions;

import Tokenizer.ExpressionInfo;

public class Or extends Operator<Boolean>{

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Or", priority, true, x -> x.equals("||"));
    }

    @Override
    public Boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
