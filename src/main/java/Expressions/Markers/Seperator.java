package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class Seperator extends Expression {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Separator", x -> x.equals(";"));
    }

    @Override
    public void execute() { } //This will not happen
}
