package com.ivo.web.modules.simplex.beans;

import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class ArgsBean implements Serializable {
    
    private final StringBuilder arg = new StringBuilder();

    public ArgsBean() {
    }
    
    public Double[] getValues() {
        String[] valuesStr = arg.toString().split(" ");
        Double[] valuesDouble = new Double[valuesStr.length];
        
        for (int i = 0; i < valuesStr.length; i++) {
            valuesDouble[i] = Double.parseDouble(valuesStr[i]);
        }
        
        return valuesDouble;
    }
    
    public String getX() {
        return arg.toString();
    }

    public void setX(String x) {
        this.arg.append(x).append(" ");
    }

}
