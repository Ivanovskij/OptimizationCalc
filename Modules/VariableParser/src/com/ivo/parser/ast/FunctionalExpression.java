package com.ivo.parser.ast;

import com.ivo.lib.Function;
import com.ivo.lib.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class FunctionalExpression implements Expression {

    private final String name;
    private final List<Expression> args;

    public FunctionalExpression(String name) {
        this.name = name;
        this.args = new ArrayList<>();
    }
    
    public FunctionalExpression(String name, List<Expression> args) {
        this.name = name;
        this.args = args;
    }
    
    public void addArg(Expression arg) {
        args.add(arg);
    }

    @Override
    public double eval() {
        final Function result = Functions.get(name);
        
        int size = args.size();
        Double[] values = new Double[size];
        for (int i = 0; i < size; i++) {
            values[i] = args.get(i).eval();
        }
        
        return result.execute(values);
    }
    
}
