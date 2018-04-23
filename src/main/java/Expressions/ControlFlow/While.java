package Expressions.ControlFlow;

import Expressions.Expression;
import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class While extends Conditional{

    public While() {

    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("While", x -> x.equals("while"), -1, false, true);
    }

    @Override
    public void execute() {
        while (condition.evaluate())
            body.execute();
    }
}
