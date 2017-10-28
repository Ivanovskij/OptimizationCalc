package com.ivo.parser.ast;

/**
 *
 * @author IOAdmin
 */
public class EvalStatement implements Statement {

    private final Expression expression;

    public EvalStatement(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void execute() {
        System.out.println(expression.eval());
    }

    @Override
    public String toString() {
        return "EVAL = " + expression;
    }
    
}
