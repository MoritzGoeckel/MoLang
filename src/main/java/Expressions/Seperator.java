package Expressions;

import Tokenizer.ExpressionInfo;

public class Seperator extends Expression{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Separator", -1, false, x -> x.equals(";"));
    }

    @Override
    public void execute() {

    }
}
