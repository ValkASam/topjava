package ru.javawebinar.topjava.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Valk on 03.10.15.
 */
//@Converter впрок объявлять нельзя, должно быть обязательно применение
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate,java.sql.Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        return entityValue != null ? Date.valueOf(entityValue) : null;
    }
    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        return databaseValue != null ? databaseValue.toLocalDate() :null;
    }
}
