package com.ivo.parser.ast;

import com.ivo.lib.Variables;

/**
 *
 * @author IOAdmin
 */
public class VariableExpression implements Expression {

    private String name;
    
    public VariableExpression(String name) {
        this.name = name;
    }
    
    @Override
    public double eval() {
        if (!Variables.isExists(name)) {
            throw new RuntimeException("Invalid argument: " + name);
        }
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }

}
