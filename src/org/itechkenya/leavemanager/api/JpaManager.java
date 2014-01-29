/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.api;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.itechkenya.leavemanager.jpa.ContractJpaController;
import org.itechkenya.leavemanager.jpa.EmployeeJpaController;
import org.itechkenya.leavemanager.jpa.LeaveTypeJpaController;
import org.itechkenya.leavemanager.jpa.OrganizationJpaController;

/**
 *
 * @author gitahi
 */
public class JpaManager {

    private static EntityManagerFactory emf;

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
