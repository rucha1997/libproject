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

import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;

/**
 *
 * @author gitahi
 */
public class LeaveManagerTest extends TestCase {
    
    public LeaveManagerTest(String testName) {
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
     * Test of run method, of class LeaveManager.
     */
//    public void testRun() {
//        System.out.println("run");
//        LeaveManager instance = null;
//        instance.run();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getContractYear method, of class LeaveManager.
     */
    public void testGetContractYear_Contract_Date() {
        System.out.println("getContractYear");
        Contract contract = new Contract();
        contract.setStartDate(DateTimeUtil.createDate(2012, 11, 13));
        contract.setEndDate(DateTimeUtil.createDate(2016, 11, 12));
        
        Date asOf1 = DateTimeUtil.createDate(2013, 1, 1);
        Date asOf2 = DateTimeUtil.createDate(2014, 6, 15);
        Date asOf3 = DateTimeUtil.createDate(2015, 12, 31);
        
        int expResult1 = 1;
        int expResult2 = 2;
        int expResult3 = 4;
        
        int result1 = LeaveManager.getContractYear(contract, asOf1);
        int result2 = LeaveManager.getContractYear(contract, asOf2);
        int result3 = LeaveManager.getContractYear(contract, asOf3);
        
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
    }

    /**
     * Test of updateCalculatedValues method, of class LeaveManager.
     */
//    public void testUpdateCalculatedValues() {
//        System.out.println("updateCalculatedValues");
//        List<LeaveEvent> leaveEvents = null;
//        LeaveManager.updateCalculatedValues(leaveEvents);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
