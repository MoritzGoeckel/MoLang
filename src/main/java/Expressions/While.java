package Expressions;

import Tokenizer.ExpressionInfo;

public class While extends Procedure{

    private RightValue<Boolean> condition;

    public While(RightValue<Boolean> condition) {
        this.condition = condition;
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("While", x -> x.equals("while"), -1, false, true);
    }

    @Override
    public void execute() {
        while (condition.evaluate())
            super.execute();
    }
}
