package Expressions.Markers;

import Tokenizer.ExpressionInfo;

public class Seperator extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Separator", x -> x.equals(";"));
    }

}
