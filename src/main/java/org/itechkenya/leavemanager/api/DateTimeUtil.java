/**
 * LeaveManager, a basic leave management program for small organizations
 * 
 * This file is part of LeaveManager.
 * 
 * LeaveManager is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * LeaveManager is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with LeaveManager. If not, see <http://www.gnu.org/licenses/>.
 */

package org.itechkenya.leavemanager.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 * Provides handy static methods for date manipulation.
 *
 * @author gitahi
 */
public class DateTimeUtil {

    private final static int DEFAULT_YEAR = 2000;
    private final static int DEFAULT_HOUR = 0;
    private final static int DEFAULT_MINUTE = 0;

    /**
     * 
     * @param datetime the DateTime to evaluate.
     * 
     * @return true if the given dateTime falls on a public holiday. See {@link DateTimeUtil#getPublicHolidays()}.
     */
    public static boolean isPublicHoliday(DateTime datetime) {
        List<DateTime> publicHolidays = getPublicHolidays();
        for (DateTime publicHoliday : publicHolidays) {
            if (shareDayAndMonth(datetime, publicHoliday)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param datetime the DateTime to evaluate.
     * 
     * @return true if the given dateTime falls on a Sunday.
     */
    public static boolean isSunday(DateTime datetime) {
        return datetime.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }

    /**
     * @param dateTime the datetime to evaluate.
     * 
     * @return true if the given DateTime falls on a weekend.
     */
    public static boolean isWeekend(DateTime dateTime) {
        return dateTime.getDayOfWeek() == DateTimeConstants.SATURDAY
                || dateTime.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }

    /**
     * @param year the year
     * @param month the month of the year
     * @param day the day of the month
     * 
     * @return a date object representing the year, month and day supplied. If the day is greater than the maximum
     * for the given month, that maximum is used. E.g. 2014,02,31 yields the same result as 2014,02,28.
     */
    public static Date createDate(int year, int month, int day) {
        DateTime firstDateTime = new DateTime(year, month, 1, DEFAULT_HOUR, DEFAULT_MINUTE);
        int maxDay = firstDateTime.dayOfMonth().withMaximumValue().getDayOfMonth();
        return new DateTime(year, month, maxDay <= day ? maxDay : day, DEFAULT_HOUR, DEFAULT_MINUTE).toDate();
    }

    /**
     * @param date the date to be formatted.
     * 
     * @return a String representing the date formated as MMM dd, yyyy. E.g. Jun 15, 2014.
     */
    public static String formatDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat("MMM dd, yyyy").format(date);
        }
        return "None";
    }

    /**
     * @return a list of all static public holidays celebrated in Kenya. Dynamic public holidays such as Easter and Idd
     * are not included. In future, this method will be updated to read from a resource outside the source code in order
     * to be configurable for other countries.
     */
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

    /**
     * @return true if dateTime1 and dateTime2 share the same day of month and month of year.
     */
    private static boolean shareDayAndMonth(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth() && dateTime1.getMonthOfYear()
                == dateTime2.getMonthOfYear();
    }
}
