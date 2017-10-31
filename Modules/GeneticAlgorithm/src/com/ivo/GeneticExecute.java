package com.ivo;

import com.ivo.module.Chromosome;
import com.ivo.module.GeneticAlgorithm;
import com.ivo.utils.Parameters;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class GeneticExecute {

    private List<Chromosome> bestIndividuals;
    
    public GeneticExecute(String xMin, String xMax,
            String max_generations, String population_count,
            String argSize) 
    {
        Parameters.set("xMin", xMin);
        Parameters.set("xMax", xMax);
        
        Parameters.set("max_generations", max_generations);
        Parameters.set("population_count", population_count);
        
        Parameters.set("arg_size", argSize);
    }
    
    public List<Chromosome> execute() {
        bestIndividuals = new GeneticAlgorithm().solve();
        return bestIndividuals;
    }
    
    
}
