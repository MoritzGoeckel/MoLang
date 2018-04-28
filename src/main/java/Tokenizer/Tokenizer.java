package Tokenizer;

import Expressions.Annotations.Local;
import Expressions.Markers.*;
import Expressions.Operators.Infix.*;
import Expressions.Operators.Prefix.*;
import Expressions.Values.Boolliteral;
import Expressions.Values.Identifier;
import Expressions.Values.Numliteral;

import java.util.*;

public class Tokenizer {

    private List<ExpressionInfo> expressionInfos = new ArrayList<>();
    {
        //Todo: Make it configurable
        expressionInfos.add(Boolliteral.getTokenType()); //Needs to be before identifier
        expressionInfos.add(ProcedureBracketsClose.getTokenType());
        expressionInfos.add(If.getTokenType());
        expressionInfos.add(ProcedureBracketsOpen.getTokenType());
        expressionInfos.add(While.getTokenType());
        expressionInfos.add(PlusPlus.getTokenType());
        expressionInfos.add(Return.getTokenType());
        expressionInfos.add(Local.getTokenType());
        expressionInfos.add(Numliteral.getTokenType());
        expressionInfos.add(Plus.getTokenType());
        expressionInfos.add(Multiply.getTokenType());
        expressionInfos.add(Assignment.getTokenType());
        expressionInfos.add(Separator.getTokenType());
        expressionInfos.add(Minus.getTokenType());
        expressionInfos.add(Modulo.getTokenType());
        expressionInfos.add(Divide.getTokenType());
        expressionInfos.add(PrecedenceBracketOpen.getTokenType());
        expressionInfos.add(PrecedenceBracketClose.getTokenType());
        expressionInfos.add(And.getTokenType());
        expressionInfos.add(Or.getTokenType());
        expressionInfos.add(More.getTokenType());
        expressionInfos.add(Less.getTokenType());
        expressionInfos.add(Equal.getTokenType());
        expressionInfos.add(ArgumentSeparator.getTokenType());

        //Needs to be way down because of collision
        expressionInfos.add(Identifier.getTokenType());
    }

    //Todo: Make it configurable
    //Todo: Problem with =, does not get separated yet same for +
    private String[] separators = {"(", ")", ";", "[", "]", "{", "}", "-", "*", "/", "==", "%", "||", "&&", ",", "++"};

    public String preprocess(String code){
        code = code.replace('\n', ' ');
        code = code.replace('\t', ' ');

        for(String c : separators)
            code = code.replace(c, " "+c+" ");

        return code;
    }

    public LinkedList<Token> tokenize(String code){
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

    public LinkedList<Token> postprocess(LinkedList<Token> tokens){
        LinkedList<Token> tokensOut = new LinkedList<>();

        for(Token token : tokens){
            if(tokensOut.size() > 0) {

                //Strip separator after }
                if (tokensOut.getLast().getType().equals(ProcedureBracketsClose.getTokenType())
                        && token.getType().equals(Separator.getTokenType()))
                    continue;

                //Strip double separators
                if (tokensOut.getLast().getType().equals(Separator.getTokenType())
                        && token.getType().equals(Separator.getTokenType()))
                    continue;
            }

            tokensOut.add(token);
        }

        //Add separator at end of program if it is not concluded by } or ;
        if(!tokensOut.getLast().getType().equals(ProcedureBracketsClose.getTokenType()) && !tokensOut.getLast().getType().equals(Separator.getTokenType()))
            tokensOut.add(new Token(Separator.getTokenType(), ";"));

        //Add main procedure brackets if first are missing
        if (!tokensOut.getFirst().getType().equals(ProcedureBracketsOpen.getTokenType())) {
            tokensOut.add(0, new Token(ProcedureBracketsOpen.getTokenType(), "{"));
            tokensOut.add(new Token(ProcedureBracketsClose.getTokenType(), "}"));
        }

        return tokensOut;
    }
}
