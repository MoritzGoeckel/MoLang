package Tokenizer;

import Expressions.*;
import java.util.*;

public class Tokenizer {

    private List<ExpressionInfo> expressionInfos = new ArrayList<>();
    {
        //Todo: Make it configurable
        expressionInfos.add(Numliteral.getTokenType());
        expressionInfos.add(Plus.getTokenType());
        expressionInfos.add(Multiply.getTokenType());
        expressionInfos.add(Identifier.getTokenType());
        expressionInfos.add(Assignment.getTokenType());
        expressionInfos.add(Seperator.getTokenType());
        expressionInfos.add(Minus.getTokenType());
        expressionInfos.add(Modulo.getTokenType());
        expressionInfos.add(Divide.getTokenType());
        expressionInfos.add(PrecedenceBracketOpen.getTokenType());
        expressionInfos.add(PrecedenceBracketClose.getTokenType());
    }

    //Todo: Make it configurable
    private String separators = "();[]{}+-*/=%";

    public LinkedList<Token> tokenize(String code){
        for(Character c : separators.toCharArray())
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
