package com.apocalypse.common.core.constraints.validators;

import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.core.constraints.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {
    private String field;

    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if (ObjectUtil.isNotNull(fieldValue)) {
            return ObjectUtil.equal(fieldValue, fieldMatchValue);
        } else {
            return ObjectUtil.isNull(fieldMatchValue);
        }
    }
}
