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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.itechkenya.leavemanager.jpa.ContractJpaController;
import org.itechkenya.leavemanager.jpa.EmployeeJpaController;
import org.itechkenya.leavemanager.jpa.LeaveEventJpaController;
import org.itechkenya.leavemanager.jpa.LeaveTypeJpaController;
import org.itechkenya.leavemanager.jpa.OrganizationJpaController;

/**
 *
 * @author gitahi
 */
public class JpaManager {

    private static EntityManagerFactory emf;

    private static LeaveEventJpaController lejc;
    private static EmployeeJpaController ejc;
    private static ContractJpaController cjc;
    private static LeaveTypeJpaController ltjc;
    private static OrganizationJpaController ojc;

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("leave-managerPU");
        }
        return emf;
    }

    public static LeaveEventJpaController getLejc() {
        if (lejc == null) {
            lejc = new LeaveEventJpaController(getEmf());
        }
        return lejc;
    }

    public static EmployeeJpaController getEjc() {
        if (ejc == null) {
            ejc = new EmployeeJpaController(getEmf());
        }
        return ejc;
    }

    public static ContractJpaController getCjc() {
        if (cjc == null) {
            cjc = new ContractJpaController(getEmf());
        }
        return cjc;
    }

    public static OrganizationJpaController getOjc() {
        if (ojc == null) {
            ojc = new OrganizationJpaController(getEmf());
        }
        return ojc;
    }

    public static LeaveTypeJpaController getLtjc() {
        if (ltjc == null) {
            ltjc = new LeaveTypeJpaController(getEmf());
        }
        return ltjc;
    }
}
