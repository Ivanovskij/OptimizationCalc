package com.ivo.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Lexer {

    private String inStr;
    private List<Token> tokens;

    public Lexer(String inStr) {
        this.inStr = inStr;
        tokens = new ArrayList<>();
    }
    
    
    public List<Token> parse() {
        
    }
    
}
