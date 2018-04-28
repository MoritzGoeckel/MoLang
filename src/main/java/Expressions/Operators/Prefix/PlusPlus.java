package Expressions.Operators.Prefix;

import Expressions.Expression;
import Expressions.LeftValue;
import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class PlusPlus extends Prefix<Integer> {

    private static int priority = -3;
    private LeftValue<Integer> operand;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("PlusPlus", x -> x.equals("++"), priority);
    }

    @Override
    public Integer evaluate(Scope scope) {
        Integer oldValue = operand.evaluate(scope);
        operand.assign(oldValue + 1, scope);
        return oldValue;
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
            this.operand = (LeftValue<Integer>)operand;
        else
            throw new RuntimeException("Operand already set");
    }
}
