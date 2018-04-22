package Tokenizer;

import java.util.function.Predicate;

public class ExpressionInfo {
    private String expressionName;
    private Integer priority;
    private Predicate<String> matcher;
    private boolean isOperator;

    public ExpressionInfo(String expressionName, Integer priority, boolean isOperator, Predicate<String> matcher) {
        this.expressionName = expressionName;
        this.priority = priority;
        this.matcher = matcher;
        this.isOperator = isOperator;
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
}
