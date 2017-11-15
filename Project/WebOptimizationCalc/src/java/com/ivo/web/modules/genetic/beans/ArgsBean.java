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
    
    private int counterArg = 1;
    
    public ArgsBean() {
    }
    
    /**
     *
     * @return rightArgs String - right args for parser
     */
    public String getValues() throws Exception {
        // int last position
        // the result is plus
        String rightArgs = args.substring(0, args.length() - 1);
        
        // need for not change original condition in session
        String tmpCondition = "none";
        if (condition.equals("≤")) {
            tmpCondition = "<=";
        } else if (condition.equals("≥")){
            tmpCondition = ">=";
        } else {
            throw new Exception("Неизвестное условие: " + condition);
        }
        rightArgs += " " + tmpCondition + " " + String.valueOf(valueCondition);
        return rightArgs;
    }
    
    /**
     *
     * @return rightValueWithoutCondition String - right value without condition for parser
     */
    public String getValuesWithoutCondition() {
        // int last position
        // the result is plus
        String rightValueWithoutCondition = args.substring(0, args.length() - 1);
        return rightValueWithoutCondition;
    }
    
    public String getX() {
        return args.toString();
    }

    public void setX(String x) {
        this.args.append(x).append("*").append("x").append(counterArg++).append("+");
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
