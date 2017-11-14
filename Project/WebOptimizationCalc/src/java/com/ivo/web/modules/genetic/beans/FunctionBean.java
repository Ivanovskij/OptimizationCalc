package com.ivo.web.modules.genetic.beans;

import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class FunctionBean implements Serializable {

    private int nameArg;
    private final StringBuilder function = new StringBuilder();

    public FunctionBean() {
    }

    public void setFunction(String x) {
        this.function.append(x).append("*").append(function.length()).append("+");
    }
    
    /**
     *
     * @return rightFunction String - right function for parser
     */
    public String getRightFunction() {
        // int last position
        // the result is plus
        String rightFunction = function.substring(0, function.length() - 1);
        return rightFunction;
    }
    
    public String getFunction() {
        return function.toString();
    }

    public int getNameArg() {
        return nameArg;
    }

    public void setNameArg(int nameArg) {
        this.nameArg = nameArg;
    }
}
