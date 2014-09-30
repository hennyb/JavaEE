/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jjlm.votes.persistence.entities.Poll;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jjlm.logic.jpacontroller.exceptions.NonexistentEntityException;
import jjlm.logic.jpacontroller.exceptions.RollbackFailureException;
import jjlm.votes.persistence.entities.Organizer;

/**
 *
 * @author henny
 */
public class OrganizerJpaController implements Serializable {

    public OrganizerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organizer organizer) throws RollbackFailureException, Exception {
        if (organizer.getPolls() == null) {
            organizer.setPolls(new ArrayList<Poll>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Poll> attachedPolls = new ArrayList<Poll>();
            for (Poll pollsPollToAttach : organizer.getPolls()) {
                pollsPollToAttach = em.getReference(pollsPollToAttach.getClass(), pollsPollToAttach.getId());
                attachedPolls.add(pollsPollToAttach);
            }
            organizer.setPolls(attachedPolls);
            em.persist(organizer);
            for (Poll pollsPoll : organizer.getPolls()) {
                pollsPoll.getOrganizer().add(organizer);
                pollsPoll = em.merge(pollsPoll);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Organizer organizer) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organizer persistentOrganizer = em.find(Organizer.class, organizer.getId());
            List<Poll> pollsOld = persistentOrganizer.getPolls();
            List<Poll> pollsNew = organizer.getPolls();
            List<Poll> attachedPollsNew = new ArrayList<Poll>();
            for (Poll pollsNewPollToAttach : pollsNew) {
                pollsNewPollToAttach = em.getReference(pollsNewPollToAttach.getClass(), pollsNewPollToAttach.getId());
                attachedPollsNew.add(pollsNewPollToAttach);
            }
            pollsNew = attachedPollsNew;
            organizer.setPolls(pollsNew);
            organizer = em.merge(organizer);
            for (Poll pollsOldPoll : pollsOld) {
                if (!pollsNew.contains(pollsOldPoll)) {
                    pollsOldPoll.getOrganizer().remove(organizer);
                    pollsOldPoll = em.merge(pollsOldPoll);
                }
            }
            for (Poll pollsNewPoll : pollsNew) {
                if (!pollsOld.contains(pollsNewPoll)) {
                    pollsNewPoll.getOrganizer().add(organizer);
                    pollsNewPoll = em.merge(pollsNewPoll);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = organizer.getId();
                if (findOrganizer(id) == null) {
                    throw new NonexistentEntityException("The organizer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organizer organizer;
            try {
                organizer = em.getReference(Organizer.class, id);
                organizer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizer with id " + id + " no longer exists.", enfe);
            }
            List<Poll> polls = organizer.getPolls();
            for (Poll pollsPoll : polls) {
                pollsPoll.getOrganizer().remove(organizer);
                pollsPoll = em.merge(pollsPoll);
            }
            em.remove(organizer);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Organizer> findOrganizerEntities() {
        return findOrganizerEntities(true, -1, -1);
    }

    public List<Organizer> findOrganizerEntities(int maxResults, int firstResult) {
        return findOrganizerEntities(false, maxResults, firstResult);
    }

    private List<Organizer> findOrganizerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Organizer.class));
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

    public Organizer findOrganizer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organizer.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Organizer> rt = cq.from(Organizer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
