package controller.instructor;


import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 04/04/2024 7:04 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class Utilities {
    private static final DateTimeFormatter
            dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm 'ngày' dd/MM/yyyy");
    private static final DateTimeFormatter
            dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter
            timeFormatter = DateTimeFormatter.ofPattern("HH 'tiếng' mm 'phút'");

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }

    public static String formatDate(Date date) {
        return date.toLocalDate().format(dateFormatter);
    }

    public static String formatTime(String time) {
        LocalTime localTime = LocalTime.parse(time);
        return timeFormatter.format(localTime);
    }

    public static String convertTimeToMinutes(String time) {
        LocalTime localTime = LocalTime.parse(time);
        int totalMinutes = localTime.getHour() * 60 + localTime.getMinute();
        return String.valueOf(totalMinutes);
    }
}
