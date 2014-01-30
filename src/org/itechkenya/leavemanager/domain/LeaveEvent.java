/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "leave_event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LeaveEvent.findAll", query = "SELECT l FROM LeaveEvent l"),
    @NamedQuery(name = "LeaveEvent.findById", query = "SELECT l FROM LeaveEvent l WHERE l.id = :id"),
    @NamedQuery(name = "LeaveEvent.findByContractYear", query = "SELECT l FROM LeaveEvent l WHERE l.contractYear = :contractYear"),
    @NamedQuery(name = "LeaveEvent.findByDaysEarned", query = "SELECT l FROM LeaveEvent l WHERE l.daysEarned = :daysEarned"),
    @NamedQuery(name = "LeaveEvent.findByDaysSpent", query = "SELECT l FROM LeaveEvent l WHERE l.daysSpent = :daysSpent"),
    @NamedQuery(name = "LeaveEvent.findByStartDate", query = "SELECT l FROM LeaveEvent l WHERE l.startDate = :startDate"),
    @NamedQuery(name = "LeaveEvent.findByEndDate", query = "SELECT l FROM LeaveEvent l WHERE l.endDate = :endDate"),
    @NamedQuery(name = "LeaveEvent.findByComment", query = "SELECT l FROM LeaveEvent l WHERE l.comment = :comment"),
    @NamedQuery(name = "LeaveEvent.findByMonth", query = "SELECT l FROM LeaveEvent l WHERE l.month = :month"),
    @NamedQuery(name = "LeaveEvent.findByContractLeaveTypeAndMonth", query = "SELECT l FROM LeaveEvent l WHERE l.contract = :contract AND l.leaveType = :leaveType AND l.month = :month")})
public class LeaveEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "contract_year")
    private int contractYear;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "days_earned")
    private BigDecimal daysEarned;
    @Column(name = "days_spent")
    private BigDecimal daysSpent;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "comment")
    private String comment;
    @Column(name = "month")
    private String month;
    @JoinColumn(name = "leave_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LeaveType leaveType;
    @JoinColumn(name = "contract", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contract contract;

    public LeaveEvent() {
    }

    public LeaveEvent(Integer id) {
        this.id = id;
    }

    public LeaveEvent(Integer id, int contractYear, Date startDate) {
        this.id = id;
        this.contractYear = contractYear;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getContractYear() {
        return contractYear;
    }

    public void setContractYear(int contractYear) {
        this.contractYear = contractYear;
    }

    public BigDecimal getDaysEarned() {
        return daysEarned;
    }

    public void setDaysEarned(BigDecimal daysEarned) {
        this.daysEarned = daysEarned;
    }

    public BigDecimal getDaysSpent() {
        return daysSpent;
    }

    public void setDaysSpent(BigDecimal daysSpent) {
        this.daysSpent = daysSpent;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeaveEvent)) {
            return false;
        }
        LeaveEvent other = (LeaveEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.itechkenya.leavemanager.domain.LeaveEvent[ id=" + id + " ]";
    }

}
