package com.ivo.web.modules.common.controllers;

import com.ivo.GeneticExecute;
import com.ivo.beans.ResultBean;
import com.ivo.module.GeneticResult;
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
    
    // in params
    private String xMin = "-100",
            xMax = "100",
            max_generation = "70",
            population_count = "2000",
            arg_size = "4",
            maxOrMin = "1";     // 1 - max
    
    // results genetic
    private final GeneticResult[] resultsGenetic;
    
    
    /**
     *  Constructor
     */
    public CommonController() {
        resultGoalFunc = new double[countArgs + 1];
        resultsX = new double[countConstraints + 1][countArgs + 1];
        
        resultsGenetic = new GeneticResult[countArgs + 1];
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
    public void calculateGenetic() {
        calculateGeneticOriginal();
        calculateGeneticAmore();
        calculateGeneticALess();
        calculateGeneticB();
        calculateGeneticC();
    }
    
    public void calculateGeneticOriginal() {
        GeneticExecute ge = new GeneticExecute(xMin, xMax, max_generation, population_count, arg_size, maxOrMin);
        GeneticResult result = ge.execute(goalFunction, constraints, constraintsWithOutCondition)
                .get(Integer.valueOf(max_generation) - 1);
        resultsGenetic[0] = result;
    }
    
    // НАЛИЧНОСТЬ (РЕСУРС) БАНКА В КАЖДОМ ПЕРИОДЕ ВРЕМЕНИ УВЕЛИЧИТСЯ НА 15%:
    public void calculateGeneticAmore() {
        GeneticExecute ge = new GeneticExecute(xMin, xMax, max_generation, population_count, arg_size, maxOrMin);
        
        String[] conditionConstraint = constraints.clone();
        
        conditionConstraint[0] = "8 * x1 + 7 * x2 + 5 * x3 + 9 * x4 <= 25.3";
        conditionConstraint[1] = "8 * x1 + 9 * x2 + 7 * x3 + 8 * x4 <= 28.75";
        conditionConstraint[2] = "10 * x1 + 9* x2 + 9 * x3 + 7 * x4 <= 43.7";
        conditionConstraint[3] = "10 * x1 + 11 * x2 +  11 * x3 + 6 * x4 <= 34.5";
        
        GeneticResult result = ge.execute(goalFunction, conditionConstraint, constraintsWithOutCondition)
                .get(Integer.valueOf(max_generation) - 1);
        resultsGenetic[1] = result;
    }
    
    // НАЛИЧНОСТЬ (РЕСУРС) БАНКА В КАЖДОМ ПЕРИОДЕ ВРЕМЕНИ УМЕНЬШИТСЯ НА 15%:
    public void calculateGeneticALess() {
        GeneticExecute ge = new GeneticExecute(xMin, xMax, max_generation, population_count, arg_size, maxOrMin);
        
        String[] conditionConstraint = constraints.clone();
        
        conditionConstraint[0] = "8 * x1 + 7 * x2 + 5 * x3 + 9 * x4 <= 18.7";
        conditionConstraint[1] = "8 * x1 + 9 * x2 + 7 * x3 + 8 * x4 <= 21.25";
        conditionConstraint[2] = "10 * x1 + 9* x2 + 9 * x3 + 7 * x4 <= 32.3";
        conditionConstraint[3] = "10 * x1 + 11 * x2 +  11 * x3 + 6 * x4 <= 25.5";
        
        GeneticResult result = ge.execute(goalFunction, conditionConstraint, constraintsWithOutCondition)
                .get(Integer.valueOf(max_generation) - 1);
        resultsGenetic[2] = result;
    }
    
    // ПРИБЫЛЬ ОТ РЕАЛИЗАЦИИ КАЖДОГО ПРОЕКТА ИЗМЕНИТСЯ НА 10%:
    public void calculateGeneticB() {
        GeneticExecute ge = new GeneticExecute(xMin, xMax, max_generation, population_count, arg_size, maxOrMin);
        
        // * 0.85
        String goalFuncCondition = "17.85 * x1 + 15.3 * x2 + 13.6 * x3 + 14.88 * x4";
        
        GeneticResult result = ge.execute(goalFuncCondition, constraints, constraintsWithOutCondition)
                .get(Integer.valueOf(max_generation) - 1);
        resultsGenetic[3] = result;
    }
    
    // ПОТРЕБНОСТИ ПРОЕКТА А В 3-М И 4-М ПЕРИОДАХ СОКРАТЯТСЯ НА 15%:
    public void calculateGeneticC() {
        GeneticExecute ge = new GeneticExecute(xMin, xMax, max_generation, population_count, arg_size, maxOrMin);
        
        String[] conditionConstraint = constraints.clone();
        
        conditionConstraint[2] = "8.5 * x1 + 9* x2 + 9 * x3 + 7 * x4 <= 38";
        conditionConstraint[3] = "8.5 * x1 + 11 * x2 +  11 * x3 + 6 * x4 <= 30";
        
        GeneticResult result = ge.execute(goalFunction, conditionConstraint, constraintsWithOutCondition)
                .get(Integer.valueOf(max_generation) - 1);
        resultsGenetic[4] = result;
    }
    // </editor-fold>
    
    public String handleSimplex() throws Exception {
        calculateSimplexConditions();
        return "common_simplex";
    }

    
    public String handleGenetic() {
        calculateGenetic();
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

    public GeneticResult[] getResultsGenetic() {
        return resultsGenetic;
    }
}
