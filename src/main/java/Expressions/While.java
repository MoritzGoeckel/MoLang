package Expressions;

import Tokenizer.ExpressionInfo;

public class While extends Procedure{

    private RightValue<Boolean> condition;

    public While(RightValue<Boolean> condition) {
        this.condition = condition;
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("While", -1, false, x -> x.equals("while"));
    }

    @Override
    public void execute() {
        while (condition.evaluate())
            super.execute();
    }
}
