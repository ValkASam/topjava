package ru.javawebinar.topjava.util.validator.annotation;

import ru.javawebinar.topjava.util.validator.MinLocalDateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by Valk on 09.11.15.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MinLocalDateTimeValidator.class)
public @interface LocalDateTimeMin {
    String message() default "Date is less than the minimum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
    String value();
}
