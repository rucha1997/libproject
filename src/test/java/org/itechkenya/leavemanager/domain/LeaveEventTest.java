/**
 * LeaveManager, a basic leave management program for small organizations
 *
 * This file is part of LeaveManager.
 *
 * LeaveManager is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * LeaveManager is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with LeaveManager. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.itechkenya.leavemanager.domain;

import java.math.BigDecimal;
import java.util.Date;
import junit.framework.TestCase;
import org.itechkenya.leavemanager.api.DateTimeUtil;

/**
 *
 * @author gitahi
 */
public class LeaveEventTest extends TestCase {

    public LeaveEventTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of compareTo method, of class LeaveEvent.
     */
    public void testCompareTo() {
        System.out.println("compareTo");

        LeaveEvent referenceLeaveEvent = new LeaveEvent();
        Date referenceDate = DateTimeUtil.createDate(2014, 6, 15);
        referenceLeaveEvent.setStartDate(referenceDate);

        LeaveEvent lesserLeaveEvent = new LeaveEvent();
        Date lessereDate = DateTimeUtil.createDate(2014, 5, 15);
        lesserLeaveEvent.setStartDate(lessereDate);

        LeaveEvent greaterLeaveEvent = new LeaveEvent();
        Date greatereDate = DateTimeUtil.createDate(2014, 7, 15);
        greaterLeaveEvent.setStartDate(greatereDate);

        LeaveEvent equalLeaveEvent = new LeaveEvent();
        Date equalDate = DateTimeUtil.createDate(2014, 6, 15);
        equalLeaveEvent.setStartDate(equalDate);

        assertEquals(referenceLeaveEvent.compareTo(lesserLeaveEvent), 1);
        assertEquals(referenceLeaveEvent.compareTo(greaterLeaveEvent), -1);
        assertEquals(referenceLeaveEvent.compareTo(equalLeaveEvent), 0);
    }

    /**
     * Test of calculateEndDate method, of class LeaveEvent.
     */
    public void testCalculateEndDate() {
        System.out.println("calculateEndDate");

        LeaveType absolute = new LeaveType();
        absolute.setAbsolute(true);
        LeaveType nonAbsolute = new LeaveType();
        nonAbsolute.setAbsolute(false);

        Date date1 = DateTimeUtil.createDate(2014, 12, 15);
        Date date2 = DateTimeUtil.createDate(2014, 12, 18);
        Date date3 = DateTimeUtil.createDate(2014, 12, 23);
        Date date4 = DateTimeUtil.createDate(2014, 12, 24);

        BigDecimal days1 = BigDecimal.valueOf(6);
        BigDecimal days2 = BigDecimal.valueOf(7);
        BigDecimal days3 = BigDecimal.valueOf(8);
        BigDecimal days4 = BigDecimal.valueOf(2);

        LeaveEvent leaveEvent1 = new LeaveEvent();
        leaveEvent1.setStartDate(date1);
        leaveEvent1.setDaysSpent(days1);

        LeaveEvent leaveEvent2 = new LeaveEvent();
        leaveEvent2.setStartDate(date2);
        leaveEvent2.setDaysSpent(days2);

        LeaveEvent leaveEvent3 = new LeaveEvent();
        leaveEvent3.setStartDate(date3);
        leaveEvent3.setDaysSpent(days3);

        LeaveEvent leaveEvent4 = new LeaveEvent();
        leaveEvent4.setStartDate(date4);
        leaveEvent4.setDaysSpent(days4);

        Date expectedNonAbsoluteResult1 = DateTimeUtil.createDate(2014, 12, 22);
        leaveEvent1.setLeaveType(nonAbsolute);
        assertEquals(leaveEvent1.calculateEndDate(), expectedNonAbsoluteResult1);

        Date expectedNonAbsoluteResult2 = DateTimeUtil.createDate(2014, 12, 30);
        leaveEvent2.setLeaveType(nonAbsolute);
        assertEquals(leaveEvent2.calculateEndDate(), expectedNonAbsoluteResult2);

        Date expectedNonAbsoluteResult3 = DateTimeUtil.createDate(2015, 1, 6);
        leaveEvent3.setLeaveType(nonAbsolute);
        assertEquals(leaveEvent3.calculateEndDate(), expectedNonAbsoluteResult3);

        Date expectedNonAbsoluteResult4 = DateTimeUtil.createDate(2014, 12, 29);
        leaveEvent4.setLeaveType(nonAbsolute);
        assertEquals(leaveEvent4.calculateEndDate(), expectedNonAbsoluteResult4);
    }
}
