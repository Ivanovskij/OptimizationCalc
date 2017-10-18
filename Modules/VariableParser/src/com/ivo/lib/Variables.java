package com.ivo.lib;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IOAdmin
 */
public class Variables {
    
    private static Map<String, Double> variables;
    
    static {
        variables = new HashMap<>();
    }

    public static void set(String key, Double value) {
        variables.put(key, value);
    }
    
    public static Double get(String key) {
        if (!isExists(key)) throw new RuntimeException("Invalid argument: " + key);
        return variables.get(key);
    }
    
    public static boolean isExists(String key) {
        return variables.containsKey(key);
    }
    
    public static void removeAll() {
        variables.clear();
    }
}
