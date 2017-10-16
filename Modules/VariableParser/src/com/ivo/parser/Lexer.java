package com.ivo.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author IOAdmin
 */
public class Lexer {

    private final String inStr;
    private final int lengthStr;
    
    private final List<Token> tokens;
    
    private int pos;
    
    private static final String OPERATORS_CHARS = "+-*/=<>()";
    private static final Map<String, TokenType> OPERATORS;
    
    static {
        OPERATORS = new HashMap<>();
        
        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);
        
        OPERATORS.put(">", TokenType.LT);
        OPERATORS.put(">=", TokenType.LTEQ);
        OPERATORS.put("<", TokenType.GT);
        OPERATORS.put("<=", TokenType.GTEQ);
        
        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
    }
    
    
    public Lexer(String inStr) {
        this.inStr = inStr;
        lengthStr = inStr.length();
        
        tokens = new ArrayList<>();
        
        pos = 0;
    }
    

    public List<Token> parse() {
        while (pos < lengthStr) {
            final char current = peek(0);
            
            if (Character.isDigit(current) && Character.isLetter(peek(1))) {
                throw new RuntimeException("Variable not start with digit!");
            }
            else if (Character.isDigit(current)) {
                tokenizeNumber();
            } else if (Character.isLetter(current)) {
                tokenizeVariable();
            } else if (OPERATORS_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            }
            else {
                // whitespaces
                next();
            }
        }
        
        return tokens;
    }

    private void tokenizeNumber() {
        char current = peek(0);
        
        StringBuilder buff = new StringBuilder();
        while (true) {
            if (current == '.') {
                if (buff.indexOf(".") != -1) {
                    throw new RuntimeException("Invalid float number!");
                }
            } else if (!Character.isDigit(current)) {
                break;
            }
            buff.append(current);
            current = next(); 
        }
        
        addToken(TokenType.NUMBER, buff.toString());
    }

    private void tokenizeVariable() {
        char current = peek(0);
        
        StringBuilder buff = new StringBuilder();
        while (true) {
            if (!Character.isLetterOrDigit(current) && current != '_') {
                break;
            }
            buff.append(current);
            current = next();
        } 
        addToken(TokenType.WORD, buff.toString());
    }
    
    private void tokenizeOperator() {
        char current = peek(0);
        
        StringBuilder buff = new StringBuilder();
        while (true) {
            final String text = buff.toString();
            // input: <=+, out: <=
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                addToken(OPERATORS.get(text));
                return;
            }
            buff.append(current);
            current = next();
        }
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
    
    private void addToken(TokenType tokenType, String text) {
        tokens.add(new Token(tokenType, text));
    }
    
    private void addToken(TokenType tokenType) {
        tokens.add(new Token(tokenType, ""));
    }
    
}
