import Expressions.*;
import Expressions.Annotations.Annotation;
import Expressions.Markers.*;
import Expressions.Operators.Infix.Assignment;
import Expressions.Operators.Infix.Infix;
import Expressions.Operators.Operator;
import Expressions.Operators.Prefix.Prefix;
import Expressions.Operators.Prefix.Return;
import Expressions.Values.Identifier;
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

    private static Procedure createProcedure(LinkedList<Token> tokens){
        Stack<Procedure> procedureStack = new Stack<>();
        Stack<LinkedList<Expression>> expressionsPerProcedureStack = new Stack<>();
        Stack<Annotation> pendingAnnotations = new Stack<>();

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
                LinkedList<Expression> statements = getStatementList(expressions);

                addImplicitReturn(procedure, statements);

                for (Expression statement : statements)
                    procedure.addExpression(statement);

                if(!procedureStack.isEmpty()) {
                    expressionsPerProcedureStack.peek().add(procedure);
                    expressionsPerProcedureStack.peek().add(new Separator()); //Todo: Is that correct?
                }
                else
                    return procedure;
            }
            else{
                //Create expression
                Expression expr = ExpressionFactory.createExpression(currentToken, procedureStack.peek().getScope());

                if(isSibling(expr, Annotation.class)) {
                    //Stack annotations
                    pendingAnnotations.push((Annotation) expr);
                }
                else {
                    //Apply annotations
                    while (!pendingAnnotations.isEmpty())
                        expr = pendingAnnotations.pop().Process(expr);

                    //Add expression
                    expressionsPerProcedureStack.peek().add(expr);
                }
            }
        }

        throw new RuntimeException("Procedure stack should be simplifiable to one: " + procedureStack.toString());
    }

    private static void addImplicitReturn(Procedure procedure, LinkedList<Expression> statements) {
        //Check if last statement should be converted to return
        Expression lastStatement = statements.getLast();
        if(isSibling(lastStatement, RightValue.class) && !isSibling(lastStatement, Return.class)) {
            //Override the last statement
            Return enhancedStatement = new Return(procedure.getScope());
            enhancedStatement.addOperand(lastStatement);
            statements.set(statements.size() - 1, enhancedStatement);
        }
    }

    private static LinkedList<Expression> getStatementList(LinkedList<Expression> expressions){
        LinkedList<Expression> statements = new LinkedList<>();
        ArrayList<Expression> latestExpressions = new ArrayList<>();

        for(Expression e:expressions){
            if(isSibling(e, Separator.class)) {
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

    private static Expression reduceExpressions(ArrayList<Expression> expressionBacklog){

        //Find brackets and reduce them before creating ast
        expressionBacklog = processPrecedenceBrackets(expressionBacklog);

        //Process argument lists
        Expression argumentList = processArgumentList(expressionBacklog);
        if (argumentList != null) return argumentList;

        //Process function definitions
        processFunctionDefinitions(expressionBacklog);

        //Process function calls
        processFunctionCalls(expressionBacklog);

        //Reduce tree to one element
        while (expressionBacklog.size() > 1) {
            for (int i = 0; i < expressionBacklog.size() && expressionBacklog.size() > 1; i++) {
                Expression currentExpression = expressionBacklog.get(i);

                //Process operator
                if(isSibling(currentExpression, Operator.class)){
                    Operator currentOperator = (Operator)currentExpression;
                    if(!currentOperator.isComplete()) {

                        //Find next operator priority
                        int nextOperatorPriority = getNextOperatorPriority(i, expressionBacklog);
                        if (currentOperator.getPriority() >= nextOperatorPriority) {

                            if(isSibling(currentOperator, Infix.class)){
                                //Infix (2 operands)
                                RightValue left = (RightValue) expressionBacklog.get(i - 1);
                                RightValue right = (RightValue) expressionBacklog.get(i + 1);
                                ((Infix)currentOperator).setOperands(left, right);
                                expressionBacklog.remove(i - 1);
                                expressionBacklog.remove(i); //Former i + 1
                            }else if(isSibling(currentOperator, Prefix.class)){
                                //Prefix (n operands)
                                Prefix currentPrefix = (Prefix)currentOperator;
                                while (!currentPrefix.isComplete()) {
                                    RightValue right = (RightValue) expressionBacklog.get(i + 1);
                                    currentPrefix.addOperand(right);
                                    expressionBacklog.remove(i + 1);
                                }
                            }
                            else {
                                //Not infix nor prefix
                                throw new RuntimeException("What kind of operator is that? " + currentOperator);
                            }

                            i--;
                        }
                    }
                }
            }
        }

        //The expression backlog should be simplified to one element now
        if(expressionBacklog.size() != 1)
            throw new RuntimeException("Expression list should be reducible to one expression! Expressions left: " + expressionBacklog.size());

        return expressionBacklog.get(expressionBacklog.size() - 1);
    }

    private static void processFunctionCalls(ArrayList<Expression> expressionBacklog) {
        for(int i = 0; i < expressionBacklog.size(); i++){
            Expression currentExpression = expressionBacklog.get(i);

            //Has next element and seeing next element
            //Processing Function calls / Definitions
            if(i + 1 < expressionBacklog.size() && isSibling(currentExpression, Identifier.class)) {
                Identifier identifier = (Identifier)currentExpression;
                Expression nextExpression = expressionBacklog.get(i + 1);

                if(isSibling(nextExpression, ArgumentList.class)){
                    //Function call with argumentList
                    ArgumentList argument = (ArgumentList)nextExpression;
                    identifier.makeFunctionCall(argument.getArguments());

                    expressionBacklog.remove(i + 1);
                }

                if(isSibling(nextExpression, RightValue.class) && (!isSibling(nextExpression, Operator.class) || ((Operator)nextExpression).isComplete())){
                    //Function call with single argument
                    RightValue argument = (RightValue)nextExpression;
                    LinkedList<RightValue> toList = new LinkedList<>();
                    toList.add(argument);
                    identifier.makeFunctionCall(toList);

                    expressionBacklog.remove(i + 1);
                }
            }
        }
    }

    private static void processFunctionDefinitions(ArrayList<Expression> expressionBacklog) {
        for(int i = 0; i < expressionBacklog.size(); i++){
            Expression currentExpression = expressionBacklog.get(i);

            //Has next element and seeing next element
            //Processing Function calls / Definitions
            if(i + 1 < expressionBacklog.size() && isSibling(expressionBacklog.get(i + 1), Procedure.class)) {
                Procedure procedure = (Procedure)expressionBacklog.get(i + 1);

                if(isSibling(currentExpression, ArgumentList.class) || isSibling(currentExpression, Identifier.class)){
                    //Its a definition
                    LinkedList<String> names = new LinkedList<>();

                    if(isSibling(currentExpression, ArgumentList.class))
                        names.addAll(((ArgumentList) currentExpression).getArgumentNames());

                    if(isSibling(currentExpression, Identifier.class))
                        names.add(((Identifier)currentExpression).getName());

                    procedure.setArgumentNames(names);
                    expressionBacklog.remove(i);
                }
            }
        }
    }

    private static Expression processArgumentList(ArrayList<Expression> expressionBacklog) {
        ArrayList<Expression> pendingArgumentExpressions = new ArrayList<>();
        LinkedList<Expression> arguments = new LinkedList<>();
        for(Expression e : expressionBacklog){
            if(isSibling(e, ArgumentSeparator.class)) {
                arguments.add(reduceExpressions(pendingArgumentExpressions));
                pendingArgumentExpressions.clear();
            }
            else
                pendingArgumentExpressions.add(e);
        }

        if(!arguments.isEmpty()) {
            //Its an argument list because it has argument separators
            if(!pendingArgumentExpressions.isEmpty())
                arguments.add(reduceExpressions(pendingArgumentExpressions));
            return new ArgumentList(arguments);
        }
        return null;
    }

    private static int getNextOperatorPriority(int startIndex, ArrayList<Expression> expressionBacklog) {
        int nextOperatorPriority = Integer.MIN_VALUE;
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

                if(openBrackets == 0){
                    //Remove brackets
                    expressionBacklog.remove(i);
                    innerExpressions.remove(0);

                    if(innerExpressions.size() != 0)
                        expressionBacklog.add(openingBracketIndex, reduceExpressions(innerExpressions));
                    else
                        expressionBacklog.add(openingBracketIndex, new ArgumentList()); //Its an empty argument list for function execution

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

    public Scope getScope() {
        return this.procedure.getScope();
    }

    public Object getResult(){
        return this.procedure.getScope().getReturnValue();
    }

    public void resetContext(){
        this.procedure.resetScope();
    }
}