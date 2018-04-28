package Expressions.Operators.Prefix;

import Tokenizer.ExpressionInfo;
import Util.Scope;

public class While extends Conditional {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("While", x -> x.equals("while"), -3);
    }

    @Override
    public Object evaluate(Scope scope) {
        while (condition.evaluate(scope))
            body.execute(scope);

        return null;
    }
}
