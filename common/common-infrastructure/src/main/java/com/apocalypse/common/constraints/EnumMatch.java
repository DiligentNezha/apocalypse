package com.apocalypse.common.constraints;


import com.apocalypse.common.constraints.validators.EnumMatchIntegerValidator;
import com.apocalypse.common.constraints.validators.EnumMatchStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.List;

@Constraint(validatedBy = {EnumMatchStringValidator.class, EnumMatchIntegerValidator.class})
@Target({
        ElementType.FIELD,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumMatch {

    String message() default "必须在指定范围 {options} !";

    Class<? extends ValuesReadable> value();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
