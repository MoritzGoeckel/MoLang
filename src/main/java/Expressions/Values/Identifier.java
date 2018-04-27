package Expressions.Values;

import Expressions.LeftValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Identifier<T> extends LeftValue<T> {

    private String name;
    private Scope scope;
    private boolean local = false;

    public Identifier(String name, Scope scope){
        this.name = name;
        this.scope = scope;
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Identifier", x -> x.matches("[a-zA-Z]+"));
    }

    public void makeLocal(){
        this.local = true;
    }

    @Override
    public T evaluate() {
        return (T)scope.getValue(name);
    }

    @Override
    public void assign(T value) {
        if(local)
            scope.setValueLocal(name, value);
        else
            scope.setValue(name, value);
    }
}
