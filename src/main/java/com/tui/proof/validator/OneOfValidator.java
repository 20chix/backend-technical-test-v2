package com.tui.proof.validator;

import org.apache.commons.lang3.ArrayUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOfValidator implements ConstraintValidator<OneOf, Integer> {

    int[] values;

    @Override
    public void initialize(OneOf constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        values = constraintAnnotation.value();

    }

    @Override
    public boolean isValid(Integer input, ConstraintValidatorContext constraintValidatorContext) {
        if( ArrayUtils.contains(values, input)){
            return true;
        }

        return false;
    }
}

