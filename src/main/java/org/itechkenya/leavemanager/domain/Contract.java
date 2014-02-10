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

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.itechkenya.leavemanager.api.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.Years;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "contract")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contract.findAll", query = "SELECT c FROM Contract c ORDER BY c.startDate DESC"),
    @NamedQuery(name = "Contract.findById", query = "SELECT c FROM Contract c WHERE c.id = :id ORDER BY c.startDate DESC"),
    @NamedQuery(name = "Contract.findByStartDate", query = "SELECT c FROM Contract c WHERE c.startDate = :startDate ORDER BY c.startDate DESC"),
    @NamedQuery(name = "Contract.findByEndDate", query = "SELECT c FROM Contract c WHERE c.endDate = :endDate ORDER BY c.startDate DESC"),
    @NamedQuery(name = "Contract.findByActive", query = "SELECT c FROM Contract c WHERE c.active = :active ORDER BY c.startDate DESC"),
    @NamedQuery(name = "Contract.findActive", query = "SELECT c FROM Contract c WHERE c.active = :active AND c.startDate <= :today AND (c.endDate IS NULL OR c.endDate >= :today) ORDER BY c.startDate DESC"),
    @NamedQuery(name = "Contract.findByEmployee", query = "SELECT c FROM Contract c WHERE c.employee = :employee ORDER BY c.startDate DESC")})
public class Contract implements Serializable, Comparable<Contract> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract")
    private List<LeaveEvent> leaveEventList;

    public Contract() {
    }

    public Contract(Integer id) {
        this.id = id;
    }

    public Contract(Integer id, Date startDate, boolean active) {
        this.id = id;
        this.startDate = startDate;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @XmlTransient
    public List<LeaveEvent> getLeaveEventList() {
        if (leaveEventList == null) {
            leaveEventList = new ArrayList<>();
        }
        Collections.sort(leaveEventList);
        return leaveEventList;
    }

    public void setLeaveEventList(List<LeaveEvent> leaveEventList) {
        this.leaveEventList = leaveEventList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Contract)) {
            return false;
        }
        Contract other = (Contract) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return DateTimeUtil.formatDate(startDate)
                + " - " + DateTimeUtil.formatDate(endDate)
                + " (" + (this.getActive() ? "Active" : "Inactive") + ")";
    }

    @Override
    public int compareTo(Contract contract) {
        return this.getStartDate().compareTo(contract.getStartDate());
    }

    public int calculateContractYear(Date asOf) {
        return Years.yearsBetween(new DateTime(this.getStartDate()),
                new DateTime(asOf)).getYears() + 1;
    }

    public int calculatePreviousContractYear(int contractYearCount) {
        DateTime contractStartDateTime = new DateTime(this.getStartDate());
        int contractStartYear = contractStartDateTime.getYear();
        return contractStartYear + (contractYearCount - 2);
    }

    public int calculateContractYear() {
        return calculateContractYear(new Date());
    }

    public void calculateLeaveEventValues() {
        BigDecimal balance = BigDecimal.ZERO;
        for (LeaveEvent leaveEvent : this.getLeaveEventList()) {
            assignStatus(leaveEvent);
            assignBalance(leaveEvent, balance);
        }
    }

    public BigDecimal calculateLeaveBalanceAtYearEnd(int year) {
        BigDecimal balance = BigDecimal.ZERO;
        if (this.calculateContractYear() > 1) {
            this.calculateLeaveEventValues();
            DateTime contractStartDateTime;
            for (LeaveEvent leaveEvent : this.getLeaveEventList()) {
                contractStartDateTime = new DateTime(this.getStartDate());
                Date contractDateThisYear = DateTimeUtil.createDate(year + 1,
                        contractStartDateTime.getMonthOfYear(), contractStartDateTime.getDayOfMonth());
                if (leaveEvent.getStartDate().compareTo(contractDateThisYear) != 1) {
                    balance = leaveEvent.getBalance();
                }
            }
        }
        return balance;
    }

    public List<PreviousCompletedPeriod> calculatePreviousCompletedPeriods() {

        List<PreviousCompletedPeriod> previousCompletedPeriods = new ArrayList<>();

        DateTime today = new DateTime(new Date());
        DateTime contractStartDate = new DateTime(this.getStartDate());

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

        int contractYearCount = this.calculateContractYear();
        if (contractYearCount > 1) {
            int previousContractYear = this.calculatePreviousContractYear(contractYearCount);

            Date recordDate = DateTimeUtil.createDate(previousContractYear + 1, contractStartDate.getMonthOfYear(), contractStartDate.getDayOfMonth());

            previousCompletedPeriods.add(
                    new PreviousCompletedPeriod(String.valueOf(previousContractYear), recordDate, PeriodType.YEAR));
        }
        return previousCompletedPeriods;
    }

    private void assignStatus(LeaveEvent leaveEvent) {
        String status = "NA";
        if (leaveEvent.getDaysEarned() != null) {
            status = "NA";
        } else {
            if (leaveEvent.getDaysSpent() != null) {
                Date today = new Date();
                if (leaveEvent.getStartDate().compareTo(today) == 1) {
                    status = "Not started";
                }
                if (leaveEvent.getEndDate().compareTo(today) == -1) {
                    status = "Completed";
                }
                if (leaveEvent.getStartDate().compareTo(today) != 1
                        && leaveEvent.getEndDate().compareTo(today) != -1) {
                    status = "In progress";
                }
            }
        }
        leaveEvent.setStatus(status);
    }

    private void assignBalance(LeaveEvent leaveEvent, BigDecimal balance) {
        if (leaveEvent.getDaysEarned() != null) {
            balance = balance.add(leaveEvent.getDaysEarned());
        }
        if (leaveEvent.getDaysSpent() != null) {
            balance = balance.add(leaveEvent.getDaysSpent().negate());
        }
        leaveEvent.setBalance(balance);
    }

    public class PreviousCompletedPeriod {

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

    public enum PeriodType {

        MONTH,
        YEAR
    }
}
