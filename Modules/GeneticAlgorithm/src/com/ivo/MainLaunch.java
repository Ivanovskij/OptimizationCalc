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
        String goalFunction = "21 * x1 + 18 * x2 + 16 * x3 + 17.5 * x4";
        
        String[] constraints = {
            "8 * x1 + 7 * x2 + 5 * x3 + 9 * x4 <= 22",
            "8 * x1 + 9 * x2 + 7 * x3 + 8 * x4 <= 25",
            "10 * x1 + 9* x2 + 9 * x3 + 7 * x4 <= 38",
            "10 * x1 + 11 * x2 +  11 * x3 + 6 * x4 <= 30",
            "1 * x1 + 0 * x2 + 0 * x3 + 0 * x4 >= 0",
            "0 * x1 + 1 * x2 + 0 * x3 + 0 * x4 >= 0",
            "0 * x1 + 0 * x2 + 1 * x3 + 0 * x4 >= 0",
            "0 * x1 + 0 * x2 + 0 * x3 + 1 * x4 >= 0",
        };
        
        String[] constraintsWithOutCondition = {
            "8 * x1 + 7 * x2 + 5 * x3 + 9 * x4",
            "8 * x1 + 9 * x2 + 7 * x3 + 8 * x4",
            "10 * x1 + 9* x2 + 9 * x3 + 7 * x4",
            "10 * x1 + 11 * x2 +  11 * x3 + 6 * x4",
            "1 * x1 + 0 * x2 + 0 * x3 + 0 * x4",
            "0 * x1 + 1 * x2 + 0 * x3 + 0 * x4",
            "0 * x1 + 0 * x2 + 1 * x3 + 0 * x4",
            "0 * x1 + 0 * x2 + 0 * x3 + 1 * x4",
        };
        
        
        
        bestIndividuals = new GeneticExecute("-10000", "10000", 
            "100", "10000", "4").execute(goalFunction, constraints, constraintsWithOutCondition);
    }
    
}
