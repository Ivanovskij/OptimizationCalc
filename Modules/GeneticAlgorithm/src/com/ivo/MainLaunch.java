package com.ivo;

import com.ivo.module.GeneticResult;
import java.util.Arrays;
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
       
//        String goalFunction = "82.5 * x1 + 70 * x2 + 78 * x3";
//        
//        String[] constraints = {
//            "2.5 * x1 + 1.8 * x2 + 2 * x3 <= 2250",
//            "2 * x1 + 1.6 * x2 + 2.2 * x3 <= 2250",
//            "1 * x1 + 0 * x2 + 0 * x3 >= 200",
//            "0 * x1 + 1 * x2 + 0 * x3 >= 200",
//            "0 * x1 + 0 * x2 + 1 * x3 >= 200",
//        };
//        
//        String[] constraintsWithOutCondition = {
//            "2.5 * x1 + 1.8 * x2 + 2 * x3",
//            "2 * x1 + 1.6 * x2 + 2.2 * x3",
//            "1 * x1 + 0 * x2 + 0 * x3",
//            "0 * x1 + 1 * x2 + 0 * x3",
//            "0 * x1 + 0 * x2 + 1 * x3",
//        };
        
//       String goalFunction = "40 * x1 + 50 * x2 + 30 * x3 + 20 * x4";
//        
//        String[] constraints = {
//            "3 * x1 + 5 * x2 + 2 * x3 + 7 * x4 <= 150",
//            "4 * x1 + 3 * x2 + 3 * x3 + 5 * x4 <= 90",
//            "5 * x1 + 6 * x2 + 4 * x3 + 8 * x4 <= 240",
//            "1 * x1 + 0 * x2 + 0 * x3 + 0 * x4 >= 3",
//            "0 * x1 + 1 * x2 + 0 * x3 + 0 * x4 >= 3",
//            "0 * x1 + 0 * x2 + 1 * x3 + 0 * x4 >= 3",
//            "0 * x1 + 0 * x2 + 0 * x3 + 1 * x4 >= 3",
//        };
//        
//        String[] constraintsWithOutCondition = {
//            "3 * x1 + 5 * x2 + 2 * x3 + 7 * x4",
//            "4 * x1 + 3 * x2 + 3 * x3 + 5 * x4",
//            "5 * x1 + 6 * x2 + 4 * x3 + 8 * x4",
//            "1 * x1 + 0 * x2 + 0 * x3 + 0 * x4",
//            "0 * x1 + 1 * x2 + 0 * x3 + 0 * x4",
//            "0 * x1 + 0 * x2 + 1 * x3 + 0 * x4",
//            "0 * x1 + 0 * x2 + 0 * x3 + 1 * x4",
//        };

//        String goalFunction = "0.09 * x1 + 0.11 * x2";
//        
//        String[] constraints = {
//            "1 * x1 + 1 * x2 <= 400000",
//            "3 * x1 + (-2) * x2 >= 0",
//            "0 * x1 + 1 * x2 <= 95000",
//            "1 * x1 + 0 * x2 >= 0",
//            "0 * x1 + 1 * x2 >= 0",
//        };
//        
//        String[] constraintsWithOutCondition = {
//            "1 * x1 + 1 * x2",
//            "3 * x1 + 2 * x2",
//            "0 * x1 + 1 * x2",
//            "1 * x1 + 0 * x2",
//            "0 * x1 + 1 * x2",
//        };
        
        results = new GeneticExecute("-100", "100",
            "100", "10000", "4", "1").execute(goalFunction, constraints, constraintsWithOutCondition);
        
        for (GeneticResult r : results) {
            System.out.println(r.getResultBestIndividualDouble());
            System.out.println(Arrays.toString(r.getPointsDoubleBestIndividual()));
        }
    }
    
}
