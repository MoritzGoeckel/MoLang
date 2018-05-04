package Expressions.Operators.Prefix;

import Expressions.Expression;
import Expressions.Procedure;
import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class ImplicitReturn extends Prefix {

    private static int priority = -3;
    private RightValue operand;

    @Override
    public Object evaluate(Scope scope) {
        Object value = null;

        //Lazy evaluation
        if(operand.getClass() == Procedure.class)
            value = operand;
        else
            value = operand.evaluate(scope);

        scope.setReturnValue(value);
        //Does not trigger "stop evaluating" on scope

        return value;
    }

    @Override
    public boolean isComplete() {
        return this.operand != null;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void addOperand(Expression operand) {
        if(this.operand == null)
            this.operand = (RightValue) operand;
        else
            throw new RuntimeException("Operand already set");
    }
}
