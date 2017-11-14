package com.ivo.web.modules.genetic.controllers;

import com.ivo.web.modules.genetic.beans.ArgsBean;
import com.ivo.web.modules.genetic.beans.FunctionBean;
import com.ivo.web.modules.genetic.beans.GeneticParamsBean;
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
public class GConstraintsController implements Serializable {

    private List<Integer> argsList;
    private List<Integer> constraintsList;
    
    private int countArgs;
    private int countConstraints;
    
    // constraints
    private final List<ArgsBean> argValues = new ArrayList<>();
    // goal func
    private final List<FunctionBean> funcValues = new ArrayList<>();
    
    // condition
    private String maxOrMin = "min";
    
    // input params for genetic algorithm
    private GeneticParamsBean params = new GeneticParamsBean();

    public GConstraintsController() {
    }
    
    
    public String handleFirstStep() {
        return "step2_genetic";
    }
    
    public String handleSecondStep() {
        for (int i = 0; i < argValues.size(); i++) {
            System.out.println(argValues.get(i).getValues());
        }
        String function = "";
        for (int i = 0; i < funcValues.size(); i++) {
            function += funcValues.get(i).getValue() + "*" + "x" + (i + 1) + "+";
        }
        // last pos is +
        // delete plus
        function = function.substring(0, function.length() - 1);
        System.out.println(function);
        return "step3_genetic";
    }
    
    public String handleThirdStep() {
        
        return "step4_genetic";
    }
    
    /*=====================================================*/
    public List<ArgsBean> setValuesArgs() {
        if (!argValues.isEmpty()) {
            argValues.clear();
        }
        for (int i = 0; i < countConstraints; i++) {
            argValues.add(new ArgsBean());
        }
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

    public String getMaxOrMin() {
        return maxOrMin;
    }

    public void setMaxOrMin(String maxOrMin) {
        this.maxOrMin = maxOrMin;
    }

    public GeneticParamsBean getParams() {
        return params;
    }

    public void setParams(GeneticParamsBean params) {
        this.params = params;
    }
}
