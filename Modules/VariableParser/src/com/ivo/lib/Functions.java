package com.ivo.lib;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IOAdmin
 */
public class Functions {

    private static Map<String, Function> functions;

    static {
        functions = new HashMap<>();
        
        functions.put("sin", new Function() {
            @Override
            public double execute(Double... args) {
                if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
                return Math.sin(args[0]);
            }
        });
        
        functions.put("cos", (Function) (Double... args) -> {
            if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
            return Math.cos(args[0]);
        });
        
        functions.put("tg", (Function) (Double... args) -> {
            if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
            return Math.tan(args[0]);
        });
        
        functions.put("ctg", (Function) (Double... args) -> {
            if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
            // according to the formula: ctg = 1 / tg(a)
            return ( 1 / Math.tan(args[0]) );
        });
        
        functions.put("exp", (Function) (Double... args) -> {
            if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
            return Math.exp(args[0]);
        });
        
        functions.put("pow", (Function) (Double... args) -> {
            if (args.length != 2) throw new RuntimeException("Expected 2 argument!");
            return Math.pow(args[0], args[1]);
        });
        
        functions.put("sqrt", (Function) (Double... args) -> {
            if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
            return Math.sqrt(args[0]);
        });
        
        functions.put("log", (Function) (Double... args) -> {
            if (args.length != 1) throw new RuntimeException("Expected 1 argument!");
            return Math.log(args[0]);
        });
    }
    
    public static Function get(String key) {
        if (!isExists(key)) throw new RuntimeException("Unknown function: " + key);
        return functions.get(key);
    }

    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    
}
