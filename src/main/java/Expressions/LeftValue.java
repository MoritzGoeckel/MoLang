package Expressions;

public abstract class LeftValue<T> extends RightValue<T>{
    public abstract void assign(T value);
}