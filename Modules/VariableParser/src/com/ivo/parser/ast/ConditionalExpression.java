package com.ivo.parser.ast;

import com.ivo.parser.TokenType;

/**
 *
 * @author IOAdmin
 */
public class ConditionalExpression implements Expression {

    private final TokenType type;
    private final Expression expr1;
    private final Expression expr2;

    public ConditionalExpression(TokenType type, Expression expr1, Expression expr2) {
        this.type = type;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public double eval() {
        Double value1 = expr1.eval();
        Double value2 = expr2.eval();
        
        boolean result;
        switch (type) {
            case EQ: result = value1.equals(value2); break;
            case LT: result = value1 > value2; break;
            case LTEQ: result = value1 >= value2; break;
            case GT: result = value1 < value2; break;
            case GTEQ: result = value1 <= value2; break;
            default: throw new RuntimeException("Expected conditional: " + type.name());
        }
        
        return (result) ? 1 : 0;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", expr1.eval(), type.name(), expr2.eval());
    }
}
