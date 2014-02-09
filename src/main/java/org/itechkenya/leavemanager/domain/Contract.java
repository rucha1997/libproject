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

package org.itechkenya.leavemanager.domain;

import java.io.Serializable;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contract)) {
            return false;
        }
        Contract other = (Contract) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String active = this.getActive() ? "Active" : "Inactive";
        return DateTimeUtil.formatDate(startDate) + " - " + DateTimeUtil.formatDate(endDate) + " (" + active + ")";
    }

    @Override
    public int compareTo(Contract contract) {
        return this.getStartDate().compareTo(contract.getStartDate());
    }

}
