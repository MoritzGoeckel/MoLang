package Expressions.Annotations;

import Expressions.Expression;
import Expressions.Markers.Marker;

public abstract class Annotation extends Marker{
    public abstract Expression Process(Expression expression);
}
