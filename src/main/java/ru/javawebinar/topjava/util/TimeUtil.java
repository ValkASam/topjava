package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return (startDate == null || ld.compareTo(startDate) >= 0)  && (endDate == null || ld.compareTo(endDate) <= 0);
    }

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0)  && (endTime == null || lt.compareTo(endTime) <= 0);
    }

    public static boolean isBetween(LocalDateTime ldt, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LocalDate ld = ldt.toLocalDate();
        LocalTime lt = ldt.toLocalTime();
        return isBetweenDate(ld, startDate, endDate) && isBetweenTime(lt, startTime, endTime);
    }
}
