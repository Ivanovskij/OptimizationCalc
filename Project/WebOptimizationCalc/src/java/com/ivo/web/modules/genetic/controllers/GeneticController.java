package com.ivo.web.modules.genetic.controllers;

import com.ivo.GeneticExecute;
import com.ivo.module.Chromosome;
import com.ivo.utils.BinaryUtil;
import com.ivo.web.modules.genetic.beans.ArgsBean;
import com.ivo.web.modules.genetic.beans.FunctionBean;
import com.ivo.web.modules.genetic.beans.GeneticParamsBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class GeneticController {

    private int countArgs;
    private int countConstraints;
    
    // constraints
    private final List<ArgsBean> argValues;
    // goal func
    private final List<FunctionBean> funcValues;
    
    // condition
    private String maxOrMin = "min";
    
    // input params for genetic algorithm
    private GeneticParamsBean params;

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
    
    
    // return List<ResultBean>
    public void calculate() throws Exception {
        // set arg size
        params.setArg_size(String.valueOf(countArgs));

        // set params
        String xMin = params.getXmin();
        String xMax = params.getXmax();
        
        String max_generations = params.getMax_generations();
        String population_count = params.getPopulation_count();
        
        String arg_size = params.getArg_size();
        
        // make constraints
        String[] constraints = new String[countConstraints];
        for (int i = 0; i < argValues.size(); i++) {
            constraints[i] = argValues.get(i).getValues();
        }
        
        // make constraints WithOut Condition
        String[] constraintsWithOutCondition = new String[countConstraints];
        for (int i = 0; i < argValues.size(); i++) {
            constraintsWithOutCondition[i] = argValues.get(i).getValuesWithoutCondition();
        }
        
        // make function
        String function = "";
        for (int i = 0; i < funcValues.size(); i++) {
            function += funcValues.get(i).getValue() + "*" + "x" + (i + 1) + "+";
        }
        // last pos is +
        // delete plus
        function = function.substring(0, function.length() - 1);
        
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
        // check
        
        GeneticExecute ga = new GeneticExecute(xMin, xMax, max_generations, population_count, arg_size);
        List<Chromosome> b = ga.execute(function, constraints, constraintsWithOutCondition);
        for (Chromosome ch : b) {
            System.out.println("X = " + Arrays.toString(BinaryUtil.binaryArrToNumberArr(ch.getChromosomes())));
        }
    }
}
