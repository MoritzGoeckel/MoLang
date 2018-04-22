package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class PrecedenceBracketOpen extends Expression {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PrecedenceBracketOpen", x -> x.equals("("));
    }

    @Override
    public void execute() {

    }
}
