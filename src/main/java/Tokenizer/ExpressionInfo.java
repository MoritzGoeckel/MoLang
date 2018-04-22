package Tokenizer;

import java.util.function.Predicate;

public class ExpressionInfo {
    private String expressionName;
    private Predicate<String> matcher;

    //Optional
    private Integer priority = 0;
    private boolean isOperator = false;
    private boolean isProcedure = false;

    public ExpressionInfo(String expressionName, Predicate<String> matcher) {
        this.expressionName = expressionName;
        this.matcher = matcher;
    }

    public ExpressionInfo(String expressionName, Predicate<String> matcher, Integer priority, Boolean isOperator){
        this(expressionName, matcher);
        this.priority = priority;
        this.isOperator = isOperator;
    }

    public ExpressionInfo(String expressionName, Predicate<String> matcher, Integer priority, Boolean isOperator, Boolean isProcedure){
        this(expressionName, matcher);
        this.priority = priority;
        this.isOperator = isOperator;
        this.isProcedure = isProcedure;
    }

    public boolean match(String input){
        return matcher.test(input);
    }

    public String getExpressionName() {
        return expressionName;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == ExpressionInfo.class)
            return expressionName.equals(((ExpressionInfo)obj).expressionName);
        else
            return false;
    }

    public boolean isOperator() {
        return isOperator;
    }

    @Override
    public String toString() {
        return expressionName;
    }

    public boolean isProcedure() {
        return isProcedure;
    }
}
