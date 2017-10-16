package com.ivo.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Lexer {

    private final String inStr;
    private final int lengthStr;
    
    private final List<Token> tokens;
    
    private int pos;
    
    public Lexer(String inStr) {
        this.inStr = inStr;
        lengthStr = inStr.length();
        
        tokens = new ArrayList<>();
        
        pos = 0;
    }
    
    
    public List<Token> parse() {
        while (pos < lengthStr) {
            final int cur = inStr.charAt(pos);
            
            if (Character.isDigit(cur)) {
                //tokenizeNumber();
            } else if (Character.isLetterOrDigit(cur)) {
                
            }
        }
        
        return tokens;
    }

    private char next() {
        pos++;
        return peek(0);
    }

    private char peek(int offsetPos) {
        final int position = pos + offsetPos;
        if (position >= lengthStr) return '\0';
        return inStr.charAt(position);
    }
    
}
