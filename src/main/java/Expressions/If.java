package Expressions;

import Tokenizer.ExpressionInfo;

import java.util.List;

public class If extends Procedure{

    private RightValue<Boolean> condition;

    public If(RightValue<Boolean> condition) {
        this.condition = condition;
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("If", -1, false, x -> x.equals("if"));
    }

    @Override
    public void execute() {
        if(condition.evaluate())
            super.execute();
    }
}
