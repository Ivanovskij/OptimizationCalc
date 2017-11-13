package com.ivo.web.modules.genetic.controllers;

import com.ivo.web.modules.genetic.beans.ArgsBean;
import com.ivo.web.modules.genetic.beans.FunctionBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class GConstraintsController implements Serializable {

    private List<Integer> argsList;
    private List<Integer> constraintsList;
    
    private int countArgs;
    private int countConstraints;
    
    // constraints
    private final List<ArgsBean> argValues = new ArrayList<>();
    // goal func
    private final List<FunctionBean> funcValues = new ArrayList<>();

    public GConstraintsController() {
    }
    
    
    public String handleFirstStep() {
        return "step2_genetic";
    }
    
    /*=====================================================*/
    public int getCountArgs() {
        return countArgs;
    }

    public void setCountArgs(int countArgs) {
        this.countArgs = countArgs;
        
        argsList = new ArrayList<>();
        for (int i = 0; i < countArgs; i++) {
            argsList.add(i);
        }
    }

    public int getCountConstraints() {
        return countConstraints;
    }

    public void setCountConstraints(int countConstraints) {
        this.countConstraints = countConstraints;
        
        constraintsList = new ArrayList<>();
        for (int i = 0; i < countConstraints; i++) {
            constraintsList.add(i);
        }
    }

    public List<Integer> getArgsList() {
        return argsList;
    }

    public List<Integer> getConstraintsList() {
        return constraintsList;
    }
    
}
