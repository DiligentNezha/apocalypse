package com.apocalypse.common.constraints.validators;


import com.apocalypse.common.constraints.EnumMatch;
import com.apocalypse.common.constraints.IntegerValuesReadable;
import com.apocalypse.common.constraints.ValuesReadable;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class EnumMatchIntegerValidator implements ConstraintValidator<EnumMatch, Integer> {

    private Class<? extends ValuesReadable> valuesReadableClass;

    private List<Integer> values;

    @Override
    public void initialize(EnumMatch constraintAnnotation) {
        this.valuesReadableClass = constraintAnnotation.value();
        try {
            if (valuesReadableClass.isEnum()) {
                Method valuesMethod = valuesReadableClass.getDeclaredMethod("values");
                Method readValuesMethod = valuesReadableClass.getDeclaredMethod("readIntegerValues");
                values = (List<Integer>) readValuesMethod.invoke(((Object[])valuesMethod.invoke(valuesReadableClass))[0]);
            } else {
                log.error("{} must be a enum type!", valuesReadableClass);
            }
        } catch (Exception e) {
            log.error("{} must implements {} interface!" , valuesReadableClass, IntegerValuesReadable.class, e);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (values.contains(value)) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                // 重新添加错误提示语句
                .replaceAll("\\{options}", values.toString())).addConstraintViolation();
        return false;
    }
}
