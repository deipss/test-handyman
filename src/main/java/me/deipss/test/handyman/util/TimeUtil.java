package me.deipss.test.handyman.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author hxl
 * @version 1.0
 * @since 2022/1/3 14:06
 */

public class TimeUtil {
    public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String Y_M_D = "yyyy-MM-dd";
    public static String YMD = "yyyyMMdd";
    public static String YM = "yyyyMM";
    public static String H_M_S = "HH:mm:ss";
    public static String HMS = "HHmmss";

    public static long epochMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static long epochMilliNow() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }



    public static LocalDate parseLocalDate(String pattern, String format) {
        return toLocalDateTime(parseDate(pattern, format)).toLocalDate();
    }

    public static LocalDateTime parseLocalDateTime(String pattern, String format) {
        return toLocalDateTime(parseDate(pattern, format));
    }

    public static LocalTime parseLocalTime(String pattern, String format) {
        return toLocalDateTime(parseDate(pattern, format)).toLocalTime();
    }

    public static Date parseDate(String pattern, String format) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(format);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String format(String pattern, Date date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(toLocalDateTime(date));
    }

    public static String formatToday() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YMD);
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public static String format(String pattern, LocalDate date) {
        return DateTimeFormatter.ofPattern(pattern).format(date);
    }

    public static String format(String pattern, LocalDateTime date) {
        return DateTimeFormatter.ofPattern(pattern).format(date);
    }

    public static String format(String pattern, LocalTime date) {
        return DateTimeFormatter.ofPattern(pattern).format(date);
    }


}
