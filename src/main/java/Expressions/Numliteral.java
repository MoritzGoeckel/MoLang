package Expressions;

import Tokenizer.TokenType;
import java.util.function.Predicate;

public class Numliteral extends RightValue<Integer> {

    private Integer value;

    public Numliteral(String value){
        this.value = Integer.parseInt(value);
    }

    public static Predicate<String> getRecognizer(){
        return x -> x.matches("[0-9]+");
    }

    public static TokenType getTokenType() {
        return TokenType.NUMLITERAL;
    }

    @Override
    Integer evaluate() {
        return value;
    }
}
