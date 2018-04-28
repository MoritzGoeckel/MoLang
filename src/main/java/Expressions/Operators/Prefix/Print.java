package Expressions.Operators.Prefix;

import Expressions.Expression;
import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Print extends Prefix {

    private static int priority = -3;
    private RightValue operand;

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("Print", x -> x.equals("print"), priority);
    }

    @Override
    public Object evaluate(Scope scope) {
        Object value =  operand.evaluate(scope);
        System.out.println(value);
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
