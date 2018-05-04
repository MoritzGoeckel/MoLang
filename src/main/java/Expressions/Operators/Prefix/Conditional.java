package Expressions.Operators.Prefix;

import Expressions.Expression;
import Expressions.Procedure;
import Expressions.RightValue;

public abstract class Conditional extends Prefix {

    RightValue<Boolean> condition = null;
    Procedure body = null;

    @Override
    public void addOperand(Expression operand) {
        if(condition == null)
            condition = (RightValue<Boolean>)operand;
        else if(body == null) {
            body = (Procedure) operand;
            body.makeSecondOrderProcedure();
        }
        else
            throw new RuntimeException("Condition and body already set!");
    }

    @Override
    public boolean isComplete() {
        return condition != null && body != null;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
