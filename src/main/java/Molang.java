import Expressions.*;
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

    private Expression createExpressionTree(LinkedList<Token> tokens){
        ArrayList<Expression> expressionBacklog = new ArrayList<>();

        while (!tokens.isEmpty())
                expressionBacklog.add(ExpressionFactory.createExpression(tokens.pop(), context));

        return createExpressionTree(expressionBacklog);
    }

    private static boolean isSibling(Object child, Class parent){
        return parent.isAssignableFrom(child.getClass());
    }

    private Expression createExpressionTree(ArrayList<Expression> expressionBacklog){

        //Find brackets and reduce before

        int openBrackets = 0;
        int openingBracketIndex = -1;
        ArrayList<Expression> innerExpressions = new ArrayList<>();
        for(int i = 0; i < expressionBacklog.size(); i++) {
            if (isSibling(expressionBacklog.get(i), PrecedenceBracketOpen.class)) {
                openBrackets++;
                if(openingBracketIndex == -1)
                    openingBracketIndex = i;
            }

            if (isSibling(expressionBacklog.get(i), PrecedenceBracketClose.class)) {
                openBrackets--;

                if(openBrackets == 0 && innerExpressions.size() != 0){
                    //Remove brackets
                    expressionBacklog.remove(i);
                    innerExpressions.remove(0);

                    expressionBacklog.add(openingBracketIndex, createExpressionTree(innerExpressions));
                    innerExpressions.clear();
                    openingBracketIndex = -1;
                }
            }

            if(openBrackets > 0){
                innerExpressions.add(expressionBacklog.get(i));
                expressionBacklog.remove(i);
                i--;
            }
        }

        //Reduce tree

        while (expressionBacklog.size() > 1) {
            for (int i = 0; i < expressionBacklog.size() && expressionBacklog.size() > 1; i++) { //Todo: Should use iterator
                Expression currentExpression = expressionBacklog.get(i);

                if(isSibling(currentExpression, Operator.class)){

                    Operator currentOperator = (Operator)currentExpression;
                    if(!currentOperator.isComplete()) {

                        //Find next operator priority
                        int nextOperatorPriority = -1;
                        for (int a = i + 1; a < expressionBacklog.size(); a++) {
                            Expression otherExpression = expressionBacklog.get(a);
                            if (isSibling(otherExpression, Operator.class)) {
                                Operator otherOperator = ((Operator) otherExpression);
                                if(!otherOperator.isComplete()) {
                                    nextOperatorPriority = otherOperator.getPriority();
                                    break;
                                }
                            }
                        }

                        //Do the operation
                        if (currentOperator.getPriority() >= nextOperatorPriority) {
                            RightValue left = (RightValue) expressionBacklog.get(i - 1);
                            RightValue right = (RightValue)expressionBacklog.get(i + 1);
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

    public Object execLine(){
        return ((RightValue)expressions.get(0)).evaluate();
    }

    public Context getContext() {
        return context;
    }
}
