package Expressions;

import Tokenizer.ExpressionInfo;

public class Assignment<T> extends Operator{

    private static int priority = 0;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Assignment", priority, true, x -> x.equals("="));
    }

    @Override
    public T evaluate() {
        T value = ((RightValue<T>)right).evaluate();
        ((Identifier)left).assign(value);
        return value;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
