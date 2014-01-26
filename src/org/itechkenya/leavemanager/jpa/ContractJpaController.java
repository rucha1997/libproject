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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            Employee employeeId = contract.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getId());
                contract.setEmployeeId(employeeId);
            }
            List<LeaveEvent> attachedLeaveEventList = new ArrayList<LeaveEvent>();
            for (LeaveEvent leaveEventListLeaveEventToAttach : contract.getLeaveEventList()) {
                leaveEventListLeaveEventToAttach = em.getReference(leaveEventListLeaveEventToAttach.getClass(), leaveEventListLeaveEventToAttach.getId());
                attachedLeaveEventList.add(leaveEventListLeaveEventToAttach);
            }
            contract.setLeaveEventList(attachedLeaveEventList);
            em.persist(contract);
            if (employeeId != null) {
                employeeId.getContractList().add(contract);
                employeeId = em.merge(employeeId);
            }
            for (LeaveEvent leaveEventListLeaveEvent : contract.getLeaveEventList()) {
                Contract oldContractIdOfLeaveEventListLeaveEvent = leaveEventListLeaveEvent.getContractId();
                leaveEventListLeaveEvent.setContractId(contract);
                leaveEventListLeaveEvent = em.merge(leaveEventListLeaveEvent);
                if (oldContractIdOfLeaveEventListLeaveEvent != null) {
                    oldContractIdOfLeaveEventListLeaveEvent.getLeaveEventList().remove(leaveEventListLeaveEvent);
                    oldContractIdOfLeaveEventListLeaveEvent = em.merge(oldContractIdOfLeaveEventListLeaveEvent);
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
            Employee employeeIdOld = persistentContract.getEmployeeId();
            Employee employeeIdNew = contract.getEmployeeId();
            List<LeaveEvent> leaveEventListOld = persistentContract.getLeaveEventList();
            List<LeaveEvent> leaveEventListNew = contract.getLeaveEventList();
            List<String> illegalOrphanMessages = null;
            for (LeaveEvent leaveEventListOldLeaveEvent : leaveEventListOld) {
                if (!leaveEventListNew.contains(leaveEventListOldLeaveEvent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LeaveEvent " + leaveEventListOldLeaveEvent + " since its contractId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getId());
                contract.setEmployeeId(employeeIdNew);
            }
            List<LeaveEvent> attachedLeaveEventListNew = new ArrayList<LeaveEvent>();
            for (LeaveEvent leaveEventListNewLeaveEventToAttach : leaveEventListNew) {
                leaveEventListNewLeaveEventToAttach = em.getReference(leaveEventListNewLeaveEventToAttach.getClass(), leaveEventListNewLeaveEventToAttach.getId());
                attachedLeaveEventListNew.add(leaveEventListNewLeaveEventToAttach);
            }
            leaveEventListNew = attachedLeaveEventListNew;
            contract.setLeaveEventList(leaveEventListNew);
            contract = em.merge(contract);
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getContractList().remove(contract);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getContractList().add(contract);
                employeeIdNew = em.merge(employeeIdNew);
            }
            for (LeaveEvent leaveEventListNewLeaveEvent : leaveEventListNew) {
                if (!leaveEventListOld.contains(leaveEventListNewLeaveEvent)) {
                    Contract oldContractIdOfLeaveEventListNewLeaveEvent = leaveEventListNewLeaveEvent.getContractId();
                    leaveEventListNewLeaveEvent.setContractId(contract);
                    leaveEventListNewLeaveEvent = em.merge(leaveEventListNewLeaveEvent);
                    if (oldContractIdOfLeaveEventListNewLeaveEvent != null && !oldContractIdOfLeaveEventListNewLeaveEvent.equals(contract)) {
                        oldContractIdOfLeaveEventListNewLeaveEvent.getLeaveEventList().remove(leaveEventListNewLeaveEvent);
                        oldContractIdOfLeaveEventListNewLeaveEvent = em.merge(oldContractIdOfLeaveEventListNewLeaveEvent);
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
                illegalOrphanMessages.add("This Contract (" + contract + ") cannot be destroyed since the LeaveEvent " + leaveEventListOrphanCheckLeaveEvent + " in its leaveEventList field has a non-nullable contractId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employee employeeId = contract.getEmployeeId();
            if (employeeId != null) {
                employeeId.getContractList().remove(contract);
                employeeId = em.merge(employeeId);
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
    
}
