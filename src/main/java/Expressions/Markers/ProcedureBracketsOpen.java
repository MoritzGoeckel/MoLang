package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class ProcedureBracketsOpen extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ProcedureBracketsOpen", x -> x.equals("{"));
    }

}
