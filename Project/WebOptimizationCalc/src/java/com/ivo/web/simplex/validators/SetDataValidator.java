package com.ivo.web.simplex.validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author IOAdmin
 */
@FacesValidator("com.ivo.web.simplex.validators.SetDataValidator")
public class SetDataValidator implements Validator {

    private static final int MIN_COUNT_ARGS = 2;
    private static final int MIN_COUNT_CONSTRAINTS = 1;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            // if value != integer
            // method: parseInt throws exception
            // profit
            Integer valueComponent = Integer.parseInt(value.toString());
            
            ResourceBundle resourceBundle 
                = ResourceBundle.getBundle("com.ivo.web.nls.messages", 
                        FacesContext.getCurrentInstance().getViewRoot().getLocale());
            
            String idComponent = component.getId();
            
            if (idComponent.equals("constraintsCount")) {
                if (valueComponent < MIN_COUNT_CONSTRAINTS) {
                    throw new IllegalArgumentException(resourceBundle.getString("min_count_constraints"));
                }
            } else {
                if (valueComponent < MIN_COUNT_ARGS) {
                    throw new IllegalArgumentException(resourceBundle.getString("min_count_args"));
                }   
            }

        } catch (NumberFormatException n) {
            FacesMessage message = new FacesMessage("Введите число");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } catch (IllegalArgumentException ex) {
            FacesMessage message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

}
