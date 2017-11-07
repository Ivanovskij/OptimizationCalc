package com.ivo.web.modules.simplex.controllers;


import com.ivo.module.SimplexMethod;
import com.ivo.web.modules.simplex.beans.ArgsBean;
import com.ivo.web.modules.simplex.beans.FunctionBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class ConstraintsController {

    private List<Integer> argsList;
    private List<Integer> constraintsList;
    
    // constraints
    private final List<ArgsBean> argValues = new ArrayList<>();
    // goal func
    private final List<FunctionBean> funcValues = new ArrayList<>();
    private double freeMemberC;
    
    // condition
    private String maxOrMin = "min";
    
    private int countArgs;
    private int countConstraints;
    
    /**
     * Creates a new instance of ConstraintsBean
     */
    public ConstraintsController() {
    }
    
    public String handleFirstStep() {
        if (countArgs != 0 && countConstraints != 0) {
            return "step2";
        }
        return "error_page";
    }
    
    public String handleSecondStep() {
        if (!argValues.isEmpty()) {
            double[] result = new double[countArgs];
            // + 1 goal function
            // + 1 valueCondition
            double[][] table = new double[countConstraints + 1][countArgs + 1];
            for (int i = 0; i < argValues.size(); i++) {
                table[i][0] = argValues.get(i).getValueCondition();
                Double[] values = argValues.get(i).getValues();
                for (int j = 0; j < values.length; j++) {
                    table[i][j + 1] = values[j];
                }
            }
            
            int n = table.length - 1;
            table[n][0] = 0;
            for (int i = 0; i < funcValues.size(); i++) {
                table[n][i+1] = funcValues.get(i).getValue();
            }
            
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    System.out.print(table[i][j] + " ");
                } 
                System.out.println("");
            }
            
            SimplexMethod s = new SimplexMethod(table);
            s.calculate(result);
            System.out.println("F = " + (result[0] * 21 + result[1] * 18 + result[2] * 16 + result[3] * 17.5));
            return "step3";
        }
        return "error_page";
    }

    public int getCountArgs() {
        return countArgs;
    }

    public void setCountArgs(int countArgs) {
        this.countArgs = countArgs;
        
        argsList = new ArrayList<>();
        for (int i = 0; i < countArgs; i++) {
            argsList.add(i);
        }
    }

    public int getCountConstraints() {
        return countConstraints;
    }

    public void setCountConstraints(int countConstraints) {
        this.countConstraints = countConstraints;
        
        constraintsList = new ArrayList<>();
        for (int i = 0; i < countConstraints; i++) {
            constraintsList.add(i);
        }
    }

    public List<Integer> getArgsList() {
        return argsList;
    }

    public List<Integer> getConstraintsList() {
        return constraintsList;
    }
    
    public List<ArgsBean> setValuesArgs() {
        if (!argValues.isEmpty()) {
            argValues.clear();
        }
        for (int i = 0; i < countConstraints; i++) {
            argValues.add(new ArgsBean());
        }
        return argValues;
    }

    public List<ArgsBean> getArgsValues() {
        return argValues;
    }
    
    public List<FunctionBean> setValuesFunc() {
        if (!funcValues.isEmpty()) {
            funcValues.clear();
        }
        // называем переменные
        // x1, x2, x3 ... xn
        for (int i = 0; i < countArgs; i++) {
            FunctionBean f = new FunctionBean();
            f.setNameArg(i + 1);
            funcValues.add(f);
        }
        return funcValues;
    }

    public List<FunctionBean> getFuncValues() {
        return funcValues;
    }

    public double getFreeMemberC() {
        return freeMemberC;
    }

    public void setFreeMemberC(double freeMemberC) {
        this.freeMemberC = freeMemberC;
    }

    public String getMaxOrMin() {
        return maxOrMin;
    }

    public void setMaxOrMin(String maxOrMin) {
        this.maxOrMin = maxOrMin;
    }
}
