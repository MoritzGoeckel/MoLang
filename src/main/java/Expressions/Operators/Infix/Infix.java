package Expressions.Operators.Infix;

import Expressions.Operators.Operator;
import Expressions.RightValue;

public abstract class Infix<T> extends Operator{
    protected RightValue<T> left, right;

    public void setOperands(RightValue<T> left, RightValue<T> right){
        this.left = left;
        this.right = right;
    }

    public boolean isComplete(){
        return left != null && right != null;
    }
}
