package Expressions.Markers;

import Expressions.ControlFlow.Procedure;
import Expressions.Expression;
import Tokenizer.ExpressionInfo;
import Util.Scope;

public class ProcedureBracketsOpen extends Procedure {

    public ProcedureBracketsOpen(Scope parent) {
        super(parent);
    }

    public static ExpressionInfo getTokenType(){
        return new ExpressionInfo("ProcedureBracketsOpen", x -> x.equals("{"), 0, false, true);
    }
}
