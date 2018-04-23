package Expressions.Values;

import Expressions.LeftValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Identifier<T> extends LeftValue<T> {

    private String name;
    private Scope scope;

    public Identifier(String name, Scope scope){
        this.name = name;
        this.scope = scope;
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Identifier", x -> x.matches("[a-zA-Z]+"));
    }

    @Override
    public T evaluate() {
        return (T)scope.getValue(name);
    }

    @Override
    public void assign(T value) {
        scope.setValue(name, value);
    }
}
