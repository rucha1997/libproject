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
package org.itechkenya.leavemanager.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import org.itechkenya.leavemanager.domain.LeaveType;
import org.itechkenya.leavemanager.gui.LeaveEventFrame;
import org.itechkenya.leavemanager.gui.MainForm;

/**
 * Manages all automated leave events such as monthly leave increments and end
 * of year forfeitures. This class spawns a separate thread on application
 * startup. It then runs periodically after every 10 seconds to check for any
 * new leave events that might need to be created automatically. In future, the
 * 10 second polling time will be removed from the source file to a
 * configuration file.
 *
 * This class also provides a few static general purpose methods for leave
 * events useful to other classes.
 *
 * @author gitahi
 */
public class LeaveManager implements Runnable {

    private final MainForm mainForm;
    private final long refreshRate = 10000L;

    public static final BigDecimal MINIMUM_CARRY_OVER = BigDecimal.TEN;

    public LeaveManager(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<LeaveType> autoIncrementableLeaveTypes = JpaManager.getLtjc().findAutoIncrementableLeaveTypes();
                if (autoIncrementableLeaveTypes != null && !autoIncrementableLeaveTypes.isEmpty()) {
                    List<Contract> activeContracts = JpaManager.getCjc().findActiveContracts();
                    if (activeContracts != null && !activeContracts.isEmpty()) {
                        int counter = 0;
                        for (LeaveType leaveType : autoIncrementableLeaveTypes) {
                            for (Contract contract : activeContracts) {
                                List<Contract.PreviouslyCompletedPeriod> previousCompletedPeriods = contract.calculatePreviouslyCompletedPeriod();
                                for (Contract.PreviouslyCompletedPeriod previousCompletedPeriod : previousCompletedPeriods) {
                                    LeaveEvent leaveEvent = JpaManager.getLejc()
                                            .findLeaveEvent(contract, leaveType, previousCompletedPeriod.getName());
                                    boolean create = true;
                                    if (leaveEvent == null) {
                                        leaveEvent = new LeaveEvent();
                                        leaveEvent.setContract(contract);
                                        leaveEvent.setContractYear(contract.calculateContractYear());
                                        leaveEvent.setLeaveType(leaveType);
                                        leaveEvent.setStartDate(previousCompletedPeriod.getDate());
                                        if (previousCompletedPeriod.getPeriodType() == Contract.PeriodType.MONTH) {
                                            leaveEvent.setDaysEarned(leaveType.getDaysPerMonth());
                                            leaveEvent.setComment("Monthly: " + previousCompletedPeriod.getName());
                                        } else if (previousCompletedPeriod.getPeriodType() == Contract.PeriodType.YEAR) {
                                            BigDecimal balance = contract.calculateLeaveBalanceAtYearEnd(Integer.parseInt(previousCompletedPeriod.getName()));
                                            if (balance.compareTo(BigDecimal.ZERO) == 1) {
                                                if (balance.compareTo(MINIMUM_CARRY_OVER) == 1) {
                                                    leaveEvent.setDaysSpent(balance.add(MINIMUM_CARRY_OVER.negate()));
                                                    leaveEvent.setEndDate(leaveEvent.getStartDate());
                                                    leaveEvent.setComment("Forfeiture: " + previousCompletedPeriod.getName());
                                                } else {
                                                    create = false;
                                                }
                                            } else {
                                                create = false;
                                            }
                                        }
                                        leaveEvent.setMonth(previousCompletedPeriod.getName());
                                        if (create) {
                                            autoCreateLeaveEvent(leaveEvent);
                                            counter++;
                                        }
                                    }
                                }
                            }
                        }
                        if (counter > 1) {
                            mainForm.showAutoCreatedLeaveEventMessage("Auto-created " + counter + " new leave event(s).");
                        }
                    }
                }
                Thread.sleep(refreshRate);
            } catch (InterruptedException ex) {
                Logger.getLogger(LeaveManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void autoCreateLeaveEvent(LeaveEvent leaveEvent) {
        ((LeaveEventFrame) mainForm.getLeaveEventFrame()).save(leaveEvent, true);
        mainForm.showAutoCreatedLeaveEventMessage("Auto-created leave event for: " + leaveEvent.getContract().getEmployee().toString());
    }
}
