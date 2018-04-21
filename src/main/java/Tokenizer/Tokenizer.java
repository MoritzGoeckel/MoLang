package Tokenizer;

import Expressions.Multiply;
import Expressions.Numliteral;
import Expressions.Plus;

import java.util.*;
import java.util.function.Predicate;

public class Tokenizer {

    private List<ExpressionInfo> expressionInfos = new ArrayList<>();
    {
        expressionInfos.add(Numliteral.getTokenType());
        expressionInfos.add(Plus.getTokenType());
        expressionInfos.add(Multiply.getTokenType());
    }

    private String seperators = "();[]{}+-*/";

    public List<Token> tokenize(String code){
        for(Character c : seperators.toCharArray())
            code = code.replace(c.toString(), " "+c.toString()+" ");

        String[] items = code.split(" ");

        List<Token> tokens = new LinkedList<Token>();
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
