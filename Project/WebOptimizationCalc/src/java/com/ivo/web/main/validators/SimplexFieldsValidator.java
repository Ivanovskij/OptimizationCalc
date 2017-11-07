package com.ivo.web.main.validators;

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
@FacesValidator("com.ivo.web.main.validators.SimplexFieldsValidator")
public class SimplexFieldsValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            // if value != double
            // method: parseDouble throws exception
            // profit
            Double valueComponent = Double.parseDouble(value.toString());
        } catch (IllegalArgumentException ex) {
            FacesMessage message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

}
