package com.ivo.web.modules.simplex.beans;

import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class FunctionBean implements Serializable {
    
    private int nameArg;
    private double value;

    public FunctionBean() {
    }

    public int getNameArg() {
        return nameArg;
    }

    public void setNameArg(int nameArg) {
        this.nameArg = nameArg;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
