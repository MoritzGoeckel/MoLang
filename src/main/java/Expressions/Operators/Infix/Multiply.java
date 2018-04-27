package Expressions.Operators.Infix;

import Tokenizer.ExpressionInfo;

public class Multiply extends Infix<Integer> {

    private static int priority = 2;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Multiply", x -> x.equals("*"), priority);
    }

    @Override
    public Integer evaluate() {
        return left.evaluate() * right.evaluate();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
