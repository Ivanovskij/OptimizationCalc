package com.ivo.web.main.controllers;

import com.ivo.beans.ResultBean;
import com.ivo.module.SimplexMethodForWord;
import com.ivo.utils.ExportToWord;
import com.ivo.web.modules.simplex.controllers.ConstraintsController;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class FileSaveController implements Serializable {

    private String path;

    public FileSaveController() {
    }

    public FileSaveController(String path) {
        this.path = path;
    }
    
    public void saveInWord() throws Exception {
        String filename = "simplex.docx";

        // Prepare response.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-word");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        
        FacesContext context = FacesContext.getCurrentInstance();
        ConstraintsController bean = (ConstraintsController) facesContext.getApplication()
            .evaluateExpressionGet(context, "#{constraintsController}", ConstraintsController.class);
        
        double[][] table = bean.getTable();
        if (table == null) {
            throw new Exception("Ошибка при взятии результатов решения, параметр: table"); 
        }
        double freeMemberC = bean.getFreeMemberC();
        
        SimplexMethodForWord sm = new SimplexMethodForWord(table, freeMemberC);
        
        List<ResultBean> results = sm.calculate();
        if (results == null) {
            throw new Exception("Ошибка при взятии результатов решения, параметр: results"); 
        }
        double[] resultsX = sm.getResultsX();
        if (resultsX == null) {
            throw new Exception("Ошибка при взятии результатов решения, параметр: resultsX"); 
        }
        double resultFunc = sm.getResultGoalFunc();
        
        
        // export
        try {
            ExportToWord export = new ExportToWord(results, resultFunc, resultsX,
                    externalContext.getResponseOutputStream());
            export.out();
        } catch (IOException | RuntimeException ex) {
            throw new Exception("Ошибка при сохранении файла решения.");
        }
        
        facesContext.responseComplete();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }    
}
