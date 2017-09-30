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

package org.itechkenya.leavemanager.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itechkenya.leavemanager.domain.Contract;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.itechkenya.leavemanager.domain.Employee;
import org.itechkenya.leavemanager.jpa.exceptions.IllegalOrphanException;
import org.itechkenya.leavemanager.jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gitahi
 */
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) {
        if (employee.getContractList() == null) {
            employee.setContractList(new ArrayList<Contract>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Contract> attachedContractList = new ArrayList<Contract>();
            for (Contract contractListContractToAttach : employee.getContractList()) {
                contractListContractToAttach = em.getReference(contractListContractToAttach.getClass(), contractListContractToAttach.getId());
                attachedContractList.add(contractListContractToAttach);
            }
            employee.setContractList(attachedContractList);
            em.persist(employee);
            for (Contract contractListContract : employee.getContractList()) {
                Employee oldEmployeeOfContractListContract = contractListContract.getEmployee();
                contractListContract.setEmployee(employee);
                contractListContract = em.merge(contractListContract);
                if (oldEmployeeOfContractListContract != null) {
                    oldEmployeeOfContractListContract.getContractList().remove(contractListContract);
                    oldEmployeeOfContractListContract = em.merge(oldEmployeeOfContractListContract);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getId());
            List<Contract> contractListOld = persistentEmployee.getContractList();
            List<Contract> contractListNew = employee.getContractList();
            List<String> illegalOrphanMessages = null;
            for (Contract contractListOldContract : contractListOld) {
                if (!contractListNew.contains(contractListOldContract)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contract " + contractListOldContract + " since its employee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contract> attachedContractListNew = new ArrayList<Contract>();
            for (Contract contractListNewContractToAttach : contractListNew) {
                contractListNewContractToAttach = em.getReference(contractListNewContractToAttach.getClass(), contractListNewContractToAttach.getId());
                attachedContractListNew.add(contractListNewContractToAttach);
            }
            contractListNew = attachedContractListNew;
            employee.setContractList(contractListNew);
            employee = em.merge(employee);
            for (Contract contractListNewContract : contractListNew) {
                if (!contractListOld.contains(contractListNewContract)) {
                    Employee oldEmployeeOfContractListNewContract = contractListNewContract.getEmployee();
                    contractListNewContract.setEmployee(employee);
                    contractListNewContract = em.merge(contractListNewContract);
                    if (oldEmployeeOfContractListNewContract != null && !oldEmployeeOfContractListNewContract.equals(employee)) {
                        oldEmployeeOfContractListNewContract.getContractList().remove(contractListNewContract);
                        oldEmployeeOfContractListNewContract = em.merge(oldEmployeeOfContractListNewContract);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employee.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contract> contractListOrphanCheck = employee.getContractList();
            for (Contract contractListOrphanCheckContract : contractListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Contract " + contractListOrphanCheckContract + " in its contractList field has a non-nullable employee field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Employee findEmployee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
