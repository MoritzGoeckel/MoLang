package Expressions;

import Util.Scope;

public abstract class RightValue<T> extends Expression{
    public abstract T evaluate(Scope scope);

    @Override
    public void execute(Scope scope){
        evaluate(scope);
    }
}
