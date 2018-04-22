package Expressions;

import Tokenizer.ExpressionInfo;

public class PrecedenceBracketClose extends Expression{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PrecedenceBracketClose", x -> x.equals(")"));
    }

    @Override
    public void execute() {

    }
}
