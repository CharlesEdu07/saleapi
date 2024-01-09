package com.charlesedu.saleapi.web.validators;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.charlesedu.saleapi.models.SellerModel;

public class SellerModelValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SellerModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SellerModel employee = (SellerModel) target;

        LocalDate admission = employee.getAdmissionDate();

        if (employee.getExitDate() != null) {
            if (employee.getExitDate().isBefore(admission)) {
                errors.rejectValue("exitDate", "AfterExitDate.employee.exitDate");
            }
        }
    }
}
