package com.ivo;

import com.ivo.module.GeneticResult;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class MainLaunch {
    
    private static List<GeneticResult> results;
    
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
        
        /*String goalFunction = "82.5 * x1 + 70 * x2 + 78 * x3";
        
        String[] constraints = {
            "2.5 * x1 + 1.8 * x2 + 2 * x3 <= 2250",
            "2 * x1 + 1.6 * x2 + 2.2 * x3 <= 2250",
            "1 * x1 + 0 * x2 + 0 * x3 >= 200",
            "0 * x1 + 1 * x2 + 0 * x3 >= 200",
            "0 * x1 + 0 * x2 + 1 * x3 >= 200",
        };
        
        String[] constraintsWithOutCondition = {
            "2.5 * x1 + 1.8 * x2 + 2 * x3",
            "2 * x1 + 1.6 * x2 + 2.2 * x3",
            "1 * x1 + 0 * x2 + 0 * x3",
            "0 * x1 + 1 * x2 + 0 * x3",
            "0 * x1 + 0 * x2 + 1 * x3",
        };*/
        
        
        results = new GeneticExecute("-1000", "1000", 
            "100", "3000", "4").execute(goalFunction, constraints, constraintsWithOutCondition);
        
        /*for (GeneticResult r : results) {
            System.out.println(r.getResultBestIndividual());
            System.out.println(r.getCountSelectionChildren());
            System.out.println(r.getCountSelectionParent());
        }*/
    }
    
}
