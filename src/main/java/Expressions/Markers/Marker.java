package Expressions.Markers;

import Expressions.Expression;
import Util.Scope;

public abstract class Marker extends Expression {

    @Override
    public void execute(Scope scope) {
        throw new RuntimeException("This should never execute!");
    }
}
