import Expressions.Expression;
import Expressions.Operator;
import Expressions.RightValue;
import Tokenizer.*;

import java.util.*;

public class Molang {
    private Expression ast;

    public Molang(String code){
        Tokenizer tokenizer = new Tokenizer();

        LinkedList<Operator> operatorBacklock = new LinkedList<>();
        LinkedList<RightValue> operandBacklock = new LinkedList<>();

        {
            LinkedList<Token> tokens = new LinkedList<>();
            tokens.addAll(tokenizer.tokenize(code));

            while (!tokens.isEmpty()) {
                Token currentToken = tokens.pop();
                if (currentToken.getType().isOperator())
                    operatorBacklock.add(ExpressionFactory.createOperatorExpr(currentToken));
                else
                    operandBacklock.add(ExpressionFactory.createRightValueExpr(currentToken));
            }
        }

        while (operatorBacklock.size() + operandBacklock.size() > 1){
            Operator currentOperator = operatorBacklock.pop();
            RightValue value = operandBacklock.pop();

            int nextOperatorPriority = -1;
            if(!operatorBacklock.isEmpty()){
                nextOperatorPriority = operatorBacklock.peek().getPriority();
            }

            if(currentOperator.getPriority() >= nextOperatorPriority){
                currentOperator.assign(value, operandBacklock.pop());
                operandBacklock.push(currentOperator);
            }
            else{
                operandBacklock.add(value);
                operatorBacklock.add(currentOperator);
            }
        }

        ast = operandBacklock.pop();
    }

    public int exec(){
        return 0;
    }

    public Expression getAst() {
        return ast;
    }
}
