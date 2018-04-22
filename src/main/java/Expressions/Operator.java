package Expressions;

public abstract class Operator<T> extends RightValue {
    RightValue<T> left, right;

    public void assign(RightValue<T> left, RightValue<T> right){
        this.left = left;
        this.right = right;
    }

    public boolean isComplete(){
        return left != null && right != null;
    }

    public abstract int getPriority();
}
