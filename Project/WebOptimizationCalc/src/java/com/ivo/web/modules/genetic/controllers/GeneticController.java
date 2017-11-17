package com.ivo.web.modules.genetic.controllers;

import com.ivo.GeneticExecute;
import com.ivo.module.GeneticResult;
import com.ivo.web.modules.genetic.beans.ArgsBean;
import com.ivo.web.modules.genetic.beans.FunctionBean;
import com.ivo.web.modules.genetic.beans.GeneticParamsBean;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class GeneticController implements Serializable {

    private int countArgs;
    private int countConstraints;
    
    // constraints
    private List<ArgsBean> argValues;
    // goal func
    private List<FunctionBean> funcValues;
    
    // condition
    private String maxOrMin = "min";
    
    // input params for genetic algorithm
    private GeneticParamsBean params;

    public GeneticController() {
    }

    public GeneticController(int countArgs, int countConstraints, 
            List<ArgsBean> argValues, List<FunctionBean> funcValues, GeneticParamsBean params) 
    {
        if (argValues == null) {
            throw new NullPointerException("argValues not be null!");
        }
        if (funcValues == null) {
            throw new NullPointerException("funcValues not be null!");
        }
        if (params == null) {
            throw new NullPointerException("params not be null!");
        }
        
        this.countArgs = countArgs;
        this.countConstraints = countConstraints;
        this.argValues = argValues;
        this.funcValues = funcValues;
        this.params = params;
    }
    
    public List<GeneticResult> calculate() throws Exception {
        setCountArgs();

        // set params
        String xMin = params.getXmin();
        String xMax = params.getXmax();
        
        String max_generations = params.getMax_generations();
        String population_count = params.getPopulation_count();
        
        String arg_size = params.getArg_size();
        
        String tmpMaxOrMin = params.getMaxOrMin();
        if (tmpMaxOrMin.equals("min")) {
            tmpMaxOrMin = "0";
        } else {
            tmpMaxOrMin = "1";
        }
        
        // make constraints
        String[] constraints = getContstraints();
        
        // make constraints WithOut Condition
        String[] constraintsWithOutCondition = getConstraintsWithOutCondition();
        
        // make function
        String function = getFunction();
        
        /*for (String c : constraints) {
            System.out.println(c);
        }
        System.out.println("=======");
        for (String c : constraintsWithOutCondition) {
            System.out.println(c);
        }
        System.out.println("====");
        System.out.println(function);
        */
        
        // get results
        GeneticExecute ga = new GeneticExecute(xMin, xMax, max_generations, 
                population_count, arg_size, tmpMaxOrMin);
        List<GeneticResult> results = ga.execute(function, constraints, constraintsWithOutCondition);
        return results;
    }

    private String getFunction() {
        String function = "";
        for (int i = 0; i < funcValues.size(); i++) {
            function += funcValues.get(i).getValue() + "*" + "x" + (i + 1) + "+";
        }
        // last pos is +
        // delete plus
        function = function.substring(0, function.length() - 1);
        return function;
    }
    
    private String[] getContstraints() throws Exception {
        String[] constraints = new String[countConstraints];
        for (int i = 0; i < argValues.size(); i++) {
            constraints[i] = argValues.get(i).getValues();
        }
        return constraints;
    }
    
    private String[] getConstraintsWithOutCondition() throws Exception {
        String[] constraintsWithOutCondition = new String[countConstraints];
        for (int i = 0; i < argValues.size(); i++) {
            constraintsWithOutCondition[i] = argValues.get(i).getValuesWithoutCondition();
        }
        return constraintsWithOutCondition;
    }

    private void setCountArgs() {
        // set arg size
        params.setArg_size(String.valueOf(countArgs));
    }
}
