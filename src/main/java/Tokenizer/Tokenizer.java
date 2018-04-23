package Tokenizer;

import Expressions.ControlFlow.*;
import Expressions.Markers.*;
import Expressions.Values.Boolliteral;
import Expressions.Values.Identifier;
import Expressions.Values.Numliteral;
import Expressions.Operators.*;

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
        //expressionInfos.add(Fun.getTokenType());

        expressionInfos.add(Numliteral.getTokenType());
        expressionInfos.add(Plus.getTokenType());
        expressionInfos.add(Multiply.getTokenType());
        expressionInfos.add(Assignment.getTokenType());
        expressionInfos.add(Seperator.getTokenType());
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

        //Needs to be way down
        expressionInfos.add(Identifier.getTokenType());
    }

    //Todo: Make it configurable
    //Todo: Problem with =, does not get seperated yet
    private String[] separators = {"(", ")", ";", "[", "]", "{", "}", "+", "-", "*", "/", "==", "%", "||", "&&"};

    public String preprocess(String code){
        code = code.replace('\n', ' ');

        for(String c : separators)
            code = code.replace(c, " "+c+" ");

        code = code.replace(" {", " { ;");
        code = code.replace(" }", " } ;");

        if(!code.endsWith(";"))
            code += " ;";

        code = code.replace('\t', ' ');
        code = code.replace('\n', ' ');

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
}
