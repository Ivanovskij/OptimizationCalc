package com.ivo.web.main.beans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@RequestScoped
public class UserLogin implements Serializable {

    /**
     * Creates a new instance of UserLogin
     */
    public UserLogin() {
    }
    
    private String name;
    private String password;
    
    // ---------------------------------------------------------
    public String login() {
        try {
            HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
            request.login(name, password);
            return "home";
        } catch (ServletException se) {
            ResourceBundle bundle = ResourceBundle.getBundle("com.ivo.web.nls.messages", 
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(bundle.getString("login_access_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("login-form:username", message);
        }
        return "login";
    } 

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
