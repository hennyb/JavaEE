/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jjlm.votes.persistence.entities.Poll;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.ParticipantList;
import jjlm.votes.persistence.jpacontroller.exceptions.NonexistentEntityException;
import jjlm.votes.persistence.jpacontroller.exceptions.RollbackFailureException;

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
            organizer.setPolls(new HashSet<Poll>());
        }
        if (organizer.getParticipantLists() == null) {
            organizer.setParticipantLists(new HashSet<ParticipantList>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<Poll> attachedPolls = new HashSet<Poll>();
            for (Poll pollsPollToAttach : organizer.getPolls()) {
                pollsPollToAttach = em.getReference(pollsPollToAttach.getClass(), pollsPollToAttach.getId());
                attachedPolls.add(pollsPollToAttach);
            }
            organizer.setPolls(attachedPolls);
            Set<ParticipantList> attachedParticipantLists = new HashSet<ParticipantList>();
            for (ParticipantList participantListsParticipantListToAttach : organizer.getParticipantLists()) {
                participantListsParticipantListToAttach = em.getReference(participantListsParticipantListToAttach.getClass(), participantListsParticipantListToAttach.getId());
                attachedParticipantLists.add(participantListsParticipantListToAttach);
            }
            organizer.setParticipantLists(attachedParticipantLists);
            em.persist(organizer);
            for (Poll pollsPoll : organizer.getPolls()) {
                pollsPoll.getOrganizer().add(organizer);
                pollsPoll = em.merge(pollsPoll);
            }
            for (ParticipantList participantListsParticipantList : organizer.getParticipantLists()) {
                Organizer oldOrganizerOfParticipantListsParticipantList = participantListsParticipantList.getOrganizer();
                participantListsParticipantList.setOrganizer(organizer);
                participantListsParticipantList = em.merge(participantListsParticipantList);
                if (oldOrganizerOfParticipantListsParticipantList != null) {
                    oldOrganizerOfParticipantListsParticipantList.getParticipantLists().remove(participantListsParticipantList);
                    oldOrganizerOfParticipantListsParticipantList = em.merge(oldOrganizerOfParticipantListsParticipantList);
                }
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
            Set<Poll> pollsOld = persistentOrganizer.getPolls();
            Set<Poll> pollsNew = organizer.getPolls();
            Set<ParticipantList> participantListsOld = persistentOrganizer.getParticipantLists();
            Set<ParticipantList> participantListsNew = organizer.getParticipantLists();
            Set<Poll> attachedPollsNew = new HashSet<Poll>();
            for (Poll pollsNewPollToAttach : pollsNew) {
                pollsNewPollToAttach = em.getReference(pollsNewPollToAttach.getClass(), pollsNewPollToAttach.getId());
                attachedPollsNew.add(pollsNewPollToAttach);
            }
            pollsNew = attachedPollsNew;
            organizer.setPolls(pollsNew);
            Set<ParticipantList> attachedParticipantListsNew = new HashSet<ParticipantList>();
            for (ParticipantList participantListsNewParticipantListToAttach : participantListsNew) {
                participantListsNewParticipantListToAttach = em.getReference(participantListsNewParticipantListToAttach.getClass(), participantListsNewParticipantListToAttach.getId());
                attachedParticipantListsNew.add(participantListsNewParticipantListToAttach);
            }
            participantListsNew = attachedParticipantListsNew;
            organizer.setParticipantLists(participantListsNew);
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
            for (ParticipantList participantListsOldParticipantList : participantListsOld) {
                if (!participantListsNew.contains(participantListsOldParticipantList)) {
                    participantListsOldParticipantList.setOrganizer(null);
                    participantListsOldParticipantList = em.merge(participantListsOldParticipantList);
                }
            }
            for (ParticipantList participantListsNewParticipantList : participantListsNew) {
                if (!participantListsOld.contains(participantListsNewParticipantList)) {
                    Organizer oldOrganizerOfParticipantListsNewParticipantList = participantListsNewParticipantList.getOrganizer();
                    participantListsNewParticipantList.setOrganizer(organizer);
                    participantListsNewParticipantList = em.merge(participantListsNewParticipantList);
                    if (oldOrganizerOfParticipantListsNewParticipantList != null && !oldOrganizerOfParticipantListsNewParticipantList.equals(organizer)) {
                        oldOrganizerOfParticipantListsNewParticipantList.getParticipantLists().remove(participantListsNewParticipantList);
                        oldOrganizerOfParticipantListsNewParticipantList = em.merge(oldOrganizerOfParticipantListsNewParticipantList);
                    }
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
            Set<Poll> polls = organizer.getPolls();
            for (Poll pollsPoll : polls) {
                pollsPoll.getOrganizer().remove(organizer);
                pollsPoll = em.merge(pollsPoll);
            }
            Set<ParticipantList> participantLists = organizer.getParticipantLists();
            for (ParticipantList participantListsParticipantList : participantLists) {
                participantListsParticipantList.setOrganizer(null);
                participantListsParticipantList = em.merge(participantListsParticipantList);
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
