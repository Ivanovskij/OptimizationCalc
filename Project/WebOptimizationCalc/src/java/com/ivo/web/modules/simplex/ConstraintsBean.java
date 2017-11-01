package com.ivo.web.modules.simplex;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class ConstraintsBean {

    private int countArgs;
    private int countConstraints;
    
    /**
     * Creates a new instance of ConstraintsBean
     */
    public ConstraintsBean() {
        countArgs = 0;
        countConstraints = 0;
    }
    
    public String handleFirstStep() {
        return "step2";
    }

    public int getCountArgs() {
        return countArgs;
    }

    public void setCountArgs(int countArgs) {
        this.countArgs = countArgs;
    }

    public int getCountConstraints() {
        return countConstraints;
    }

    public void setCountConstraints(int countConstraints) {
        this.countConstraints = countConstraints;
    }
}
