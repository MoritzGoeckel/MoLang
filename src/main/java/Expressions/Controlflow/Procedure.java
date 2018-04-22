package Expressions.Controlflow;

import Expressions.Expression;
import Expressions.RightValue;

import java.util.LinkedList;
import java.util.List;

public class Procedure extends Expression {
    private List<Expression> expressionList;

    public Procedure(){
        this.expressionList = new LinkedList<>();
    }

    public void addExpression(Expression expression){
        expressionList.add(expression);
    }

    @Override
    public void execute() {
        for(Expression e : expressionList){
            e.execute();
        }
    }

    public Object evaluateLine(int number) {
        return ((RightValue)expressionList.get(number)).evaluate();
    }
}
