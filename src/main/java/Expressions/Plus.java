package Expressions;

import Tokenizer.TokenType;
import java.util.function.Predicate;

public class Plus extends Operator<Integer>{

    RightValue<Integer> left, right;

    public Plus(RightValue<Integer> left, RightValue<Integer> right){
        this.left = left;
        this.right = right;
    }

    public static Predicate<String> getRecognizer(){
        return x -> x.equals("+");
    }

    public static TokenType getTokenType(){
        return TokenType.PLUS;
    }

    @Override
    Integer evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
