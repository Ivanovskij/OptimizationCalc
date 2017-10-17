package com.ivo.lib;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IOAdmin
 */
public class Functions {

    private static Map<String, Function> functions;

    public Functions() {
        functions = new HashMap<>();
        
        functions.put("sin", new Function() {
            @Override
            public double execute(Double... args) {
                if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
                return Math.sin(args[0]);
            }
        });
    }
    
    public static Function get(String key) {
        if (!isExists(key)) throw new RuntimeException("Unknown function: " + key);
        return functions.get(key);
    }

    private static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    
}
