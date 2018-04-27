package Expressions.Annotations;

import Expressions.Expression;
import Expressions.Values.Identifier;
import Tokenizer.ExpressionInfo;

public class Local extends Annotation {

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Local", x -> x.equals("local"));
    }

    @Override
    public Expression Process(Expression expression) {
        ((Identifier)expression).makeLocal();
        return expression;
    }

}
