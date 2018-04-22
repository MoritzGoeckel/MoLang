package Expressions;

import Tokenizer.ExpressionInfo;

public class Then extends Expression{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Then", -1, false, x -> x.equals("then"));
    }

    @Override
    public void execute() { } //This will not happen
}
