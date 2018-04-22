package Expressions;

import Tokenizer.ExpressionInfo;

public class Assignment<T> extends Operator{

    private static int priority = -1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Assignment", x -> x.equals("="), priority, true, false);
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
