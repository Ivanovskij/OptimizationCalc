package com.ivo.module;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class SimplexMethod {

    // table without basis vars
    private double[][] table;
    
    // size row, cols
    private int m, n;
    
    // basis
    private final List<Integer> basis;

    public SimplexMethod(double[][] source) {
        m = source.length;
        n = source[0].length;
        
        table = new double[m][n + m - 1];
        basis = new ArrayList<>();
        
        // create basis
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < table[row].length; col++) {
                if (col < n) {
                    table[row][col] = source[row][col];
                } else {
                    table[row][col] = 0;
                }
            }
            // add coefficient 1 before basis var in row
            if ((n + row) < table[row].length) {
                table[row][n + row] = 1;
                basis.add(n + row);
            } 
        }
        
        n = table[0].length;
    }
    
    public double[][] calculate(double[] result) {
        // main (leader) col and row
        int mainCol, mainRow;
        
        while (!isItEnd()) {
            mainCol = findMainCol();
            mainRow = findMainRow(mainCol);
            basis.set(mainRow, mainCol);
            
            double[][] newTable = new double[m][n];
            
            for (int j = 0; j < n; j++) {
                newTable[mainRow][j] = table[mainRow][j] / table[mainRow][mainCol];
            }
            
            for (int i = 0; i < m; i++) {
                if (i == mainRow) {
                    continue;
                }
                
                for (int j = 0; j < n; j++) {
                    newTable[i][j] = table[i][j] - table[i][mainCol] * newTable[mainRow][j];
                }
            }
            table = newTable;
        }
        
        // set in result finding values X
        for (int i = 0; i < result.length; i++) {
            int k = basis.indexOf(i + 1);
            if (k != -1) {
                result[i] = table[k][0];
            } else {
                result[i] = 0;
            }
        }
        
        return table;
    }

    private boolean isItEnd() {
        boolean flag = true;

        for (int j = 1; j < n; j++) {
            if (table[m - 1][j] < 0) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private int findMainCol() {
        int mainCol = 1;

        for (int j = 2; j < n; j++) {
            if (table[m - 1][j] < table[m - 1][mainCol]) {
                mainCol = j;
            }
        }

        return mainCol;
    }

    private int findMainRow(int mainCol) {
        int mainRow = 0;

        for (int i = 0; i < m - 1; i++)
            if (table[i][mainCol] > 0)
            {
                mainRow = i;
                break;
            }

        for (int i = mainRow + 1; i < m - 1; i++)
            if ((table[i][mainCol] > 0) && ((table[i][0] / table[i][mainCol]) < (table[mainRow][0] / table[mainRow][mainCol])))
                mainRow = i;

        return mainRow;
    }
    
}
