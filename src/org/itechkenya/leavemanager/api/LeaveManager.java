package org.itechkenya.leavemanager.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import org.itechkenya.leavemanager.domain.LeaveType;
import org.joda.time.DateTime;
import org.joda.time.Years;

/**
 *
 * @author gitahi
 */
public class LeaveManager implements Runnable {

    @Override
    public void run() {
        //get todays date
        //get all leave types where days per month > 0
        //for each such leavetype get all active and unexpired contracts contracts
        //for each contract, calculate the previous month+get leave event for previous month and of this type
        //check if they have earned leave for the previous CONTRACT month i.e. did above return result?
        // if yes, go to next, otherwise create leave event
        //exit
        List<LeaveType> allLeaveTypes = JpaManager.getLtjc().findLeaveTypeEntities();

        List<LeaveType> autoIncreasingLeaveTypes = new ArrayList<>();
        for (LeaveType leaveType : allLeaveTypes) {
            if (leaveType.getDaysPerMonth().compareTo(BigDecimal.ZERO) == 1) {
                autoIncreasingLeaveTypes.add(leaveType);
            }
        }

        List<Contract> allContracts = JpaManager.getCjc().findContractEntities();

        List<Contract> activeContracts = new ArrayList<>();
        for (Contract contract : allContracts) {
            if (contract.getActive()) {
                activeContracts.add(contract);
            }
        }

        for (LeaveType leaveType : autoIncreasingLeaveTypes) {
            for (Contract contract : activeContracts) {
                String previousMonth = getPreviousCompletedMonth(contract);
                LeaveEvent leaveEvent = null;//get from database
                if (leaveEvent == null) {
                    leaveEvent = new LeaveEvent();
                    leaveEvent.setContract(contract);
                    leaveEvent.setContractYear(getContractYear(contract));
                    leaveEvent.setLeaveType(leaveType);
                    leaveEvent.setStartDate(new Date());
                    leaveEvent.setDaysEarned(leaveType.getDaysPerMonth());
                    leaveEvent.setComment("Auto-created");
                    leaveEvent.setMonth(previousMonth);
                }
            }
        }
    }

    private String getPreviousCompletedMonth(Contract contract) {
        DateTime today = new DateTime(new Date());
        DateTime contractStartDate = new DateTime(contract.getStartDate());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        
        if (today.getDayOfMonth() >= contractStartDate.getDayOfMonth()) {
            return sdf.format(today.minusMonths(1).toDate());
        } else {
            return sdf.format(today.minusMonths(2).toDate());
        }
    }

    public static int getContractYear(Contract contract) {
        return Years.yearsBetween(new DateTime(contract.getStartDate()), 
                new DateTime(new Date())).getYears() + 1;
    }
}
