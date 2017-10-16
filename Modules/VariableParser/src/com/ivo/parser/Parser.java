package com.ivo.parser;

import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Parser {

    private final Token EOF = new Token(TokenType.EOF, "");
    
    private final List<Token> tokens;
    
    private int size;
    private int pos;

    public Parser(List<Token> tokens, Double[] args) {
        this.tokens = tokens;
        size = tokens.size();
        
        pos = 0;
    }
    
    public double parse() {
        double result;
        while (!match(TokenType.EOF)) {
            result = statement();
        }
        
        return 0;
    }

    private boolean match(TokenType tokenType) {
        final Token current = get(0);
        if (tokenType != current.getTokenType()) { return false; }
        pos++;
        return true;
    }
    
    private Token get(int offsetPosition) {
        final int position = pos + offsetPosition;
        if (position >= size) {
            return EOF;
        }
        return tokens.get(position);
    }

    private double statement() {
        if (get(0).getTokenType() == TokenType.WORD) {
            
        }
    }
    
    
}
