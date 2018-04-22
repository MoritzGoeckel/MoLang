package Expressions;

import Tokenizer.ExpressionInfo;

public class End extends Expression{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("End", x -> x.equals("end"));
    }

    @Override
    public void execute() { } //This will not happen
}
