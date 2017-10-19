package com.ivo.parser.util;

import com.ivo.parser.Token;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author IOAdmin
 */
public class CacheStringFunctions {

    private static final Map<String, List<Token>> mapFunctions;
    
    static {
        mapFunctions = new HashMap<>();
    }
    
    public static void add(String name, List<Token> tokens) {
        mapFunctions.put(name, tokens);
    }
    
    public static List<Token> get(String name) {
        if (!isExists(name)) { /* nop */ }
        return mapFunctions.get(name);
    }
    
    public static boolean isExists(String name) {
        return mapFunctions.containsKey(name);
    }
}
