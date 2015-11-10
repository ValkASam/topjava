package ru.javawebinar.topjava.util.validator;

import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.validator.annotation.LocalDateTimeMax;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Valk on 09.11.15.
 */
public class MaxLocalDateTimeValidator implements ConstraintValidator<LocalDateTimeMax, LocalDateTime> {
    private LocalDateTime localDateTimeHighBound;

    @Override
    public void initialize(LocalDateTimeMax constraintAnnotation) {
        String constraintValue = constraintAnnotation.value();
        if (constraintValue.isEmpty()) constraintValue = LocalDate.now().toString();
        constraintValue = constraintValue + (constraintValue.indexOf(' ') < 0 ? " " + LocalTime.MAX.format(DateTimeFormatter.ofPattern("HH:mm")) : "");
        this.localDateTimeHighBound = TimeUtil.parseLocalDateTime(constraintValue);
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value.isBefore(localDateTimeHighBound);
    }
}
