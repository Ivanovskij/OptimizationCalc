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
    
    public void add(Statement s) {
        statements.add(s);
    }
    
    @Override
    public void execute() {
        for (Statement st : statements) {
            st.execute();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Statement st : statements) {
            result.append(st.toString());
        }
        return result.toString();
    }
}
