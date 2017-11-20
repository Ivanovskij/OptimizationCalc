package com.ivo.web.modules.common.controllers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class CommonController implements Serializable {

    public CommonController() {
    }

    
    public String handleSimplex() {
        return "common_simplex";
    }

    
    public String handleGenetic() {
        return "common_genetic";
    }
}
