package com.ivo.web.modules.simplex.controllers;


import com.ivo.web.modules.simplex.beans.ArgsBean;
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
public class ConstraintsController {

    private List<Integer> argsList;
    private List<Integer> constraintsList;
    
    private final List<ArgsBean> argValues = new ArrayList<>();
    
    private int countArgs;
    private int countConstraints;
    
    /**
     * Creates a new instance of ConstraintsBean
     */
    public ConstraintsController() {
    }
    
    public String handleFirstStep() {
        if (countArgs != 0 && countConstraints != 0) {
            return "step2";
        }
        return "error_page";
    }
    
    public String handleSecondStep() {
        if (!argValues.isEmpty()) {
            return "step3";
        }
        return "error_page";
    }

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
    
    public List<ArgsBean> setValuesArgs() {
        if (!argValues.isEmpty()) {
            argValues.clear();
        }
        for (int i = 0; i < countConstraints; i++) {
            argValues.add(new ArgsBean());
        }
        return argValues;
    }

    public List<ArgsBean> getArgsValues() {
        return argValues;
    }
    
}
