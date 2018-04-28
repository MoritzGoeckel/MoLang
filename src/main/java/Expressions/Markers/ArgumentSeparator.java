package Expressions.Markers;

import Tokenizer.ExpressionInfo;

public class ArgumentSeparator extends Marker {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ArgumentSeparator", x -> x.equals(","));
    }

}
