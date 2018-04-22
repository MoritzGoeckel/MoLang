package Expressions;

import Tokenizer.ExpressionInfo;

public class Equal<T> extends Operator<T>{

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Equal", x -> x.equals("=="), priority, true);
    }

    @Override
    public Boolean evaluate() {
        return left.evaluate().equals(right.evaluate());
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
