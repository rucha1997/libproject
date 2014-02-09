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

    public static String formatDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat("MMM dd, yyyy").format(date);
        }
        return "None";
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
