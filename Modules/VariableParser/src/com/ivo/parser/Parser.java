package com.ivo.parser;

import com.ivo.parser.ast.BinaryExpression;
import com.ivo.parser.ast.Expression;
import com.ivo.parser.ast.NumberExpression;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Parser {

    private final Token EOF = new Token(TokenType.EOF, "");
    
    private final List<Token> tokens;
    
    private final int size;
    private int pos;

    public Parser(List<Token> tokens, Double[] args) {
        this.tokens = tokens;
        size = tokens.size();
        
        pos = 0;
    }
    
    public List<Expression> parse() {
        List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(expression());
        }
        
        return result;
    }
    
    private Expression statement() {
        // function
        if (get(0).getTokenType() == TokenType.WORD &&
                get(1).getTokenType() == TokenType.LT) {
            return null;
        }
        
        return primary();
    }
    
    private Expression expression() {
        return additive();
    }
    
    private Expression additive() {
        Expression result = multiplicative();
        
        while (true) {
            // 2 * 6 * 3
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression multiplicative() {
        Expression result = primary();
        
        while (true) {
            // 2 * 6 * 3
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, primary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, primary());
                continue;
            }
            break;
        }
        
        return result;
    }


    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        }
        throw new RuntimeException("Unknown expression!");
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
    
    
}
