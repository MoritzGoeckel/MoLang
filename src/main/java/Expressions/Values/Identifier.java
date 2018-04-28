package Expressions.Values;

import Expressions.LeftValue;
import Expressions.Procedure;
import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

import java.util.LinkedList;

public class Identifier<T> extends LeftValue<T> {

    private String name;
    private Scope scope;
    private boolean local = false;
    private LinkedList<RightValue> parameters;

    public Identifier(String name, Scope scope){
        this.name = name;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Identifier", x -> x.matches("[a-zA-Z]+"));
    }

    public void makeLocal(){
        this.local = true;
    }

    public void makeFunctionCall(LinkedList<RightValue> parameters){
        this.parameters = parameters;
    }

    @Override
    public T evaluate() {
        if(parameters == null)
            return (T)scope.getValue(name);
        else {
            //Eval function
            Procedure procedure = (Procedure)scope.getValue(name);
            procedure.assignArguments(parameters);
            return (T)procedure.evaluate();
        }
    }

    @Override
    public void assign(T value) {
        if(local)
            scope.setValueLocal(name, value);
        else
            scope.setValue(name, value);
    }
}
