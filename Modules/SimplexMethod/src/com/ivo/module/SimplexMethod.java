package com.ivo.module;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class SimplexMethod {

    // таблица без базисных переменных
    private double[][] table;
    
    // размерность строки, столбца
    private int n, m;
    
    // базисы
    private List<Integer> basis;

    public SimplexMethod(double[][] source) {
        n = source.length;
        m = source.length;
        
        table = new double[n][m];
        basis = new ArrayList<>();
        
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                table[row][col] = source[row][col];
            }
        }
        
        
    }
    
    

    
    
}
