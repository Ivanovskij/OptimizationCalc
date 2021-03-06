package com.ivo.web.modules.simplex.controllers;


import com.ivo.beans.ResultBean;
import com.ivo.web.modules.simplex.beans.ArgsBean;
import com.ivo.web.modules.simplex.beans.FunctionBean;
import java.io.Serializable;
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
public class SConstraintsController implements Serializable {

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
    
    // results
    // first generate table for simplex
    // need for simplex for word
    private double[][] table;       
    private List<ResultBean> results;
    private double resultGoalFuncDouble;
    private double[] resultsXDouble;
    private int resultGoalFuncInt;
    private int[] resultsXInt;
    
    /**
     * Creates a new instance of ConstraintsBean
     */
    public SConstraintsController() {
    }
    
    public String handleFirstStep() {
        return "step2";
    }
    
    public String handleSecondStep() throws Exception {
        if (!argValues.isEmpty()) {
            SimplexController sc = new SimplexController(argValues, funcValues, countConstraints, 
                    countArgs, maxOrMin, freeMemberC);
            
            try {
                results = sc.calculate();
                this.resultGoalFuncDouble = sc.getResultGoalFuncDouble();
                this.resultsXDouble = sc.getResultsXDouble();
                this.resultGoalFuncInt = sc.getResultGoalFuncInt();
                this.resultsXInt = sc.getResultsXInt();
                this.table = sc.getTableValues();
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
            
            return "step3";
        }
        return "error";
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
        // create names for args
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

    public List<ResultBean> getResults() {
        return results;
    }

    public void setResults(List<ResultBean> results) {
        this.results = results;
    }

    public double getResultGoalFuncDouble() {
        return resultGoalFuncDouble;
    }

    public void setResultGoalFuncDouble(double resultGoalFuncDouble) {
        this.resultGoalFuncDouble = resultGoalFuncDouble;
    }

    public Double[] getResultsX() {
        Double[] result = new Double[resultsXDouble.length];
        for (int i = 0; i < resultsXDouble.length; i++) {
            result[i] = new Double(resultsXDouble[i]);
        }
        return result;
    }

    public void setResultsXDouble(double[] resultsXDouble) {
        this.resultsXDouble = resultsXDouble;
    }

    public double getResultGoalFuncInt() {
        return resultGoalFuncInt;
    }

    public void setResultGoalFuncInt(int resultGoalFuncInt) {
        this.resultGoalFuncInt = resultGoalFuncInt;
    }

    public int[] getResultsXInt() {
        return resultsXInt;
    }

    public void setResultsXInt(int[] resultsXInt) {
        this.resultsXInt = resultsXInt;
    }

    public double[][] getTable() {
        return table;
    }
}
