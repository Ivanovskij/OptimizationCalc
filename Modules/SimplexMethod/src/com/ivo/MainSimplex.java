package com.ivo;

import com.ivo.module.SimplexMethod;

/**
 *
 * @author IOAdmin
 */
public class MainSimplex {

    public static void main(String[] args) {
        double[][] table = { {22, 8,  7, 5, 9},
                                {25, 8,  9, 7, 8},
                                {38,  10,  9, 9, 7},
                                { 30,  10, 11, 11, 6},
                                { 0, -21, -18, -16, -17.5} 
        };

        double[] result = new double[4];
        double[][] table_result;

        SimplexMethod S = new SimplexMethod(table);
        table_result = S.calculate(result);
        
        System.out.println(result[0] + ":" + result[1] + ":" + result[2] + ":" + result[3]);
        System.out.println("F = " + (result[0] * 21 + result[1] * 18 + result[2] * 16 + result[3] * 17.5));
    }
    
}
