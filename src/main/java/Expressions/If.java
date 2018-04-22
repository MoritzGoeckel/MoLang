package Expressions;

import Tokenizer.ExpressionInfo;

import java.util.List;

public class If extends Procedure{

    private RightValue<Boolean> condition;

    public If(RightValue<Boolean> condition) {
        this.condition = condition;
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("If", x -> x.equals("if"), -1, false, true);
    }

    @Override
    public void execute() {
        if(condition.evaluate())
            super.execute();
    }
}
