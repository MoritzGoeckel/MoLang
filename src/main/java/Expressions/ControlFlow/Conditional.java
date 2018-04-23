package Expressions.ControlFlow;

import Expressions.Expression;
import Expressions.RightValue;

public abstract class Conditional extends Expression {

    RightValue<Boolean> condition;
    Expression body;

    public void assignConditionAndBody(RightValue<Boolean> condition, Expression body){
        this.body = body;
        this.condition = condition;
    }

}
