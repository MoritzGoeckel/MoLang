package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class ProcedureBracketsClose extends Expression {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ProcedureBracketsClose", x -> x.equals("}"));
    }

    @Override
    public void execute() { } //This will not happen
}
