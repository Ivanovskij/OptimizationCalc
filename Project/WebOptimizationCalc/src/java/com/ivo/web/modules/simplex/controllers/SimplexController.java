package com.ivo.web.modules.simplex.controllers;

import com.ivo.beans.ResultBean;
import com.ivo.module.SimplexMethodForWeb;
import com.ivo.web.modules.simplex.beans.ArgsBean;
import com.ivo.web.modules.simplex.beans.FunctionBean;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class SimplexController {

    private List<ArgsBean> constraintsValues;
    private List<FunctionBean> funcValues;
    
    private int countConstraints;
    private int countArgs;
    
    private String maxOrMin;
    
    private double freeMemberC;
    
    private double resultGoalFunc;
    private double[] resultsX;

    
    public SimplexController(List<ArgsBean> constraintsValues, List<FunctionBean> funcValues,
            int countConstraints, int countArgs, String maxOrMin, double freeMemberC) 
    {
        if (constraintsValues == null || constraintsValues.isEmpty()) {
            throw new NullPointerException("constraintsValues not be null!");
        }
        if (funcValues == null || funcValues.isEmpty()) {
            throw new NullPointerException("funcValues not be null!");
        }
        
        this.constraintsValues = constraintsValues;
        this.funcValues = funcValues;
        this.countConstraints = countConstraints;
        this.countArgs = countArgs;
        
        this.maxOrMin = maxOrMin;
        
        this.freeMemberC = freeMemberC;
    }
    
    /**
     * Calculate simplex method with description
     * @return results - all iterations results
     */
    public List<ResultBean> calculate() {
        List<ResultBean> results;

        double[][] table = getTableValues();
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(table, freeMemberC);
        results = s.calculate();
        setResultGoalFunc(s.getResultGoalFunc());
        setResultsX(s.getResultsX());
        
        return results;
    }

    /**
     * Calculate simplex method with description
     * @return table - right table for simplex method
     *  last col containt: -1 or 1, dependens from conditions(max or min)
     */
    private double[][] getTableValues() {
        // read constraints value
        // + 1 goal function
        // + 1 valueCondition
        double[][] table = new double[countConstraints + 1][countArgs + 2];
        
        int n = table.length - 1;
        int m = table[0].length - 1;
        
        ArgsBean argBean;
        Double[] values;
        
        for (int i = 0; i < constraintsValues.size(); i++) {
            
            argBean = constraintsValues.get(i);
            values = argBean.getValues();
            
            // value constraint after condition
            table[i][0] = constraintsValues.get(i).getValueCondition();
            // values constraints
            for (int j = 0; j < values.length; j++) {
                table[i][j + 1] = values[j];
            }
            
            // last col, -1 or 1
            if (maxOrMin.equals("min")) {
                if (argBean.getCondition().equals("≤")) {
                    table[i][m] = -1;
                } else {
                    table[i][m] = 1;
                }
            }
            if (maxOrMin.equals("max")) {
                if (argBean.getCondition().equals("≥")) {
                    table[i][m] = -1;
                } else {
                    table[i][m] = 1;
                }
            }
        }
        
        // read the goal func values
        table[n][0] = 0;
        for (int i = 0; i < funcValues.size(); i++) {
            table[n][i+1] = funcValues.get(i).getValue(); 
        }
        if (maxOrMin.equals("max")) {
            table[n][m] = -1; 
        } else {
            table[n][m] = 1; 
        }
        
        return table;
    }
    
    /**
     *
     * @return resultGoalFunc
     */
    public double getResultGoalFunc() {
        return resultGoalFunc;
    }
    
    /**
     *
     * @return resultsX
     */
    public double[] getResultsX() {
        return resultsX;
    }
    
    /**
     *
     * @param value - goal func
     */
    public void setResultGoalFunc(double value) {
        this.resultGoalFunc = value;
    }
    
    /**
     *
     * @param values - result values x
     */
    public void setResultsX(double[] values) {
        this.resultsX = values;
    }
    
}
