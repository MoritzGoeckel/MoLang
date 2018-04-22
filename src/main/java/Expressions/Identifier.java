package Expressions;

import Tokenizer.ExpressionInfo;

public class Identifier<T> extends LeftValue<T> {

    private T value;
    private String name;

    public Identifier(String name){
        this.name = name;
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Identifier", x -> x.matches("[a-zA-Z]+"));
    }

    @Override
    public T evaluate() {
        return value;
    }

    @Override
    public void assign(T value) {
        this.value = value;
    }
}
