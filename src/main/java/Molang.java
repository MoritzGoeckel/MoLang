import Expressions.*;
import Expressions.ControlFlow.*;
import Expressions.Markers.*;
import Expressions.Operators.Operator;
import Tokenizer.*;
import Util.ExpressionFactory;
import Util.Scope;

import java.util.*;

public class Molang {
    private Procedure procedure;

    public Molang(String code){
        Tokenizer tokenizer = new Tokenizer();
        code = tokenizer.preprocess(code);
        LinkedList<Token> tokens = tokenizer.tokenize(code);
        tokens = tokenizer.postprocess(tokens);
        procedure = createProcedure(tokens);
    }

    /*//Todo: should that be here?
        if(!expressionList.isEmpty() && isSibling(expressionList.getLast(), Conditional.class))
            ((Conditional)expressionList.getLast()).assignBody(expression);
        else*/

    private Procedure createProcedure(LinkedList<Token> tokens){
        Stack<Procedure> procedureStack = new Stack<>();
        Stack<LinkedList<Expression>> expressionsPerProcedureStack = new Stack<>();

        while (!tokens.isEmpty()) {
            Token currentToken = tokens.pop();
            if(currentToken.getType().equals(ProcedureBracketsOpen.getTokenType())){

                Scope parentScope = null;
                if(!procedureStack.isEmpty())
                    parentScope = procedureStack.peek().getScope();

                procedureStack.push(new Procedure(parentScope));
                expressionsPerProcedureStack.push(new LinkedList<>());

            }else if(currentToken.getType().equals(ProcedureBracketsClose.getTokenType())){
                Procedure procedure = procedureStack.pop();

                LinkedList<Expression> expressions = expressionsPerProcedureStack.pop();
                List<Expression> statements = getStatementList(expressions);
                for(Expression statement : statements)
                    procedure.addExpression(statement);

                if(!procedureStack.isEmpty()) {
                    expressionsPerProcedureStack.peek().add(procedure);
                    expressionsPerProcedureStack.peek().add(new Seperator()); //Todo: Maybe?
                }
                else
                    return procedure;
            }
            else{
                expressionsPerProcedureStack.peek().add(ExpressionFactory.createExpression(currentToken, procedureStack.peek().getScope()));
            }
        }

        throw new RuntimeException("Procedure stack should be simplifiable to one: " + procedureStack.toString());
    }

    private static LinkedList<Expression> getStatementList(LinkedList<Expression> expressions){
        LinkedList<Expression> statements = new LinkedList<>();
        ArrayList<Expression> latestExpressions = new ArrayList<>();

        for(Expression e:expressions){
            if(isSibling(e, Seperator.class)) {
                statements.add(reduceExpressions(latestExpressions));
                latestExpressions.clear();
            }
            else
                latestExpressions.add(e);
        }

        if(!latestExpressions.isEmpty())
            throw new RuntimeException("Some expressions have not been concluded by an statement separator");

        return statements;
    }

    /*private static Expression createExpressionTree(LinkedList<Token> tokens, Scope scope){
        if(tokens.size() == 0)
            throw new RuntimeException("Tokens are empty");

        //Handling procedures
        if(tokens.getFirst().getType().isProcedure()){
            //Removing the procedure opener
            Token token = tokens.removeFirst();

            //Creating the procedure node
            if(token.getType().equals(If.getTokenType()))
                return new If((RightValue<Boolean>) createExpressionTree(tokens, scope));
            else if(token.getType().equals(While.getTokenType()))
                return new While((RightValue<Boolean>) createExpressionTree(tokens, scope));

            throw new RuntimeException("Could not find procedure class: " + token.getType());
        }
        else { //Handling normal expressions
            ArrayList<Expression> expressionBacklog = new ArrayList<>();

            while (!tokens.isEmpty())
                expressionBacklog.add(ExpressionFactory.createExpression(tokens.pop(), scope));

            return reduceExpressions(expressionBacklog);
        }
    }*/

    private static Expression reduceExpressions(ArrayList<Expression> expressionBacklog){

        if(isSibling(expressionBacklog.get(0), Conditional.class)){
            Conditional conditional = (Conditional) expressionBacklog.get(0);
            expressionBacklog.remove(0);

            int lastIndex = expressionBacklog.size() - 1;
            Procedure body = (Procedure) expressionBacklog.get(lastIndex);
            expressionBacklog.remove(lastIndex);

            RightValue<Boolean> condition = (RightValue<Boolean>) reduceExpressions(expressionBacklog);

            conditional.assignConditionAndBody(condition, body);

            return conditional;
        }

        //Find brackets and reduce before
        expressionBacklog = processPrecedenceBrackets(expressionBacklog);

        //Reduce tree to one element
        while (expressionBacklog.size() > 1) {
            //Todo: Should use iterator
            for (int i = 0; i < expressionBacklog.size() && expressionBacklog.size() > 1; i++) {
                Expression currentExpression = expressionBacklog.get(i);

                //Process operator
                if(isSibling(currentExpression, Operator.class)){
                    Operator currentOperator = (Operator)currentExpression;
                    if(!currentOperator.isComplete()) {

                        //Find next operator priority
                        int nextOperatorPriority = getNextOperatorPriority(i, expressionBacklog);

                        //ProcedureBracketsOpen the operation
                        if (currentOperator.getPriority() >= nextOperatorPriority) {
                            RightValue left = (RightValue) expressionBacklog.get(i - 1);
                            RightValue right = (RightValue) expressionBacklog.get(i + 1);
                            currentOperator.assign(left, right);

                            expressionBacklog.remove(i - 1);
                            expressionBacklog.remove(i); //Former i + 1

                            i--;
                        }
                    }
                }
            }
        }

        //The expression backlog should be simplified to one element now
        return expressionBacklog.get(expressionBacklog.size() - 1);
    }

    private static int getNextOperatorPriority(int startIndex, ArrayList<Expression> expressionBacklog) {
        int nextOperatorPriority = -1;
        for (int a = startIndex + 1; a < expressionBacklog.size(); a++) {
            Expression otherExpression = expressionBacklog.get(a);
            if (isSibling(otherExpression, Operator.class)) {
                Operator otherOperator = ((Operator) otherExpression);
                if(!otherOperator.isComplete()) {
                    nextOperatorPriority = otherOperator.getPriority();
                    break;
                }
            }
        }
        return nextOperatorPriority;
    }

    private static ArrayList<Expression> processPrecedenceBrackets(ArrayList<Expression> expressionBacklog) {
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

                    expressionBacklog.add(openingBracketIndex, reduceExpressions(innerExpressions));
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

        return expressionBacklog;
    }

    private static boolean isSibling(Object child, Class parent){
        return parent.isAssignableFrom(child.getClass());
    }

    public void exec(){
        procedure.execute();
    }

    public Object execLine(){
        return procedure.evaluateLine(0);
    }

    public Scope getScope() {
        return this.procedure.getScope();
    }

    public void resetContext(){
        this.procedure.resetScope();
    }
}