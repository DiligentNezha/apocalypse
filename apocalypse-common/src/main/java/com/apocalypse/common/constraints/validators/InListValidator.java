package com.apocalypse.common.constraints.validators;

import com.apocalypse.common.constraints.InList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class InListValidator implements ConstraintValidator<InList, String> {
    private String[] options;

    @Override
    public void initialize(InList inList) {
        options = inList.options();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Stream.of(options).anyMatch(option -> option.equals(value));
    }
}
