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
    private double[][] tableSimplex = { 
        { 22, 8,  7, 5, 9, 1 },
        { 25, 8,  9, 7, 8, 1 },
        { 38,  10,  9, 9, 7, 1 },
        { 30,  10, 11, 11, 6, 1 },
        { 0, 21, 18, 16, 17.5, -1 } 
    };
    private double freeMemberC = 0;     // by default 0
    
    // results simplex
    private double resultGoalFunc;
    private double[] resultsX;
    /* ========================= GENETIC ========================= */
    
    public CommonController() {
    }

    // <editor-fold defaultstate="collapsed" desc="CALCULATE SIMPLEX">
    public List<ResultBean> calculateSimplexOriginal() throws Exception {
        List<ResultBean> results;
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(tableSimplex, freeMemberC);
        try {
            results = s.calculate();
            setResultGoalFunc(s.getResultGoalFunc());
            setResultsX(s.getResultsX());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return results;
    }
    
    public List<ResultBean> calculateSimplexAMore() throws Exception {
        List<ResultBean> results;
        
        // ПРЕОБРАЗОВАТЬ TABLE
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(tableSimplex, freeMemberC);
        try {
            results = s.calculate();
            setResultGoalFunc(s.getResultGoalFunc());
            setResultsX(s.getResultsX());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return results;
    }
    
    public List<ResultBean> calculateSimplexALess() throws Exception {
        List<ResultBean> results;
        
        // ПРЕОБРАЗОВАТЬ TABLE
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(tableSimplex, freeMemberC);
        try {
            results = s.calculate();
            setResultGoalFunc(s.getResultGoalFunc());
            setResultsX(s.getResultsX());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return results;
    }
    
    public List<ResultBean> calculateSimplexB() throws Exception {
        List<ResultBean> results;
        
        // ПРЕОБРАЗОВАТЬ TABLE
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(tableSimplex, freeMemberC);
        try {
            results = s.calculate();
            setResultGoalFunc(s.getResultGoalFunc());
            setResultsX(s.getResultsX());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return results;
    }
    
    public List<ResultBean> calculateSimplexC() throws Exception {
        List<ResultBean> results;
        
        // ПРЕОБРАЗОВАТЬ TABLE
        
        SimplexMethodForWeb s = new SimplexMethodForWeb(tableSimplex, freeMemberC);
        try {
            results = s.calculate();
            setResultGoalFunc(s.getResultGoalFunc());
            setResultsX(s.getResultsX());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return results;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CALCULATE GENETIC">
    public List<ResultBean> calculateGenetic() {
        return null;
    }
    // </editor-fold>
    
    public String handleSimplex() throws Exception {
        calculateSimplexOriginal();
        return "common_simplex";
    }

    
    public String handleGenetic() {
        return "common_genetic";
    }
    
    /* ====================================================== */

    public double getResultGoalFunc() {
        return resultGoalFunc;
    }

    public void setResultGoalFunc(double resultGoalFunc) {
        this.resultGoalFunc = resultGoalFunc;
    }

    public double[] getResultsX() {
        return resultsX;
    }

    public void setResultsX(double[] resultsX) {
        this.resultsX = resultsX;
    }

    public int getCountArgs() {
        return countArgs;
    }

    public int getCountConstraints() {
        return countConstraints;
    }
}
