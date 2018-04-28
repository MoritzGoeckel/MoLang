package Expressions;

import Util.Scope;
import java.util.LinkedList;

public class Procedure extends RightValue {
    private LinkedList<Expression> expressionList;
    private LinkedList<String> argumentNames;

    public Procedure(){
        this.expressionList = new LinkedList<>();
    }

    public void addExpression(Expression expression){
        expressionList.add(expression);
    }

    @Override
    public Object evaluate(Scope scope) {
        return evaluateWithScope(new Scope(scope));
    }

    public Object evaluateWithArguments(Scope scope, LinkedList<RightValue> values) {
        Scope thisScope = new Scope(scope);

        if(values.size() != argumentNames.size())
            throw new RuntimeException("Number of arguments does not match: " + values.size() + " != " + argumentNames.size());

        for(int i = 0; i < values.size(); i++){
            thisScope.setValueLocal(argumentNames.get(i), values.get(i).evaluate(scope));
        }

        return evaluateWithScope(thisScope);
    }

    public Object evaluateWithScope(Scope scope){
        for(Expression e : expressionList){
            e.execute(scope);
        }
        return scope.getReturnValue();
    }

    public void setArgumentNames(LinkedList<String> argumentNames){
        if(this.argumentNames == null)
            this.argumentNames = argumentNames;
        else
            throw new RuntimeException("argumentNames already set!");
    }

    public LinkedList<String> getArgumentNames() {
        return argumentNames;
    }
}
