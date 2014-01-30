/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itechkenya.leavemanager.domain.LeaveEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.itechkenya.leavemanager.domain.LeaveType;
import org.itechkenya.leavemanager.jpa.exceptions.IllegalOrphanException;
import org.itechkenya.leavemanager.jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gitahi
 */
public class LeaveTypeJpaController implements Serializable {

    public LeaveTypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LeaveType leaveType) {
        if (leaveType.getLeaveEventList() == null) {
            leaveType.setLeaveEventList(new ArrayList<LeaveEvent>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<LeaveEvent> attachedLeaveEventList = new ArrayList<LeaveEvent>();
            for (LeaveEvent leaveEventListLeaveEventToAttach : leaveType.getLeaveEventList()) {
                leaveEventListLeaveEventToAttach = em.getReference(leaveEventListLeaveEventToAttach.getClass(), leaveEventListLeaveEventToAttach.getId());
                attachedLeaveEventList.add(leaveEventListLeaveEventToAttach);
            }
            leaveType.setLeaveEventList(attachedLeaveEventList);
            em.persist(leaveType);
            for (LeaveEvent leaveEventListLeaveEvent : leaveType.getLeaveEventList()) {
                LeaveType oldLeaveTypeOfLeaveEventListLeaveEvent = leaveEventListLeaveEvent.getLeaveType();
                leaveEventListLeaveEvent.setLeaveType(leaveType);
                leaveEventListLeaveEvent = em.merge(leaveEventListLeaveEvent);
                if (oldLeaveTypeOfLeaveEventListLeaveEvent != null) {
                    oldLeaveTypeOfLeaveEventListLeaveEvent.getLeaveEventList().remove(leaveEventListLeaveEvent);
                    oldLeaveTypeOfLeaveEventListLeaveEvent = em.merge(oldLeaveTypeOfLeaveEventListLeaveEvent);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LeaveType leaveType) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LeaveType persistentLeaveType = em.find(LeaveType.class, leaveType.getId());
            List<LeaveEvent> leaveEventListOld = persistentLeaveType.getLeaveEventList();
            List<LeaveEvent> leaveEventListNew = leaveType.getLeaveEventList();
            List<String> illegalOrphanMessages = null;
            for (LeaveEvent leaveEventListOldLeaveEvent : leaveEventListOld) {
                if (!leaveEventListNew.contains(leaveEventListOldLeaveEvent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LeaveEvent " + leaveEventListOldLeaveEvent + " since its leaveType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<LeaveEvent> attachedLeaveEventListNew = new ArrayList<LeaveEvent>();
            for (LeaveEvent leaveEventListNewLeaveEventToAttach : leaveEventListNew) {
                leaveEventListNewLeaveEventToAttach = em.getReference(leaveEventListNewLeaveEventToAttach.getClass(), leaveEventListNewLeaveEventToAttach.getId());
                attachedLeaveEventListNew.add(leaveEventListNewLeaveEventToAttach);
            }
            leaveEventListNew = attachedLeaveEventListNew;
            leaveType.setLeaveEventList(leaveEventListNew);
            leaveType = em.merge(leaveType);
            for (LeaveEvent leaveEventListNewLeaveEvent : leaveEventListNew) {
                if (!leaveEventListOld.contains(leaveEventListNewLeaveEvent)) {
                    LeaveType oldLeaveTypeOfLeaveEventListNewLeaveEvent = leaveEventListNewLeaveEvent.getLeaveType();
                    leaveEventListNewLeaveEvent.setLeaveType(leaveType);
                    leaveEventListNewLeaveEvent = em.merge(leaveEventListNewLeaveEvent);
                    if (oldLeaveTypeOfLeaveEventListNewLeaveEvent != null && !oldLeaveTypeOfLeaveEventListNewLeaveEvent.equals(leaveType)) {
                        oldLeaveTypeOfLeaveEventListNewLeaveEvent.getLeaveEventList().remove(leaveEventListNewLeaveEvent);
                        oldLeaveTypeOfLeaveEventListNewLeaveEvent = em.merge(oldLeaveTypeOfLeaveEventListNewLeaveEvent);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = leaveType.getId();
                if (findLeaveType(id) == null) {
                    throw new NonexistentEntityException("The leaveType with id " + id + " no longer exists.");
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
            LeaveType leaveType;
            try {
                leaveType = em.getReference(LeaveType.class, id);
                leaveType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leaveType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LeaveEvent> leaveEventListOrphanCheck = leaveType.getLeaveEventList();
            for (LeaveEvent leaveEventListOrphanCheckLeaveEvent : leaveEventListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LeaveType (" + leaveType + ") cannot be destroyed since the LeaveEvent " + leaveEventListOrphanCheckLeaveEvent + " in its leaveEventList field has a non-nullable leaveType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(leaveType);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LeaveType> findLeaveTypeEntities() {
        return findLeaveTypeEntities(true, -1, -1);
    }

    public List<LeaveType> findLeaveTypeEntities(int maxResults, int firstResult) {
        return findLeaveTypeEntities(false, maxResults, firstResult);
    }

    private List<LeaveType> findLeaveTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LeaveType.class));
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

    public LeaveType findLeaveType(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LeaveType.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeaveTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LeaveType> rt = cq.from(LeaveType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<LeaveType> findAutoIncrementableLeaveTypes() {
        EntityManager em = getEntityManager();
        TypedQuery<LeaveType> query
                = em.createNamedQuery("LeaveType.findByDaysPerMonth", LeaveType.class);
        query.setParameter("daysPerMonth", BigDecimal.ZERO);
        return query.getResultList();
    }
}
