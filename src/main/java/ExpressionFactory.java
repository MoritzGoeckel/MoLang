import Expressions.*;
import Tokenizer.Token;

public class ExpressionFactory {
    public static Operator createOperatorExpr(Token token){
        String name = token.getType().getExpressionName();

        if(name.equals(Plus.getTokenType().getExpressionName()))
            return new Plus();

        if(name.equals(Multiply.getTokenType().getExpressionName()))
            return new Multiply();

        throw new RuntimeException("Operator Name not found: " + name);
    }

    public static RightValue createRightValueExpr(Token token){
        String name = token.getType().getExpressionName();

        if(name.equals(Numliteral.getTokenType().getExpressionName()))
            return new Numliteral(token.getValue());

        throw new RuntimeException("Operator Name not found: " + name);
    }
}
