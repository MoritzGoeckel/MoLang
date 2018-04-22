import Expressions.Values.Identifier;

import java.util.HashMap;

public class Context {
    private HashMap<String, Identifier> identifierMap = new HashMap<>();

    public Context(){

    }

    public Identifier getIdentifier(String id){
        if(!identifierMap.containsKey(id))
            identifierMap.put(id, new Identifier(id));

        return identifierMap.get(id);
    }
}
