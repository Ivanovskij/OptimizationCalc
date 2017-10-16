package com.ivo.parser;

import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Parser {

    private final Token EOF = new Token(TokenType.EOF, "");
    
    private List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    public double parse() {
        while (!match(TokenType.EOF)) {
            
        }
        
        return 0;
    }

    private boolean match(TokenType tokenType) {
        return false;
    }
    
}
