package Expressions.Markers;

import Expressions.Expression;
import Tokenizer.ExpressionInfo;

public abstract class Marker extends Expression {

    @Override
    public void execute() {
        throw new RuntimeException("This should never execute!");
    }
}
