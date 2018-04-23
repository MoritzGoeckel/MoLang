package Expressions.ControlFlow;

import Expressions.RightValue;
import Tokenizer.ExpressionInfo;

public class If extends Conditional{

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("If", x -> x.equals("if"), -1, false, true);
    }

    @Override
    public void execute() {
        if (condition.evaluate())
            body.execute();
    }
}
