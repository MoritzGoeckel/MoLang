package Expressions.ControlFlow;

import Expressions.Expression;
import Expressions.RightValue;
import Util.Scope;

import java.util.LinkedList;
import java.util.List;

public class Procedure extends RightValue {
    private List<Expression> expressionList;
    private Scope scope;

    public Procedure(Scope parent){
        this.expressionList = new LinkedList<>();
        this.scope = new Scope(parent);
    }

    public void addExpression(Expression expression){
        expressionList.add(expression);
    }

    public Object evaluateLine(int number) {
        return ((RightValue)expressionList.get(number)).evaluate();
    }

    public Scope getScope() {
        return scope;
    }

    public void resetScope(){
        this.scope.reset();
    }

    @Override
    public Object evaluate() {
        for(Expression e : expressionList){
            e.execute();
        }

        //Todo: some kind of return statement should be added
        return null;
    }
}
