package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class PrecedenceBracketOpen extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PrecedenceBracketOpen", x -> x.equals("("));
    }

}
