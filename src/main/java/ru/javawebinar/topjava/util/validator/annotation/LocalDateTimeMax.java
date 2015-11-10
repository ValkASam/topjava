package ru.javawebinar.topjava.util.validator.annotation;

import ru.javawebinar.topjava.util.validator.MaxLocalDateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Valk on 09.11.15.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MaxLocalDateTimeValidator.class)
public @interface LocalDateTimeMax {
    String message() default "Date is greater than the maximum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
