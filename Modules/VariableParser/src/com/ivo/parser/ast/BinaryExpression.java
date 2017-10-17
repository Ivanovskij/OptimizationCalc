package com.ivo.parser.ast;

/**
 *
 * @author IOAdmin
 */
public class BinaryExpression implements Expression {

    private final Expression expr1;
    private final Expression expr2;
    private final char operation;

    public BinaryExpression(char operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    
    @Override
    public double eval() {
        switch (operation) {
            case '+': return expr1.eval() + expr2.eval();
            case '-': return expr1.eval() - expr2.eval();
            case '*': return expr1.eval() * expr2.eval();
            case '/': return expr1.eval() / expr2.eval();
            default:
                throw new RuntimeException("Unknown operation expression!");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %c %s", expr1, operation, expr2);
    }
}
