package Expressions.Markers;

import Tokenizer.ExpressionInfo;

public class ProcedureBracketsClose extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ProcedureBracketsClose", x -> x.equals("}"));
    }

}
