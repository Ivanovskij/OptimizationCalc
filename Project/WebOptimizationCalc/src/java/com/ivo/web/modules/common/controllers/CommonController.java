package com.ivo.web.modules.common.controllers;

import com.ivo.beans.ResultBean;
import com.ivo.module.SimplexMethodForWeb;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class CommonController implements Serializable {

    private final int countArgs = 4;
    private final int countConstraints = 4;
    
    /* ========================= SIMPLEX ========================= */
    // on max - original
    private final double[][] tableSimplex = {
        { 22, 8, 7, 5, 9, 1 },
        { 25, 8, 9, 7, 8, 1 },
        { 38, 10, 9, 9, 7, 1 },
        { 30, 10, 11, 11, 6, 1 },
        { 0, 21, 18, 16, 17.5, -1 } 
    };
    private final double freeMemberC = 0;     // by default 0
    
    // condition
    private static final double FIFTEEN_PERCENT = 1.15;
    private static final double EIGHTY_FIVE_PERCENT = 0.85;
    private static final double NINTY_PERCENT = 0.90;
    
    // results simplex
    private final double[] resultGoalFunc;
    private final double[][] resultsX;
    /* ========================= GENETIC ========================= */
    
    public CommonController() {
        resultGoalFunc = new double[countArgs + 1];
        resultsX = new double[countConstraints + 1][countArgs + 1];
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="CALCULATE SIMPLEX">
    public List<ResultBean> calculateSimplexConditions() throws Exception {
        calculateSimplexOriginal();
        calculateSimplexAMore();
        calculateSimplexALess();
        calculateSimplexB();
        calculateSimplexC();
        return null;
    }
    
    public void calculateSimplexOriginal() throws Exception {
        SimplexMethodForWeb s = new SimplexMethodForWeb(tableSimplex, freeMemberC);
        try {
            s.calculate();
            resultGoalFunc[0] = s.getResultGoalFunc();
            for (int i = 0; i < s.getResultsX().length; i++) {
                resultsX[0][i] = s.getResultsX()[i];
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    // НАЛИЧНОСТЬ (РЕСУРС) БАНКА В КАЖДОМ ПЕРИОДЕ ВРЕМЕНИ УВЕЛИЧИТСЯ НА 15%:
    public void calculateSimplexAMore() throws Exception {
        double[][] conditionTableSimplex = copyOriginalMatrix();
        
        // cast table by condition A more
        for (int i = 0; i < countConstraints; i++) {
            double newValue = conditionTableSimplex[i][0] * FIFTEEN_PERCENT;
            conditionTableSimplex[i][0] = newValue;
        }
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(conditionTableSimplex, freeMemberC);
        try {
            s.calculate();
            resultGoalFunc[1] = s.getResultGoalFunc();
            for (int i = 0; i < s.getResultsX().length; i++) {
                resultsX[1][i] = s.getResultsX()[i];
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    // НАЛИЧНОСТЬ (РЕСУРС) БАНКА В КАЖДОМ ПЕРИОДЕ ВРЕМЕНИ УМЕНЬШИТСЯ НА 15%:
    public void calculateSimplexALess() throws Exception {
        double[][] conditionTableSimplex = copyOriginalMatrix();
        
        // cast table by condition A less
        for (int i = 0; i < countConstraints; i++) {
            double newValue = conditionTableSimplex[i][0] * EIGHTY_FIVE_PERCENT;
            conditionTableSimplex[i][0] = newValue;
        }
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(conditionTableSimplex, freeMemberC);
        try {
            s.calculate();
            resultGoalFunc[2] = s.getResultGoalFunc();
            for (int i = 0; i < s.getResultsX().length; i++) {
                resultsX[2][i] = s.getResultsX()[i];
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    // ПРИБЫЛЬ ОТ РЕАЛИЗАЦИИ КАЖДОГО ПРОЕКТА ИЗМЕНИТСЯ НА 10%:
    public void calculateSimplexB() throws Exception {
        // ПРЕОБРАЗОВАТЬ TABLE, * 0.90 - прибыль уменьшилась (можно сделать чтобы увеличится)
        
        double[][] conditionTableSimplex = copyOriginalMatrix();
        
        // cast table by condition B
        int lastRow = countConstraints;
        // dont take last col
        for (int i = 0; i < countArgs + 1; i++) {
            double newValue = conditionTableSimplex[lastRow][i] * NINTY_PERCENT;
            conditionTableSimplex[lastRow][i] = newValue;
        }
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(conditionTableSimplex, freeMemberC);
        try {
            s.calculate();
            resultGoalFunc[3] = s.getResultGoalFunc();
            for (int i = 0; i < s.getResultsX().length; i++) {
                resultsX[3][i] = s.getResultsX()[i];
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    // ПОТРЕБНОСТИ ПРОЕКТА А В 3-М И 4-М ПЕРИОДАХ СОКРАТЯТСЯ НА 15%:
    public void calculateSimplexC() throws Exception {
        // ПРЕОБРАЗОВАТЬ TABLE, * A (3) и A(4) * 0.85
        double[][] conditionTableSimplex = copyOriginalMatrix();
        
        // cast table by condition C
        int lastRow = countConstraints - 1; 
        double newValue = conditionTableSimplex[lastRow][1] * EIGHTY_FIVE_PERCENT;
        conditionTableSimplex[lastRow][1] = newValue;
        newValue = conditionTableSimplex[lastRow - 1][1] * EIGHTY_FIVE_PERCENT;
        conditionTableSimplex[lastRow - 1][1] = newValue;
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(conditionTableSimplex, freeMemberC);
        try {
            s.calculate();
            resultGoalFunc[4] = s.getResultGoalFunc();
            for (int i = 0; i < s.getResultsX().length; i++) {
                resultsX[4][i] = s.getResultsX()[i];
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    private double[][] copyOriginalMatrix() {
        double[][] newMatrix = new double[countConstraints + 1][countArgs + 2];
        for (int i = 0; i < countConstraints + 1; i++) {
            for (int j = 0; j < countArgs + 2; j++) {
                newMatrix[i][j] = tableSimplex[i][j];
            }
        }
        return newMatrix;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CALCULATE GENETIC">
    public List<ResultBean> calculateGenetic() {
        return null;
    }
    // </editor-fold>
    
    public String handleSimplex() throws Exception {
        calculateSimplexConditions();
        return "common_simplex";
    }

    
    public String handleGenetic() {
        return "common_genetic";
    }
    
    /* ====================================================== */

    public double[] getResultGoalFunc() {
        return resultGoalFunc;
    }

    public double[][] getResultsX() {
        return resultsX;
    }

    public int getCountArgs() {
        return countArgs;
    }

    public int getCountConstraints() {
        return countConstraints;
    }
}
