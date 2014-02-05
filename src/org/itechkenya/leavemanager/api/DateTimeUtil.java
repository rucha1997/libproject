package org.itechkenya.leavemanager.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

/**
 *
 * @author gitahi
 */
public class DateTimeUtil {

    private final static int DEFAULT_YEAR = 2000;
    private final static int DEFAULT_HOUR = 0;
    private final static int DEFAULT_MINUTE = 0;

    public static boolean isPublicHoliday(DateTime datetime) {
        List<DateTime> publicHolidays = getPublicHolidays();
        for (DateTime publicHoliday : publicHolidays) {
            if (shareDayAndMonth(datetime, publicHoliday)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSunday(DateTime datetime) {
        return datetime.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }

    public static boolean isWeekend(DateTime datetime) {
        return datetime.getDayOfWeek() == DateTimeConstants.SATURDAY
                || datetime.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }

    public static Date createDate(int year, int month, int day) {
        DateTime firstDateTime = new DateTime(year, month, 1, DEFAULT_HOUR, DEFAULT_MINUTE);
        int maxDay = firstDateTime.dayOfMonth().withMaximumValue().getDayOfMonth();
        return new DateTime(year, month, maxDay <= day ? maxDay : day, DEFAULT_HOUR, DEFAULT_MINUTE).toDate();
    }

    private static List<DateTime> getPublicHolidays() {
        List<DateTime> publicHolidays = new ArrayList<>();
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 1, 1, DEFAULT_HOUR, DEFAULT_MINUTE)); //New Year Day
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 5, 1, DEFAULT_HOUR, DEFAULT_MINUTE)); //Labor Day
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 6, 1, DEFAULT_HOUR, DEFAULT_MINUTE)); //Madaraka Day
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 10, 20, DEFAULT_HOUR, DEFAULT_MINUTE)); //Mashujaa Day
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 12, 12, DEFAULT_HOUR, DEFAULT_MINUTE)); //Jamhuri Day
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 12, 25, DEFAULT_HOUR, DEFAULT_MINUTE)); //Christmas Day
        publicHolidays.add(new DateTime(DEFAULT_YEAR, 12, 26, DEFAULT_HOUR, DEFAULT_MINUTE)); //Boxing Day
        return publicHolidays;
    }

    private static boolean shareDayAndMonth(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth() && dateTime1.getMonthOfYear()
                == dateTime2.getMonthOfYear();
    }
}
