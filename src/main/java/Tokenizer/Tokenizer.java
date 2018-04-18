package Tokenizer;

import Expressions.Numliteral;
import Expressions.Plus;

import java.util.*;
import java.util.function.Predicate;

public class Tokenizer {

    private Map<TokenType, Predicate<String>> recognizer = new HashMap<>();
    {
        recognizer.put(Numliteral.getTokenType(), Numliteral.getRecognizer());
        recognizer.put(Plus.getTokenType(), Plus.getRecognizer());
    }

    private String seperators = "();[]{}";

    public List<Token> tokenize(String code){
        for(Character c : seperators.toCharArray())
            code = code.replace(c.toString(), " "+c.toString()+" ");

        String[] items = code.split(" ");

        List<Token> tokens = new LinkedList<Token>();
        for(String item : items){
            Boolean foundSomething = false;
            for (Map.Entry<TokenType, Predicate<String>> entry : recognizer.entrySet()) {
                if(entry.getValue().test(item)){
                    tokens.add(new Token(entry.getKey(), item));
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
