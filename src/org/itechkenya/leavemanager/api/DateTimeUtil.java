/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.api;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 *
 * @author gitahi
 */
public class DateTimeUtil {

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

    private static List<DateTime> getPublicHolidays() {
        List<DateTime> publicHolidays = new ArrayList<>();
        int defaultYear = 2000;
        int defaultHour = 0;
        int defaultMinute = 0;
        publicHolidays.add(new DateTime(defaultYear, 1, 1, defaultHour, defaultMinute)); //New Year Day
        publicHolidays.add(new DateTime(defaultYear, 5, 1, defaultHour, defaultMinute)); //Labor Day
        publicHolidays.add(new DateTime(defaultYear, 6, 1, defaultHour, defaultMinute)); //Madaraka Day
        publicHolidays.add(new DateTime(defaultYear, 10, 20, defaultHour, defaultMinute)); //Mashujaa Day
        publicHolidays.add(new DateTime(defaultYear, 12, 12, defaultHour, defaultMinute)); //Jamhuri Day
        publicHolidays.add(new DateTime(defaultYear, 12, 25, defaultHour, defaultMinute)); //Christmas Day
        publicHolidays.add(new DateTime(defaultYear, 12, 26, defaultHour, defaultMinute)); //Boxing Day
        return publicHolidays;
    }

    private static boolean shareDayAndMonth(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth() && dateTime1.getMonthOfYear()
                == dateTime2.getMonthOfYear();
    }
}
