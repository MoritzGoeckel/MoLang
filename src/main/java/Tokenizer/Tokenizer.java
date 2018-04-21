package Tokenizer;

import Expressions.*;

import java.util.*;
import java.util.function.Predicate;

public class Tokenizer {

    private List<ExpressionInfo> expressionInfos = new ArrayList<>();
    {
        expressionInfos.add(Numliteral.getTokenType());
        expressionInfos.add(Plus.getTokenType());
        expressionInfos.add(Multiply.getTokenType());
        expressionInfos.add(Identifier.getTokenType());
        expressionInfos.add(Assignment.getTokenType());
    }

    private String seperators = "();[]{}+-*/=";

    public LinkedList<Token> tokenize(String code){
        for(Character c : seperators.toCharArray())
            code = code.replace(c.toString(), " "+c.toString()+" ");

        String[] items = code.split(" ");

        LinkedList<Token> tokens = new LinkedList<Token>();
        for(String item : items){
            if(item.isEmpty())
                continue;

            Boolean foundSomething = false;
            for (ExpressionInfo expInfo : expressionInfos) {
                if(expInfo.match(item)){
                    tokens.add(new Token(expInfo, item));
                    foundSomething = true;
                    break;
                }
            }

            if(!foundSomething)
                throw new RuntimeException(item + " is unknown!");
        }

        return tokens;
    }
}
