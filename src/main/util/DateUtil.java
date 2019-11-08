package main.util;


import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-MMM-yyyy");

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String date) {
        return isEmpty(date) ? LocalDate.now() : LocalDate.parse(date);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.trim().length() == 0;
    }

}