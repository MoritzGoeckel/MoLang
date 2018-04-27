package Expressions.Operators.Prefix;

import Expressions.Expression;
import Expressions.Operators.Operator;

public abstract class Prefix<T> extends Operator<T> {
    public abstract void addOperand(Expression operand);
}
