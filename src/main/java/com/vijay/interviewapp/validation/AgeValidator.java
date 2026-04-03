package com.vijay.interviewapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Adult, Integer> {

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext context) {
        if (age == null) return false;
        return age >= 18;
    }
}
