package Expressions.Values;

import Expressions.RightValue;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class Boolliteral extends RightValue<Boolean> {

    private Boolean value;

    public Boolliteral(String value){
        this.value = value.equals("true");
    }

    public static ExpressionInfo getTokenType() {
        return new ExpressionInfo("Boolliteral", x -> x.equals("true") || x.equals("false"));
    }

    @Override
    public Boolean evaluate(Scope scope) {
        return value;
    }
}
