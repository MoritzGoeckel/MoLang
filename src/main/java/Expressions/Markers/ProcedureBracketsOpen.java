package Expressions.Markers;

import Tokenizer.ExpressionInfo;

public class ProcedureBracketsOpen extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ProcedureBracketsOpen", x -> x.equals("{"));
    }

}
