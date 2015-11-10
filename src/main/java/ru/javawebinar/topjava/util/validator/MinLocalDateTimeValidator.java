package ru.javawebinar.topjava.util.validator;

import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.validator.annotation.LocalDateTimeMin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Valk on 09.11.15.
 */
public class MinLocalDateTimeValidator implements ConstraintValidator<LocalDateTimeMin, LocalDateTime> {
    private LocalDateTime localDateTimeLowBound;

    @Override
    public void initialize(LocalDateTimeMin constraintAnnotation) {
        String constraintValue = constraintAnnotation.value();
        constraintValue = constraintValue + (constraintValue.indexOf(' ') < 0 ? " " + LocalTime.MIN.toString() : "");
        this.localDateTimeLowBound = TimeUtil.parseLocalDateTime(constraintValue);
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value.isAfter(localDateTimeLowBound);
    }
}
