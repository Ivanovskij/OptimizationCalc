package com.ivo.web.modules.genetic.beans;

import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class FunctionBean implements Serializable {

    private final StringBuilder function = new StringBuilder();

    public FunctionBean() {
    }

    public void setX(String x) {
        this.function.append(x).append("*").append(function.length()).append("+");
    }
    
    public String getFunction() {
        // int last position
        // the result is plus
        String rightFunction = function.substring(0, function.length() - 1);
        return rightFunction;
    }
}
