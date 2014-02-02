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
