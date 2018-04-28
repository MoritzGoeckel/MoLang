package Expressions.Operators.Infix;

import Expressions.Procedure;
import Expressions.Values.Identifier;
import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Assignment<T> extends Infix<T> {

    private static int priority = -1;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Assignment", x -> x.equals("="), priority);
    }

    @Override
    public T evaluate(Scope scope) {

        T value = null;

        //Lazy evaluation
        if(right.getClass() == Procedure.class)
            value = (T)right;
        else
            value = ((RightValue<T>)right).evaluate(scope);

        ((Identifier)left).assign(value, scope);
        return value;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
