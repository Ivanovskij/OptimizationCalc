package com.ivo.web.modules.simplex.beans;

import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class ArgsBean implements Serializable {
    
    private final StringBuilder args = new StringBuilder();
    private String condition;
    private Double valueCondition;

    public ArgsBean() {
    }
    
    public Double[] getValues() {
        String[] valuesStr = args.toString().split(" ");
        Double[] valuesDouble = new Double[valuesStr.length];
        
        for (int i = 0; i < valuesStr.length; i++) {
            valuesDouble[i] = Double.parseDouble(valuesStr[i]);
        }
        
        return valuesDouble;
    }
    
    public String getX() {
        return args.toString();
    }

    public void setX(String x) {
        this.args.append(x).append(" ");
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getValueCondition() {
        return valueCondition;
    }

    public void setValueCondition(Double valueCondition) {
        this.valueCondition = valueCondition;
    }
}
