package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public class Seperator extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Separator", x -> x.equals(";"));
    }

}
