package Expressions.Operators;

import Expressions.RightValue;

public abstract class Operator<T> extends RightValue<T> {
    public abstract boolean isComplete();
    public abstract int getPriority();
}
