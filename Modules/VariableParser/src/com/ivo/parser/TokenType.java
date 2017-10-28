package com.ivo.parser;

/**
 *
 * @author IOAdmin
 */
public enum TokenType {
    
    NUMBER,
    WORD,
    
    EVAL,
    
    PLUS,
    MINUS,
    STAR,
    SLASH,
    
    EQ,     // =
    LT,     // >
    LTEQ,   // >=
    GT,     // <
    GTEQ,   // <=
    
    LPAREN,
    RPAREN,
    
    COMMA,
    
    EOF
    
}
