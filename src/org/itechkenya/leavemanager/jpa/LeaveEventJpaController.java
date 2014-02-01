/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itechkenya.leavemanager.domain.LeaveType;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import org.itechkenya.leavemanager.jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gitahi
 */
public class LeaveEventJpaController implements Serializable {

    public LeaveEventJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LeaveEvent leaveEvent) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LeaveType leaveType = leaveEvent.getLeaveType();
            if (leaveType != null) {
                leaveType = em.getReference(leaveType.getClass(), leaveType.getId());
                leaveEvent.setLeaveType(leaveType);
            }
            Contract contract = leaveEvent.getContract();
            if (contract != null) {
                contract = em.getReference(contract.getClass(), contract.getId());
                leaveEvent.setContract(contract);
            }
            em.persist(leaveEvent);
            if (leaveType != null) {
                leaveType.getLeaveEventList().add(leaveEvent);
                leaveType = em.merge(leaveType);
            }
            if (contract != null) {
                contract.getLeaveEventList().add(leaveEvent);
                contract = em.merge(contract);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LeaveEvent leaveEvent) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LeaveEvent persistentLeaveEvent = em.find(LeaveEvent.class, leaveEvent.getId());
            LeaveType leaveTypeOld = persistentLeaveEvent.getLeaveType();
            LeaveType leaveTypeNew = leaveEvent.getLeaveType();
            Contract contractOld = persistentLeaveEvent.getContract();
            Contract contractNew = leaveEvent.getContract();
            if (leaveTypeNew != null) {
                leaveTypeNew = em.getReference(leaveTypeNew.getClass(), leaveTypeNew.getId());
                leaveEvent.setLeaveType(leaveTypeNew);
            }
            if (contractNew != null) {
                contractNew = em.getReference(contractNew.getClass(), contractNew.getId());
                leaveEvent.setContract(contractNew);
            }
            leaveEvent = em.merge(leaveEvent);
            if (leaveTypeOld != null && !leaveTypeOld.equals(leaveTypeNew)) {
                leaveTypeOld.getLeaveEventList().remove(leaveEvent);
                leaveTypeOld = em.merge(leaveTypeOld);
            }
            if (leaveTypeNew != null && !leaveTypeNew.equals(leaveTypeOld)) {
                leaveTypeNew.getLeaveEventList().add(leaveEvent);
                leaveTypeNew = em.merge(leaveTypeNew);
            }
            if (contractOld != null && !contractOld.equals(contractNew)) {
                contractOld.getLeaveEventList().remove(leaveEvent);
                contractOld = em.merge(contractOld);
            }
            if (contractNew != null && !contractNew.equals(contractOld)) {
                contractNew.getLeaveEventList().add(leaveEvent);
                contractNew = em.merge(contractNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = leaveEvent.getId();
                if (findLeaveEvent(id) == null) {
                    throw new NonexistentEntityException("The leaveEvent with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LeaveEvent leaveEvent;
            try {
                leaveEvent = em.getReference(LeaveEvent.class, id);
                leaveEvent.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leaveEvent with id " + id + " no longer exists.", enfe);
            }
            LeaveType leaveType = leaveEvent.getLeaveType();
            if (leaveType != null) {
                leaveType.getLeaveEventList().remove(leaveEvent);
                leaveType = em.merge(leaveType);
            }
            Contract contract = leaveEvent.getContract();
            if (contract != null) {
                contract.getLeaveEventList().remove(leaveEvent);
                contract = em.merge(contract);
            }
            em.remove(leaveEvent);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LeaveEvent> findLeaveEventEntities() {
        return findLeaveEventEntities(true, -1, -1);
    }

    public List<LeaveEvent> findLeaveEventEntities(int maxResults, int firstResult) {
        return findLeaveEventEntities(false, maxResults, firstResult);
    }

    private List<LeaveEvent> findLeaveEventEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LeaveEvent.class));
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

    public LeaveEvent findLeaveEvent(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LeaveEvent.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeaveEventCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LeaveEvent> rt = cq.from(LeaveEvent.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public LeaveEvent findLeaveEvent(Contract contract, LeaveType leaveType, String month) {
        LeaveEvent leaveEvent = null;

        EntityManager em = getEntityManager();
        TypedQuery<LeaveEvent> query
                = em.createNamedQuery("LeaveEvent.findByContractLeaveTypeAndMonth", LeaveEvent.class);
        query.setParameter("contract", contract);
        query.setParameter("leaveType", leaveType);
        query.setParameter("month", month);
        List<LeaveEvent> leaveEvents = query.getResultList();
        if (leaveEvents != null && !leaveEvents.isEmpty()) {
            leaveEvent = leaveEvents.get(0);
        }

        return leaveEvent;
    }

    public List<LeaveEvent> findLeaveEvents(Contract contract) {
        EntityManager em = getEntityManager();
        TypedQuery<LeaveEvent> query
                = em.createNamedQuery("LeaveEvent.findByContract", LeaveEvent.class);
        query.setParameter("contract", contract);
        return query.getResultList();
    }
}
