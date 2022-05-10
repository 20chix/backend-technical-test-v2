package com.tui.proof.validator;

import com.tui.proof.model.Pilotes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
class OneOfValidatorTest {

    private OneOfValidator validator;
    int[] allowedValues = {5, 10, 15};
    int defaultValue = 0;

    @BeforeEach
    void setUp() {
        validator = new OneOfValidator();
        validator.initialize(createAnnotation(allowedValues, defaultValue));
    }

    @Test
    void givenAnOrderWithOnePilotesItShouldFail() {

        assertThat(isValid(1)).isFalse();

    }

    @Test
    void givenAnOrderWithFivePilotesItShouldSucceed() {

        assertThat(isValid(5)).isTrue();

    }

    @Test
    void givenAnOrderWith10PilotesItShouldSucceed() {

        assertThat(isValid(10)).isTrue();

    }

    @Test
    void givenAnOrderWith15PilotesItShouldSucceed() {

        assertThat(isValid(15)).isTrue();

    }

    @Test
    void givenAnOrderWith900PilotesItShouldFail() {

        assertThat(isValid(900)).isFalse();

    }



    private boolean isValid(int value) {
        return validator.isValid(value, null);
    }

    private OneOf createAnnotation(int[] allowedValues, int defaultValue) {
        return new OneOf() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String message() {
                return "You can only order 5 or 10 or 15 pilotes at the time";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Pilotes>[] payload() {
                return new Class[0];
            }

            @Override
            public int[] value() {
                int[] allowedValues = {5, 10, 15};
                return  allowedValues;
            }
        };
    }
}