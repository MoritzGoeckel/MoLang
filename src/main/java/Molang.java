import Expressions.Expression;
import Expressions.Operator;
import Expressions.RightValue;
import Expressions.Seperator;
import Tokenizer.*;

import java.util.*;

public class Molang {
    private LinkedList<Expression> expressions;
    private Context context;

    public Molang(String code){
        Tokenizer tokenizer = new Tokenizer();
        LinkedList<Token> tokens = tokenizer.tokenize(code);
        context = new Context();
        expressions = createExpressionList(tokens);
    }

    private LinkedList<Expression> createExpressionList(LinkedList<Token> tokens){
        LinkedList<Expression> expressions = new LinkedList<>();
        LinkedList<Token> latestTokens = new LinkedList<>();

        while (!tokens.isEmpty()) {
            Token currentToken = tokens.pop();
            if (currentToken.getType().equals(Seperator.getTokenType())){
                expressions.add(createExpressionTree(latestTokens));
                latestTokens.clear();
            }
            else {
                latestTokens.add(currentToken);
            }
        }

        if(!latestTokens.isEmpty())
            expressions.add(createExpressionTree(latestTokens));

        return expressions;
    }

    private RightValue createExpressionTree(LinkedList<Token> tokens){
        ArrayList<RightValue> expressionBacklog = new ArrayList<>();

        while (!tokens.isEmpty()) {
            Token currentToken = tokens.pop();
            if (currentToken.getType().isOperator())
                expressionBacklog.add(ExpressionFactory.createOperatorExpr(currentToken));
            else
                expressionBacklog.add(ExpressionFactory.createRightValueExpr(currentToken, context));
        }

        while (expressionBacklog.size() > 1) {
            for (int i = 0; i < expressionBacklog.size() && expressionBacklog.size() > 1; i++) { //Todo: Should use iterator
                Expression currentExpression = expressionBacklog.get(i);
                if(Operator.class.isAssignableFrom(currentExpression.getClass())){

                    Operator currentOperator = (Operator)currentExpression;

                    if(!currentOperator.isComplete()) {

                        //Find next operator priority
                        int nextOperatorPriority = -1;
                        for (int a = i + 1; a < expressionBacklog.size(); a++) {
                            Expression otherExpression = expressionBacklog.get(a);
                            if (Operator.class.isAssignableFrom(otherExpression.getClass())) {
                                Operator otherOperator = ((Operator) otherExpression);
                                if(!otherOperator.isComplete()) {
                                    nextOperatorPriority = otherOperator.getPriority();
                                    break;
                                }
                            }
                        }

                        //Do the operation
                        if (currentOperator.getPriority() >= nextOperatorPriority) {
                            RightValue left = expressionBacklog.get(i - 1);
                            RightValue right = expressionBacklog.get(i + 1);
                            currentOperator.assign(left, right);

                            expressionBacklog.remove(i - 1);
                            expressionBacklog.remove(i); //Former i + 1

                            i--;
                        }
                    }
                }

            }
        }

        return expressionBacklog.get(expressionBacklog.size() - 1);
    }

    public void exec(){
        for(Expression e : expressions)
            e.execute();
    }

    public Integer execLine(){
        return (Integer)((RightValue)expressions.get(0)).evaluate();
    }

    public Context getContext() {
        return context;
    }
}
