package Expressions;

import Tokenizer.ExpressionInfo;

public class PrecedenceBracketOpen extends Expression{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PrecedenceBracketOpen", -1, false, x -> x.equals("("));
    }

    @Override
    public void execute() {

    }
}
