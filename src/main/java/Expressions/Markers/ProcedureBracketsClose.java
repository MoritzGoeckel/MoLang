package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class ProcedureBracketsClose extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ProcedureBracketsClose", x -> x.equals("}"));
    }

}
