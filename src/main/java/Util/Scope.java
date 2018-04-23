package Util;

import java.util.HashMap;

public class Scope {
    private Scope parent;
    private HashMap<String, Object> identifierMap = new HashMap<>();

    public Scope(Scope parent){
        this.parent = parent;
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
        if(parent != null && parent.containsIdentifier(id))
            parent.setValue(id, value);
        else
            identifierMap.put(id, value);
    }

    public void reset(){
        this.identifierMap.clear();
    }
}
