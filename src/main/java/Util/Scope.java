package Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Scope {
    private Scope parent;
    private HashMap<String, Object> identifierMap = new HashMap<>();
    private Set<String> localVars = new HashSet<>();

    private Object returnValue;

    private boolean stopEvaluating = false;
    private boolean isSecondOrderScope;

    public Scope(Scope parent, boolean isSecondOrderScope){
        this.parent = parent;
        this.isSecondOrderScope = isSecondOrderScope;
    }

    public Object getValue(String id){
        if(identifierMap.containsKey(id)) {
            return identifierMap.get(id);
        }
        else if(parent != null){
            return parent.getValue(id);
        }

        return null;
    }

    private boolean containsIdentifier(String id){
        if(identifierMap.containsKey(id))
            return true;
        else if(parent != null)
            return parent.containsIdentifier(id);
        else
            return false;
    }

    public void setValue(String id, Object value){
        if(parent != null && !localVars.contains(id) && parent.containsIdentifier(id))
            parent.setValue(id, value);
        else
            identifierMap.put(id, value);
    }

    public void setValueLocal(String id, Object value) {
        identifierMap.put(id, value);
        localVars.add(id);
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public boolean isStopEvaluating() {
        return stopEvaluating;
    }

    public void triggerStopEvaluating() {
        this.stopEvaluating = true;
        if(isSecondOrderScope) {
            parent.setReturnValue(getReturnValue());
            parent.triggerStopEvaluating();
        }
    }
}
