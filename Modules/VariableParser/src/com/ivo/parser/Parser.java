package com.ivo.parser;

import com.ivo.parser.ast.AssignmentStatement;
import com.ivo.parser.ast.BinaryExpression;
import com.ivo.parser.ast.BlockStatement;
import com.ivo.parser.ast.ConditionalExpression;
import com.ivo.parser.ast.EvalStatement;
import com.ivo.parser.ast.Expression;
import com.ivo.parser.ast.FunctionalExpression;
import com.ivo.parser.ast.NumberExpression;
import com.ivo.parser.ast.Statement;
import com.ivo.parser.ast.UnaryExpression;
import com.ivo.parser.ast.VariableExpression;
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
    
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();

        pos = 0;
    }
    
    /*public List<Expression> parse() {
        List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(expression());
        }
        
        return result;
    }*/
    
    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }
    
    private Statement statement() {
        if (match(TokenType.EVAL)) {
            return new EvalStatement(expression());
        }
        return assignmentStatement();
    }
    
    private Statement assignmentStatement() {
        if (get(0).getTokenType() == TokenType.WORD && 
                get(1).getTokenType() == TokenType.EQ) {
            final String variable = consume(TokenType.WORD).getText();
            consume(TokenType.EQ);
            return new AssignmentStatement(variable, expression());
        }
        throw new RuntimeException("Unknown statement!");
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
        return conditional();
    }
    
    private Expression conditional() {
        Expression result = additive();
        
        if (match(TokenType.LT)) {
            return new ConditionalExpression(TokenType.LT, result, additive());
        }
        if (match(TokenType.LTEQ)) {
            return new ConditionalExpression(TokenType.LTEQ, result, additive());
        }
        if (match(TokenType.GT)) {
            return new ConditionalExpression(TokenType.GT, result, additive());
        }
        if (match(TokenType.GTEQ)) {
            return new ConditionalExpression(TokenType.GTEQ, result, additive());
        }
        
        return result;
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
            return new VariableExpression(current.getText());
        } else if (match(TokenType.LPAREN)) {
            Expression result = expression();
            if (!match(TokenType.RPAREN)) {
                throw new RuntimeException("Expected RPAREN");
            }
            return result;
        }
        throw new RuntimeException("Unknown expression!");
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
