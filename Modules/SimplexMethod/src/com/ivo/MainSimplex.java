package com.ivo;

import com.ivo.beans.ResultBean;
import com.ivo.module.SimplexMethodForWeb;
import com.ivo.utils.ExportToWord;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class MainSimplex {

    public static void main(String[] args) throws Exception {
//        double[][] table = { {22, 8,  7, 5, 9, 1},
//                                {25, 8,  9, 7, 8, 1},
//                                {38,  10,  9, 9, 7, 1},
//                                { 30,  10, 11, 11, 6, 1},
//                                { 0, 21, 18, 16, 17.5, -1} 
//        };
//        

        double[][] table = { {6, 1, 6,  1},
                                { 0, 2, 1, -1} 
        };

        double freeMemberC = 0;
        
//        double[][] table = { {15, 3,  3, 1},
//                                {18, 2,  6,  1},
//                                {16,  4,  0,  -1},
//                                { 8,  1, 2,  1},
//                                { 0, 2, 3, -1} 
//        };

        double[] result = new double[5];
        List<ResultBean> results;

        SimplexMethodForWeb S = new SimplexMethodForWeb(table, freeMemberC);
        results = S.calculate();
        
        String[][] matrix;
        for (ResultBean res: results) {
            System.out.println(res.getDescription());
            
            for (int i = 0; i < res.getIterResults().length; i++) {
                matrix = res.getIterResults();
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        }
        
        try {
            ExportToWord exportToWord = new ExportToWord(results);
            exportToWord.out();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        System.out.println("=================");
        System.out.println(S.getResultGoalFunc());
        System.out.println("=================");
        double[] x = S.getResultsX();
        for (int i = 0; i < x.length; i++) {
            System.out.println("X[" + i + "] = " + x[i]);
        }
    }
    
}
