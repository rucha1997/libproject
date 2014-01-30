package org.itechkenya.leavemanager.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import org.itechkenya.leavemanager.domain.LeaveType;
import org.itechkenya.leavemanager.gui.MainForm;
import org.joda.time.DateTime;
import org.joda.time.Years;

/**
 *
 * @author gitahi
 */
public class LeaveManager implements Runnable {

    private MainForm mainForm;

    public LeaveManager(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public void run() {
        List<LeaveType> autoIncrementableLeaveTypes = JpaManager.getLtjc().findAutoIncrementableLeaveTypes();
        if (autoIncrementableLeaveTypes != null && !autoIncrementableLeaveTypes.isEmpty()) {
            List<Contract> activeContracts = JpaManager.getCjc().findActiveContracts();
            if (activeContracts != null && !activeContracts.isEmpty()) {
                int counter = 0;
                for (LeaveType leaveType : autoIncrementableLeaveTypes) {
                    for (Contract contract : activeContracts) {
                        String previousCompletedMonth = getPreviousCompletedMonth(contract);
                        LeaveEvent leaveEvent = JpaManager.getLejc()
                                .findLeaveEvent(contract, leaveType, previousCompletedMonth);
                        if (leaveEvent == null) {
                            leaveEvent = new LeaveEvent();
                            leaveEvent.setContract(contract);
                            leaveEvent.setContractYear(getContractYear(contract));
                            leaveEvent.setLeaveType(leaveType);
                            leaveEvent.setStartDate(new Date());
                            leaveEvent.setDaysEarned(leaveType.getDaysPerMonth());
                            leaveEvent.setComment("Auto-created for: " + previousCompletedMonth);
                            leaveEvent.setMonth(previousCompletedMonth);
                            counter++;
                            mainForm.showAutoCreatedLeaveEventMessage("Auto-created new leave event for: " + leaveEvent.getContract().getEmployee().toString());
                        }
                    }
                }
                if (counter > 1) {
                    mainForm.showAutoCreatedLeaveEventMessage("Auto-created " + counter + " new leave event(s).");
                }
            }
        }
    }

    public static int getContractYear(Contract contract) {
        return Years.yearsBetween(new DateTime(contract.getStartDate()),
                new DateTime(new Date())).getYears() + 1;
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
}
