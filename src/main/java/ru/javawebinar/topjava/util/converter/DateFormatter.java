package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * GKislin
 * 15.04.2015.
 */
public class DateFormatter implements Formatter<Date> {
    /*сделал для конвертации Date при передаче по Ajax*/
    /*по факту не использую - в данном контексте проще так (и для разнообразия): new Date(data.registered) - в колбеке Ajax*/
    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return new Date(Long.parseLong(text));
    }

    @Override
    public String print(Date dt, Locale locale) {
        return dt.toString();
    }
}
