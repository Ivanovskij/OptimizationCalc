package com.ivo.web.modules.genetic.beans;

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
    
    public String getX() {
        // int last position
        // the result is plus
        String rightArgs = args.substring(0, args.length() - 1);
        return rightArgs;
    }

    public void setX(String x) {
        this.args.append(x).append("*").append(args.length()).append("+");
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
