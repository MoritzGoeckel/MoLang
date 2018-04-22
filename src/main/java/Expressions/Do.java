package Expressions;

import Tokenizer.ExpressionInfo;

public class Do extends Expression{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Do", x -> x.equals("do"));
    }

    @Override
    public void execute() { } //This will not happen
}
