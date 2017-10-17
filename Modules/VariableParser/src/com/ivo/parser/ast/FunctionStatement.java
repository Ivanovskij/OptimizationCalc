package com.ivo.parser.ast;

/**
 *
 * @author IOAdmin
 */
public class FunctionStatement implements Statement {

    private final FunctionalExpression function;

    public FunctionStatement(FunctionalExpression functionalExpression) {
        this.function = functionalExpression;
    }

    @Override
    public void execute() {
        function.eval();
    }

    @Override
    public String toString() {
        return function.toString();
    }
}
