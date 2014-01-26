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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itechkenya.leavemanager.domain.Contract;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import org.itechkenya.leavemanager.jpa.exceptions.NonexistentEntityException;
import org.itechkenya.leavemanager.jpa.exceptions.PreexistingEntityException;

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

    public void create(LeaveEvent leaveEvent) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contract contractId = leaveEvent.getContractId();
            if (contractId != null) {
                contractId = em.getReference(contractId.getClass(), contractId.getId());
                leaveEvent.setContractId(contractId);
            }
            em.persist(leaveEvent);
            if (contractId != null) {
                contractId.getLeaveEventList().add(leaveEvent);
                contractId = em.merge(contractId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLeaveEvent(leaveEvent.getId()) != null) {
                throw new PreexistingEntityException("LeaveEvent " + leaveEvent + " already exists.", ex);
            }
            throw ex;
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
            Contract contractIdOld = persistentLeaveEvent.getContractId();
            Contract contractIdNew = leaveEvent.getContractId();
            if (contractIdNew != null) {
                contractIdNew = em.getReference(contractIdNew.getClass(), contractIdNew.getId());
                leaveEvent.setContractId(contractIdNew);
            }
            leaveEvent = em.merge(leaveEvent);
            if (contractIdOld != null && !contractIdOld.equals(contractIdNew)) {
                contractIdOld.getLeaveEventList().remove(leaveEvent);
                contractIdOld = em.merge(contractIdOld);
            }
            if (contractIdNew != null && !contractIdNew.equals(contractIdOld)) {
                contractIdNew.getLeaveEventList().add(leaveEvent);
                contractIdNew = em.merge(contractIdNew);
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
            Contract contractId = leaveEvent.getContractId();
            if (contractId != null) {
                contractId.getLeaveEventList().remove(leaveEvent);
                contractId = em.merge(contractId);
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
    
}
