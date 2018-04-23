package Expressions.ControlFlow;

import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class If extends Procedure{

    private RightValue<Boolean> condition;

    public If(RightValue<Boolean> condition, Scope parentScope) {
        super(parentScope);
        this.condition = condition;
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("If", x -> x.equals("if"), -1, false, true);
    }

    @Override
    public Object evaluate() {
        if (condition.evaluate())
            super.evaluate();

        return null;
    }
}
