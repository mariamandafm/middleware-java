package main.java.ufrn.br.core;

import java.util.HashMap;
import java.util.Map;

// TODO: Implementar como singleton
public class Lookup {
    private static final Lookup instance = new Lookup();
    private Map<String, Object> registry = new HashMap<>();

    private Lookup(){}

    public static Lookup getInstance(){
        return instance;
    }

    public void registerObject(String objectId, Object serviceInstance){
        registry.put(objectId, serviceInstance);
        System.out.println("Objeto registrado: " + objectId);
    }

    public Object getObject(String objectId){
        return registry.get(objectId);
    }
}
