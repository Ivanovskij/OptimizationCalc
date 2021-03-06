package com.ivo;

import com.ivo.module.Chromosome;
import com.ivo.module.GeneticAlgorithm;
import com.ivo.module.GeneticResult;
import com.ivo.utils.Config;
import com.ivo.utils.Parameters;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class GeneticExecute {

    private List<GeneticResult> results;
    
    public GeneticExecute(String xMin, String xMax,
            String max_generations, String population_count,
            String argSize, String maxOrMin) 
    {
        Parameters.set("xMin", xMin);
        Parameters.set("xMax", xMax);
        
        Parameters.set("max_generations", max_generations);
        Parameters.set("population_count", population_count);
        
        Parameters.set("arg_size", argSize);
        
        // min = 0, max = 1
        Parameters.set(Config.MAX_OR_MIN, maxOrMin);
    }
    
    public List<GeneticResult> execute(String goalFunction, String[] constraints, String[] constraintsWithOutCondition) {
        results = new GeneticAlgorithm(goalFunction, constraints, constraintsWithOutCondition).solve();
        return results;
    }
    
    
}
