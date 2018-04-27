package Expressions.Markers;

import Tokenizer.ExpressionInfo;

public class PrecedenceBracketOpen extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PrecedenceBracketOpen", x -> x.equals("("));
    }

}
