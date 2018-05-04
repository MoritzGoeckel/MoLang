package Expressions.Operators.Prefix;

import Tokenizer.ExpressionInfo;
import Util.Scope;

public class If extends Conditional {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("If", x -> x.equals("if"), -3);
    }

    @Override
    public Object evaluate(Scope scope) {
        if (condition.evaluate(scope)) {
            Object output = body.evaluate(scope);
            if(output != null)
                scope.setReturnValue(output);
        }

        return null;
    }
}
