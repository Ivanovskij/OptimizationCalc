package com.ivo.parser;

import com.ivo.lib.Functions;
import com.ivo.parser.ast.BinaryExpression;
import com.ivo.parser.ast.Expression;
import com.ivo.parser.ast.FunctionalExpression;
import com.ivo.parser.ast.NumberExpression;
import com.ivo.parser.ast.UnaryExpression;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Parser {

    private final Token EOF = new Token(TokenType.EOF, "");
    
    private final List<Token> tokens;
    private final Double[] args;
    
    private final int size;
    private int pos;

    public Parser(List<Token> tokens, Double... args) {
        this.tokens = tokens;
        size = tokens.size();
        
        this.args = args;
        
        pos = 0;
    }
    
    public List<Expression> parse() {
        List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(expression());
        }
        
        return result;
    }
    
    private FunctionalExpression function() {
        final String name = consume(TokenType.WORD).getText();
        consume(TokenType.LPAREN);
        final FunctionalExpression function = new FunctionalExpression(name);
        while (!match(TokenType.RPAREN)) {
            function.addArg(expression());
            match(TokenType.COMMA);
        }
        return function;
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
        Expression result = unary();
        
        while (true) {
            // 2 * 6 * 3
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        return primary();
    }


    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        } else if (get(0).getTokenType() == TokenType.WORD &&
                get(1).getTokenType() == TokenType.LPAREN) {
            return function();
        } else if (match(TokenType.WORD)) {
            List<String> variablesName = variablesCount();
            int variablesCount = variablesName.size();
            
            if (variablesCount != args.length) {
                throw new RuntimeException("Expected " + variablesCount + " arguments!");
            }

            setVariableValueByArg(variablesName);
            
            return new NumberExpression(Double.parseDouble(current.getText()));
        } else if (match(TokenType.LPAREN)) {
            Expression result = expression();
            if (!match(TokenType.RPAREN)) {
                throw new RuntimeException("Expected RPAREN");
            }
            return result;
        }
        throw new RuntimeException("Unknown expression!");
    }

    private void setVariableValueByArg(List<String> variablesName) {
        int argCounter = 0;

        for (String varName : variablesName) {
            for (Token token : tokens) {
                if (!Functions.isExists(varName) && 
                        token.getText().equals(varName)) 
                {
                    token.setText(String.valueOf(args[argCounter]));
                }
            }
            argCounter++;
        }
            
    }
    
    private List<String> variablesCount() {        
        // delete duplicates variables name
        List<String> variablesName = new ArrayList<>();
        String varName;
        
        for (Token token : tokens) {
            varName = token.getText();
            if (!Functions.isExists(varName) && 
                    token.getTokenType() == TokenType.WORD &&
                    variablesName.indexOf(varName) == -1) 
            {
                variablesName.add(varName);
            }
        }

        return variablesName;
    }
    
    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getTokenType()) {
            throw new RuntimeException("Token current " + current + " doesn't match " + type);
        }
        pos++;
        return current;
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
