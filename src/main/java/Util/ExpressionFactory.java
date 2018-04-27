package Util;

import Expressions.*;
import Expressions.Annotations.Local;
import Expressions.Operators.Prefix.*;
import Expressions.Markers.*;
import Expressions.Operators.Infix.*;
import Expressions.Values.Boolliteral;
import Expressions.Values.Identifier;
import Expressions.Values.Numliteral;
import Tokenizer.ExpressionInfo;
import Tokenizer.Token;

public class ExpressionFactory {

    public static Expression createExpression(Token token, Scope scope){
        //Todo: Make configurable
        ExpressionInfo type = token.getType();

        if(type.equals(Return.getTokenType()))
            return new Return(scope);

        if(type.equals(Local.getTokenType()))
            return new Local();

        if(type.equals(PrecedenceBracketOpen.getTokenType()))
            return new PrecedenceBracketOpen();

        if(type.equals(PrecedenceBracketClose.getTokenType()))
            return new PrecedenceBracketClose();

        if(type.equals(Plus.getTokenType()))
            return new Plus();

        if(type.equals(Minus.getTokenType()))
            return new Minus();

        if(type.equals(Multiply.getTokenType()))
            return new Multiply();

        if(type.equals(Assignment.getTokenType()))
            return new Assignment();

        if(type.equals(Divide.getTokenType()))
            return new Divide();

        if(type.equals(Modulo.getTokenType()))
            return new Modulo();

        if(type.equals(Numliteral.getTokenType()))
            return new Numliteral(token.getValue());

        if(type.equals(Identifier.getTokenType()))
            return new Identifier(token.getValue(), scope);

        if(type.equals(Boolliteral.getTokenType()))
            return new Boolliteral(token.getValue());

        if(type.equals(And.getTokenType()))
            return new And();

        if(type.equals(Or.getTokenType()))
            return new Or();

        if(type.equals(Equal.getTokenType()))
            return new Equal();

        if(type.equals(Less.getTokenType()))
            return new Less();

        if(type.equals(More.getTokenType()))
            return new More();

        if(type.equals(Seperator.getTokenType()))
            return new Seperator();

        if(type.equals(While.getTokenType()))
            return new While();

        if(type.equals(If.getTokenType()))
            return new If();

        if(type.equals(PlusPlus.getTokenType()))
            return new PlusPlus();

        throw new RuntimeException("Expression Name not found: " + type.getExpressionName());
    }
}
