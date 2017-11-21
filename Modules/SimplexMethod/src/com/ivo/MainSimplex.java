package com.ivo;

import com.ivo.beans.ResultBean;
import com.ivo.module.SimplexMethodForWeb;
import com.ivo.utils.ExportToWord;
import java.io.FileOutputStream;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class MainSimplex {

    public static void main(String[] args) throws Exception {
        double[][] table = { {22, 8,  7, 5, 9, 1},
                                {25, 8,  9, 7, 8, 1},
                                {38,  10,  9, 9, 7, 1},
                                { 30,  10, 11, 11, 6, 1},
                                { 0, 21, 18, 16, 17.5, -1} 
        };
        
        // condition A more
//        double[][] table = { {25.3, 8,  7, 5, 9, 1},
//                                {28.75, 8,  9, 7, 8, 1},
//                                {43.7,  10,  9, 9, 7, 1},
//                                { 34.5,  10, 11, 11, 6, 1},
//                                { 0, 21, 18, 16, 17.5, -1} 
//        };
        
//        // condition A less
//        double[][] table = { {18.7, 8,  7, 5, 9, 1},
//                                {21.25, 8,  9, 7, 8, 1},
//                                {32.3,  10,  9, 9, 7, 1},
//                                { 25.5,  10, 11, 11, 6, 1},
//                                { 0, 21, 18, 16, 17.5, -1} 
//        };

//// condition C 
//        double[][] table = { {22, 8,  7, 5, 9, 1},
//                                {25, 8,  9, 7, 8, 1},
//                                {38,  8.5,  9, 9, 7, 1},
//                                { 30,  8.5, 11, 11, 6, 1},
//                                { 0, 21, 18, 16, 17.5, -1} 
//        };
//        

//        double[][] table = { {6, 1, 6,  1},
//                                { 0, 2, 1, -1} 
//        };

        double freeMemberC = 0;
        
//        double[][] table = { {15, 3,  3, 1},
//                                {18, 2,  6,  1},
//                                {16,  4,  0,  -1},
//                                { 8,  1, 2,  1},
//                                { 0, 2, 3, -1} 
//        };

//        // Kesel
//        double[][] table = { {150, 3,  5, 2, 7, 1},
//                                {90, 4,  3, 3, 5, 1},
//                                {240,  5,  6, 4, 8, 1},
//                                { 3,  1, 0, 0, 0, -1},
//                                { 3,  0, 1, 0, 0, -1},
//                                { 3,   0, 0, 1, 0, -1},
//                                { 3,   0, 0, 0, 1, -1},
//                                { 0, 40, 50, 30, 20, -1} 
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
        
//        try {
//            ExportToWord exportToWord = new ExportToWord(results, new FileOutputStream("D:\\simplex.docx"));
//            exportToWord.out();
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//        }
        
        
        System.out.println("=================");
        System.out.println(S.getResultGoalFunc());
        System.out.println("=================");
        double[] x = S.getResultsX();
        for (int i = 0; i < x.length; i++) {
            System.out.println("X[" + i + "] = " + x[i]);
        }
    }
    
}
