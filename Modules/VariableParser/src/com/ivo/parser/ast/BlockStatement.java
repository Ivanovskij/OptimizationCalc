package com.ivo.parser.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class BlockStatement implements Statement {

    private final List<Statement> statements;

    public BlockStatement() {
        this.statements = new ArrayList<>();
    }
    
    public void add(Statement st) {
        statements.add(st);
    }

    @Override
    public void execute() {
        statements.forEach((st) -> {
            st.execute();
        });
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        statements.forEach((st) -> {
            buff.append(st.toString()).append(System.lineSeparator());
        });
        return buff.toString();
    }
    
}
