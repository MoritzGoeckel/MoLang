import Expressions.Expression;
import Expressions.Operator;
import Expressions.RightValue;
import Tokenizer.*;

import java.util.*;

public class Molang {
    private Expression ast;

    public Molang(String code){
        Tokenizer tokenizer = new Tokenizer();
        LinkedList<Token> tokens = tokenizer.tokenize(code);
        ast = createExpressionTree(tokens);
    }

    public RightValue createExpressionTree(LinkedList<Token> tokens){
        LinkedList<Operator> operatorBacklog = new LinkedList<>();
        LinkedList<RightValue> valueBacklog = new LinkedList<>();

        while (!tokens.isEmpty()) {
            Token currentToken = tokens.pop();
            if (currentToken.getType().isOperator())
                operatorBacklog.add(ExpressionFactory.createOperatorExpr(currentToken));
            else
                valueBacklog.add(ExpressionFactory.createRightValueExpr(currentToken));
        }

        while (operatorBacklog.size() + valueBacklog.size() > 1){
            Operator currentOperator = operatorBacklog.pop();
            RightValue value = valueBacklog.pop();

            int nextOperatorPriority = -1;
            if(!operatorBacklog.isEmpty()){
                nextOperatorPriority = operatorBacklog.peek().getPriority();
            }

            if(currentOperator.getPriority() >= nextOperatorPriority){
                currentOperator.assign(value, valueBacklog.pop());
                valueBacklog.push(currentOperator);
            }
            else{
                valueBacklog.add(value);
                operatorBacklog.add(currentOperator);
            }
        }

        return valueBacklog.pop();
    }

    public int exec(){
        return 0;
    }

    public Expression getAst() {
        return ast;
    }
}
