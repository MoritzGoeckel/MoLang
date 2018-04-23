package Expressions.ControlFlow;

import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class While extends Procedure{

    private RightValue<Boolean> condition;

    public While(RightValue<Boolean> condition, Scope parentScope) {
        super(parentScope);
        this.condition = condition;
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("While", x -> x.equals("while"), -1, false, true);
    }

    @Override
    public Object evaluate() {
        while (condition.evaluate())
            super.evaluate();

        return null;
    }
}
