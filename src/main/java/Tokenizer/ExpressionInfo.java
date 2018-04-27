package Tokenizer;

import java.util.function.Predicate;

public class ExpressionInfo {
    private String expressionName;
    private Predicate<String> matcher;

    //Optional
    private Integer priority = 0;

    public ExpressionInfo(String expressionName, Predicate<String> matcher) {
        this.expressionName = expressionName;
        this.matcher = matcher;
    }

    public ExpressionInfo(String expressionName, Predicate<String> matcher, Integer priority){
        this(expressionName, matcher);
        this.priority = priority;
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

    @Override
    public String toString() {
        return expressionName;
    }
}
