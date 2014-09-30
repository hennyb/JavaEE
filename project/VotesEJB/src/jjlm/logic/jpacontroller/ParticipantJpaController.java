/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.jpacontroller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jjlm.logic.jpacontroller.exceptions.NonexistentEntityException;
import jjlm.logic.jpacontroller.exceptions.RollbackFailureException;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author henny
 */
public class ParticipantJpaController implements Serializable {

    public ParticipantJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Participant participant) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Poll poll = participant.getPoll();
            if (poll != null) {
                poll = em.getReference(poll.getClass(), poll.getId());
                participant.setPoll(poll);
            }
            em.persist(participant);
            if (poll != null) {
                poll.getParticipants().add(participant);
                poll = em.merge(poll);
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

    public void edit(Participant participant) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Participant persistentParticipant = em.find(Participant.class, participant.getId());
            Poll pollOld = persistentParticipant.getPoll();
            Poll pollNew = participant.getPoll();
            if (pollNew != null) {
                pollNew = em.getReference(pollNew.getClass(), pollNew.getId());
                participant.setPoll(pollNew);
            }
            participant = em.merge(participant);
            if (pollOld != null && !pollOld.equals(pollNew)) {
                pollOld.getParticipants().remove(participant);
                pollOld = em.merge(pollOld);
            }
            if (pollNew != null && !pollNew.equals(pollOld)) {
                pollNew.getParticipants().add(participant);
                pollNew = em.merge(pollNew);
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
                Integer id = participant.getId();
                if (findParticipant(id) == null) {
                    throw new NonexistentEntityException("The participant with id " + id + " no longer exists.");
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
            Participant participant;
            try {
                participant = em.getReference(Participant.class, id);
                participant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participant with id " + id + " no longer exists.", enfe);
            }
            Poll poll = participant.getPoll();
            if (poll != null) {
                poll.getParticipants().remove(participant);
                poll = em.merge(poll);
            }
            em.remove(participant);
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

    public List<Participant> findParticipantEntities() {
        return findParticipantEntities(true, -1, -1);
    }

    public List<Participant> findParticipantEntities(int maxResults, int firstResult) {
        return findParticipantEntities(false, maxResults, firstResult);
    }

    private List<Participant> findParticipantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participant.class));
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

    public Participant findParticipant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participant.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participant> rt = cq.from(Participant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
