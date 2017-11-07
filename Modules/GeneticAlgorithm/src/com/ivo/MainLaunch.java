package com.ivo;

import com.ivo.module.Chromosome;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class MainLaunch {
    
    private static List<Chromosome> bestIndividuals;
    
    public static void main(String[] args) {
        bestIndividuals = new GeneticExecute("-10000", "10000", 
            "100", "1000", "2").execute();
    }
    
}
