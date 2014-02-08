/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itechkenya.leavemanager.domain.Employee;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.jpa.exceptions.IllegalOrphanException;
import org.itechkenya.leavemanager.jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gitahi
 */
public class ContractJpaController implements Serializable {

    public ContractJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contract contract) {
        if (contract.getLeaveEventList() == null) {
            contract.setLeaveEventList(new ArrayList<LeaveEvent>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee = contract.getEmployee();
            if (employee != null) {
                employee = em.getReference(employee.getClass(), employee.getId());
                contract.setEmployee(employee);
            }
            List<LeaveEvent> attachedLeaveEventList = new ArrayList<LeaveEvent>();
            for (LeaveEvent leaveEventListLeaveEventToAttach : contract.getLeaveEventList()) {
                leaveEventListLeaveEventToAttach = em.getReference(leaveEventListLeaveEventToAttach.getClass(), leaveEventListLeaveEventToAttach.getId());
                attachedLeaveEventList.add(leaveEventListLeaveEventToAttach);
            }
            contract.setLeaveEventList(attachedLeaveEventList);
            em.persist(contract);
            if (employee != null) {
                employee.getContractList().add(contract);
                employee = em.merge(employee);
            }
            for (LeaveEvent leaveEventListLeaveEvent : contract.getLeaveEventList()) {
                Contract oldContractOfLeaveEventListLeaveEvent = leaveEventListLeaveEvent.getContract();
                leaveEventListLeaveEvent.setContract(contract);
                leaveEventListLeaveEvent = em.merge(leaveEventListLeaveEvent);
                if (oldContractOfLeaveEventListLeaveEvent != null) {
                    oldContractOfLeaveEventListLeaveEvent.getLeaveEventList().remove(leaveEventListLeaveEvent);
                    oldContractOfLeaveEventListLeaveEvent = em.merge(oldContractOfLeaveEventListLeaveEvent);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contract contract) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contract persistentContract = em.find(Contract.class, contract.getId());
            Employee employeeOld = persistentContract.getEmployee();
            Employee employeeNew = contract.getEmployee();
            List<LeaveEvent> leaveEventListOld = persistentContract.getLeaveEventList();
            List<LeaveEvent> leaveEventListNew = contract.getLeaveEventList();
            List<String> illegalOrphanMessages = null;
            for (LeaveEvent leaveEventListOldLeaveEvent : leaveEventListOld) {
                if (!leaveEventListNew.contains(leaveEventListOldLeaveEvent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LeaveEvent " + leaveEventListOldLeaveEvent + " since its contract field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (employeeNew != null) {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getId());
                contract.setEmployee(employeeNew);
            }
            List<LeaveEvent> attachedLeaveEventListNew = new ArrayList<LeaveEvent>();
            for (LeaveEvent leaveEventListNewLeaveEventToAttach : leaveEventListNew) {
                leaveEventListNewLeaveEventToAttach = em.getReference(leaveEventListNewLeaveEventToAttach.getClass(), leaveEventListNewLeaveEventToAttach.getId());
                attachedLeaveEventListNew.add(leaveEventListNewLeaveEventToAttach);
            }
            leaveEventListNew = attachedLeaveEventListNew;
            contract.setLeaveEventList(leaveEventListNew);
            contract = em.merge(contract);
            if (employeeOld != null && !employeeOld.equals(employeeNew)) {
                employeeOld.getContractList().remove(contract);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld)) {
                employeeNew.getContractList().add(contract);
                employeeNew = em.merge(employeeNew);
            }
            for (LeaveEvent leaveEventListNewLeaveEvent : leaveEventListNew) {
                if (!leaveEventListOld.contains(leaveEventListNewLeaveEvent)) {
                    Contract oldContractOfLeaveEventListNewLeaveEvent = leaveEventListNewLeaveEvent.getContract();
                    leaveEventListNewLeaveEvent.setContract(contract);
                    leaveEventListNewLeaveEvent = em.merge(leaveEventListNewLeaveEvent);
                    if (oldContractOfLeaveEventListNewLeaveEvent != null && !oldContractOfLeaveEventListNewLeaveEvent.equals(contract)) {
                        oldContractOfLeaveEventListNewLeaveEvent.getLeaveEventList().remove(leaveEventListNewLeaveEvent);
                        oldContractOfLeaveEventListNewLeaveEvent = em.merge(oldContractOfLeaveEventListNewLeaveEvent);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contract.getId();
                if (findContract(id) == null) {
                    throw new NonexistentEntityException("The contract with id " + id + " no longer exists.");
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
            Contract contract;
            try {
                contract = em.getReference(Contract.class, id);
                contract.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contract with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LeaveEvent> leaveEventListOrphanCheck = contract.getLeaveEventList();
            for (LeaveEvent leaveEventListOrphanCheckLeaveEvent : leaveEventListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contract (" + contract + ") cannot be destroyed since the LeaveEvent " + leaveEventListOrphanCheckLeaveEvent + " in its leaveEventList field has a non-nullable contract field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employee employee = contract.getEmployee();
            if (employee != null) {
                employee.getContractList().remove(contract);
                employee = em.merge(employee);
            }
            em.remove(contract);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contract> findContractEntities() {
        return findContractEntities(true, -1, -1);
    }

    public List<Contract> findContractEntities(int maxResults, int firstResult) {
        return findContractEntities(false, maxResults, firstResult);
    }

    private List<Contract> findContractEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contract.class));
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

    public Contract findContract(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contract.class, id);
        } finally {
            em.close();
        }
    }

    public int getContractCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contract> rt = cq.from(Contract.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Contract> findContracts(Employee employee) {
        EntityManager em = getEntityManager();
        TypedQuery<Contract> query
                = em.createNamedQuery("Contract.findByEmployee", Contract.class);
        query.setParameter("employee", employee);
        return query.getResultList();
    }

    public List<Contract> findActiveContracts() {
        EntityManager em = getEntityManager();
        TypedQuery<Contract> query
                = em.createNamedQuery("Contract.findActive", Contract.class);
        query.setParameter("active", Boolean.TRUE);
        query.setParameter("today", new Date());
        return query.getResultList();
    }
}
