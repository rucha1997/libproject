package org.itechkenya.leavemanager.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import org.itechkenya.leavemanager.domain.LeaveType;
import org.itechkenya.leavemanager.gui.LeaveEventFrame;
import org.itechkenya.leavemanager.gui.MainForm;
import org.joda.time.DateTime;
import org.joda.time.Years;

/**
 *
 * @author gitahi
 */
public class LeaveManager implements Runnable {

    private final MainForm mainForm;

    public static final BigDecimal MINIMUM_CARRY_OVER = BigDecimal.TEN;

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
                        List<PreviousCompletedPeriod> previousCompletedPeriods = getPreviousCompletedPeriods(contract);
                        for (PreviousCompletedPeriod previousCompletedPeriod : previousCompletedPeriods) {
                            LeaveEvent leaveEvent = JpaManager.getLejc()
                                    .findLeaveEvent(contract, leaveType, previousCompletedPeriod.getName());
                            boolean create = true;
                            if (leaveEvent == null) {
                                leaveEvent = new LeaveEvent();
                                leaveEvent.setContract(contract);
                                leaveEvent.setContractYear(getContractYear(contract));
                                leaveEvent.setLeaveType(leaveType);
                                leaveEvent.setStartDate(previousCompletedPeriod.getDate());
                                if (previousCompletedPeriod.getPeriodType() == PeriodType.MONTH) {
                                    leaveEvent.setDaysEarned(leaveType.getDaysPerMonth());
                                    leaveEvent.setComment("Monthly: " + previousCompletedPeriod.getName());
                                } else if (previousCompletedPeriod.getPeriodType() == PeriodType.YEAR) {
                                    BigDecimal balance = getLeaveBalanceAtYearEnd(contract, Integer.parseInt(previousCompletedPeriod.getName()));
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
    }

    public static int getContractYear(Contract contract, Date asOf) {
        return Years.yearsBetween(new DateTime(contract.getStartDate()),
                new DateTime(asOf)).getYears() + 1;
    }

    public static int getContractYear(Contract contract) {
        return getContractYear(contract, new Date());
    }

    public static void updateCalculatedValues(List<LeaveEvent> leaveEvents) {
        BigDecimal balance = BigDecimal.ZERO;
        String status = "NA";
        for (LeaveEvent leaveEvent : leaveEvents) {
            if (leaveEvent.getDaysEarned() != null) {
                balance = balance.add(leaveEvent.getDaysEarned());
                status = "NA";
            }
            if (leaveEvent.getDaysSpent() != null) {
                balance = balance.add(leaveEvent.getDaysSpent().negate());
                Date today = new Date();
                if (leaveEvent.getStartDate().compareTo(today) == 1) {
                    status = "Not started";
                }
                if (leaveEvent.getEndDate().compareTo(today) == -1) {
                    status = "Completed";
                }
                if (leaveEvent.getStartDate().compareTo(today) != 1 && leaveEvent.getEndDate().compareTo(today) != -1) {
                    status = "In progress";
                }
            }
            leaveEvent.setBalance(balance);
            leaveEvent.setStatus(status);
        }
    }

    private void autoCreateLeaveEvent(LeaveEvent leaveEvent) {
        ((LeaveEventFrame) mainForm.getLeaveEventFrame()).save(leaveEvent, true);
        mainForm.showAutoCreatedLeaveEventMessage("Auto-created leave event for: " + leaveEvent.getContract().getEmployee().toString());
    }

    private List<PreviousCompletedPeriod> getPreviousCompletedPeriods(Contract contract) {

        List<PreviousCompletedPeriod> previousCompletedPeriods = new ArrayList<>();

        DateTime today = new DateTime(new Date());
        DateTime contractStartDate = new DateTime(contract.getStartDate());

        SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");

        DateTime earnDateTime;
        DateTime recordDateTime;
        Date date;

        if (today.getDayOfMonth() >= contractStartDate.getDayOfMonth()) {
            earnDateTime = today.minusMonths(1);
        } else {
            earnDateTime = today.minusMonths(2);
        }
        recordDateTime = earnDateTime.plusMonths(1);
        date = DateTimeUtil.createDate(recordDateTime.getYear(), recordDateTime.getMonthOfYear(), contractStartDate.getDayOfMonth());
        previousCompletedPeriods.add(
                new PreviousCompletedPeriod(monthSdf.format(earnDateTime.toDate()), date, PeriodType.MONTH));

        int contractYearCount = getContractYear(contract);
        if (contractYearCount > 1) {
            int previousContractYear = getPreviousContractYear(contract, contractYearCount);

            Date recordDate = DateTimeUtil.createDate(previousContractYear + 1, contractStartDate.getMonthOfYear(), contractStartDate.getDayOfMonth());

            previousCompletedPeriods.add(
                    new PreviousCompletedPeriod(String.valueOf(previousContractYear), recordDate, PeriodType.YEAR));
        }
        return previousCompletedPeriods;
    }

    private int getPreviousContractYear(Contract contract, int contractYearCount) {
        DateTime contractStartDateTime = new DateTime(contract.getStartDate());
        int contractStartYear = contractStartDateTime.getYear();
        return contractStartYear + (contractYearCount - 2);
    }

    private BigDecimal getLeaveBalanceAtYearEnd(Contract contract, int year) {
        BigDecimal balance = BigDecimal.ZERO;
        if (getContractYear(contract) > 1) {
            List<LeaveEvent> contractLeaveEvents = contract.getLeaveEventList();
            Collections.sort(contractLeaveEvents);
            updateCalculatedValues(contractLeaveEvents);
            DateTime contractStartDateTime;
            for (LeaveEvent leaveEvent : contractLeaveEvents) {
                contractStartDateTime = new DateTime(contract.getStartDate());
                Date contractDateThisYear = DateTimeUtil.createDate(year + 1,
                        contractStartDateTime.getMonthOfYear(), contractStartDateTime.getDayOfMonth());
                if (leaveEvent.getStartDate().compareTo(contractDateThisYear) != 1) {
                    balance = leaveEvent.getBalance();
                }
            }
        }
        return balance;
    }

    private class PreviousCompletedPeriod {

        private final String name;
        private final Date date;
        private final PeriodType periodType;

        public PreviousCompletedPeriod(String name, Date date, PeriodType periodType) {
            this.name = name;
            this.date = date;
            this.periodType = periodType;
        }

        public String getName() {
            return name;
        }

        public Date getDate() {
            return date;
        }

        public PeriodType getPeriodType() {
            return periodType;
        }
    }

    private enum PeriodType {

        MONTH,
        YEAR
    }

}
