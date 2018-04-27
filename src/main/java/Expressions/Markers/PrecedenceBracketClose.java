package Expressions.Markers;

import Tokenizer.ExpressionInfo;

public class PrecedenceBracketClose extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PrecedenceBracketClose", x -> x.equals(")"));
    }

}
