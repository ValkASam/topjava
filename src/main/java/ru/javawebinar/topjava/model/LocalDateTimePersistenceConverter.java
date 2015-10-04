package ru.javawebinar.topjava.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Valk on 03.10.15.
 */
//@Converter // только для попробовать - hibernate 5 поддерживает LocalDateTime //(autoApply = true) - вручную спокойней
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime,java.sql.Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        return entityValue != null ? Timestamp.valueOf(entityValue) : null;
    }
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp databaseValue) {
        return databaseValue != null ? databaseValue.toLocalDateTime() :null;
    }
}
