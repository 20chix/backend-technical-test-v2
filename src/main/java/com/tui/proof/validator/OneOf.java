package com.tui.proof.validator;

import com.tui.proof.model.Pilotes;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OneOfValidator.class)
public @interface OneOf {
    String message() default "value must match one of the values in the list";
    Class<?>[] groups() default {};
    Class<? extends Pilotes>[] payload() default {};
    int[] value() default {};
}