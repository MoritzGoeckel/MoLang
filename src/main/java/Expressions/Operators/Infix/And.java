package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;

public class And extends Infix<Boolean> {

    private static int priority = 1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("And", x -> x.equals("&&"), priority);
    }

    @Override
    public Boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
