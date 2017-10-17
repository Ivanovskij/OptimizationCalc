package com.ivo.parser.ast;

/**
 *
 * @author IOAdmin
 */
public class EvalStatement implements Statement {

    private final Expression expr;

    public EvalStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void execute() {
        expr.eval();
    }
    
    @Override
    public String toString() {
        return String.format("%s", expr.eval());
    }
}
