package com.ivo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IOAdmin
 */
public class Parameters {
    
    private static final Map<String, NumberValue> params;
    
    static {
        params = new HashMap<>();
        
        params.put(Config.BINARY_GENE_SIZE, new NumberValue("64"));
        params.put(Config.GENE_SIZE, new NumberValue("8"));
        params.put(Config.MUTATION_PERCENT, new NumberValue("0.2"));
    }
    
    public static void set(String name, String value) {
        params.put(name, new NumberValue(value));
    }
    
    public static NumberValue get(String name) {
        if (!isExists(name)) {
            throw new RuntimeException("Invalid param: " + name);
        }
        
        return params.get(name);
    }

    private static boolean isExists(String name) {
        return params.containsKey(name);
    }
}
