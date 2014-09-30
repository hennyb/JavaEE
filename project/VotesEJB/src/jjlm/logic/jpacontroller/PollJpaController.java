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
import jjlm.votes.persistence.entities.Token;
import java.util.ArrayList;
import java.util.List;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Item;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jjlm.logic.jpacontroller.exceptions.NonexistentEntityException;
import jjlm.logic.jpacontroller.exceptions.RollbackFailureException;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author henny
 */
public class PollJpaController implements Serializable {

    public PollJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Poll poll) throws RollbackFailureException, Exception {
        if (poll.getTokens() == null) {
            poll.setTokens(new ArrayList<Token>());
        }
        if (poll.getParticipants() == null) {
            poll.setParticipants(new ArrayList<Participant>());
        }
        if (poll.getOrganizer() == null) {
            poll.setOrganizer(new ArrayList<Organizer>());
        }
        if (poll.getItems() == null) {
            poll.setItems(new HashSet<Item>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Token> attachedTokens = new ArrayList<Token>();
            for (Token tokensTokenToAttach : poll.getTokens()) {
                tokensTokenToAttach = em.getReference(tokensTokenToAttach.getClass(), tokensTokenToAttach.getId());
                attachedTokens.add(tokensTokenToAttach);
            }
            poll.setTokens(attachedTokens);
            List<Participant> attachedParticipants = new ArrayList<Participant>();
            for (Participant participantsParticipantToAttach : poll.getParticipants()) {
                participantsParticipantToAttach = em.getReference(participantsParticipantToAttach.getClass(), participantsParticipantToAttach.getId());
                attachedParticipants.add(participantsParticipantToAttach);
            }
            poll.setParticipants(attachedParticipants);
            List<Organizer> attachedOrganizer = new ArrayList<Organizer>();
            for (Organizer organizerOrganizerToAttach : poll.getOrganizer()) {
                organizerOrganizerToAttach = em.getReference(organizerOrganizerToAttach.getClass(), organizerOrganizerToAttach.getId());
                attachedOrganizer.add(organizerOrganizerToAttach);
            }
            poll.setOrganizer(attachedOrganizer);
            Set<Item> attachedItems = new HashSet<Item>();
            for (Item itemsItemToAttach : poll.getItems()) {
                itemsItemToAttach = em.getReference(itemsItemToAttach.getClass(), itemsItemToAttach.getId());
                attachedItems.add(itemsItemToAttach);
            }
            poll.setItems(attachedItems);
            em.persist(poll);
            for (Token tokensToken : poll.getTokens()) {
                Poll oldPollOfTokensToken = tokensToken.getPoll();
                tokensToken.setPoll(poll);
                tokensToken = em.merge(tokensToken);
                if (oldPollOfTokensToken != null) {
                    oldPollOfTokensToken.getTokens().remove(tokensToken);
                    oldPollOfTokensToken = em.merge(oldPollOfTokensToken);
                }
            }
            for (Participant participantsParticipant : poll.getParticipants()) {
                Poll oldPollOfParticipantsParticipant = participantsParticipant.getPoll();
                participantsParticipant.setPoll(poll);
                participantsParticipant = em.merge(participantsParticipant);
                if (oldPollOfParticipantsParticipant != null) {
                    oldPollOfParticipantsParticipant.getParticipants().remove(participantsParticipant);
                    oldPollOfParticipantsParticipant = em.merge(oldPollOfParticipantsParticipant);
                }
            }
            for (Organizer organizerOrganizer : poll.getOrganizer()) {
                organizerOrganizer.getPolls().add(poll);
                organizerOrganizer = em.merge(organizerOrganizer);
            }
            for (Item itemsItem : poll.getItems()) {
                Poll oldPollOfItemsItem = itemsItem.getPoll();
                itemsItem.setPoll(poll);
                itemsItem = em.merge(itemsItem);
                if (oldPollOfItemsItem != null) {
                    oldPollOfItemsItem.getItems().remove(itemsItem);
                    oldPollOfItemsItem = em.merge(oldPollOfItemsItem);
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

    public void edit(Poll poll) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Poll persistentPoll = em.find(Poll.class, poll.getId());
            List<Token> tokensOld = persistentPoll.getTokens();
            List<Token> tokensNew = poll.getTokens();
            List<Participant> participantsOld = persistentPoll.getParticipants();
            List<Participant> participantsNew = poll.getParticipants();
            List<Organizer> organizerOld = persistentPoll.getOrganizer();
            List<Organizer> organizerNew = poll.getOrganizer();
            Set<Item> itemsOld = persistentPoll.getItems();
            Set<Item> itemsNew = poll.getItems();
            List<Token> attachedTokensNew = new ArrayList<Token>();
            for (Token tokensNewTokenToAttach : tokensNew) {
                tokensNewTokenToAttach = em.getReference(tokensNewTokenToAttach.getClass(), tokensNewTokenToAttach.getId());
                attachedTokensNew.add(tokensNewTokenToAttach);
            }
            tokensNew = attachedTokensNew;
            poll.setTokens(tokensNew);
            List<Participant> attachedParticipantsNew = new ArrayList<Participant>();
            for (Participant participantsNewParticipantToAttach : participantsNew) {
                participantsNewParticipantToAttach = em.getReference(participantsNewParticipantToAttach.getClass(), participantsNewParticipantToAttach.getId());
                attachedParticipantsNew.add(participantsNewParticipantToAttach);
            }
            participantsNew = attachedParticipantsNew;
            poll.setParticipants(participantsNew);
            List<Organizer> attachedOrganizerNew = new ArrayList<Organizer>();
            for (Organizer organizerNewOrganizerToAttach : organizerNew) {
                organizerNewOrganizerToAttach = em.getReference(organizerNewOrganizerToAttach.getClass(), organizerNewOrganizerToAttach.getId());
                attachedOrganizerNew.add(organizerNewOrganizerToAttach);
            }
            organizerNew = attachedOrganizerNew;
            poll.setOrganizer(organizerNew);
            Set<Item> attachedItemsNew = new HashSet<Item>();
            for (Item itemsNewItemToAttach : itemsNew) {
                itemsNewItemToAttach = em.getReference(itemsNewItemToAttach.getClass(), itemsNewItemToAttach.getId());
                attachedItemsNew.add(itemsNewItemToAttach);
            }
            itemsNew = attachedItemsNew;
            poll.setItems(itemsNew);
            poll = em.merge(poll);
            for (Token tokensOldToken : tokensOld) {
                if (!tokensNew.contains(tokensOldToken)) {
                    tokensOldToken.setPoll(null);
                    tokensOldToken = em.merge(tokensOldToken);
                }
            }
            for (Token tokensNewToken : tokensNew) {
                if (!tokensOld.contains(tokensNewToken)) {
                    Poll oldPollOfTokensNewToken = tokensNewToken.getPoll();
                    tokensNewToken.setPoll(poll);
                    tokensNewToken = em.merge(tokensNewToken);
                    if (oldPollOfTokensNewToken != null && !oldPollOfTokensNewToken.equals(poll)) {
                        oldPollOfTokensNewToken.getTokens().remove(tokensNewToken);
                        oldPollOfTokensNewToken = em.merge(oldPollOfTokensNewToken);
                    }
                }
            }
            for (Participant participantsOldParticipant : participantsOld) {
                if (!participantsNew.contains(participantsOldParticipant)) {
                    participantsOldParticipant.setPoll(null);
                    participantsOldParticipant = em.merge(participantsOldParticipant);
                }
            }
            for (Participant participantsNewParticipant : participantsNew) {
                if (!participantsOld.contains(participantsNewParticipant)) {
                    Poll oldPollOfParticipantsNewParticipant = participantsNewParticipant.getPoll();
                    participantsNewParticipant.setPoll(poll);
                    participantsNewParticipant = em.merge(participantsNewParticipant);
                    if (oldPollOfParticipantsNewParticipant != null && !oldPollOfParticipantsNewParticipant.equals(poll)) {
                        oldPollOfParticipantsNewParticipant.getParticipants().remove(participantsNewParticipant);
                        oldPollOfParticipantsNewParticipant = em.merge(oldPollOfParticipantsNewParticipant);
                    }
                }
            }
            for (Organizer organizerOldOrganizer : organizerOld) {
                if (!organizerNew.contains(organizerOldOrganizer)) {
                    organizerOldOrganizer.getPolls().remove(poll);
                    organizerOldOrganizer = em.merge(organizerOldOrganizer);
                }
            }
            for (Organizer organizerNewOrganizer : organizerNew) {
                if (!organizerOld.contains(organizerNewOrganizer)) {
                    organizerNewOrganizer.getPolls().add(poll);
                    organizerNewOrganizer = em.merge(organizerNewOrganizer);
                }
            }
            for (Item itemsOldItem : itemsOld) {
                if (!itemsNew.contains(itemsOldItem)) {
                    itemsOldItem.setPoll(null);
                    itemsOldItem = em.merge(itemsOldItem);
                }
            }
            for (Item itemsNewItem : itemsNew) {
                if (!itemsOld.contains(itemsNewItem)) {
                    Poll oldPollOfItemsNewItem = itemsNewItem.getPoll();
                    itemsNewItem.setPoll(poll);
                    itemsNewItem = em.merge(itemsNewItem);
                    if (oldPollOfItemsNewItem != null && !oldPollOfItemsNewItem.equals(poll)) {
                        oldPollOfItemsNewItem.getItems().remove(itemsNewItem);
                        oldPollOfItemsNewItem = em.merge(oldPollOfItemsNewItem);
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
                Integer id = poll.getId();
                if (findPoll(id) == null) {
                    throw new NonexistentEntityException("The poll with id " + id + " no longer exists.");
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
            Poll poll;
            try {
                poll = em.getReference(Poll.class, id);
                poll.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poll with id " + id + " no longer exists.", enfe);
            }
            List<Token> tokens = poll.getTokens();
            for (Token tokensToken : tokens) {
                tokensToken.setPoll(null);
                tokensToken = em.merge(tokensToken);
            }
            List<Participant> participants = poll.getParticipants();
            for (Participant participantsParticipant : participants) {
                participantsParticipant.setPoll(null);
                participantsParticipant = em.merge(participantsParticipant);
            }
            List<Organizer> organizer = poll.getOrganizer();
            for (Organizer organizerOrganizer : organizer) {
                organizerOrganizer.getPolls().remove(poll);
                organizerOrganizer = em.merge(organizerOrganizer);
            }
            Set<Item> items = poll.getItems();
            for (Item itemsItem : items) {
                itemsItem.setPoll(null);
                itemsItem = em.merge(itemsItem);
            }
            em.remove(poll);
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

    public List<Poll> findPollEntities() {
        return findPollEntities(true, -1, -1);
    }

    public List<Poll> findPollEntities(int maxResults, int firstResult) {
        return findPollEntities(false, maxResults, firstResult);
    }

    private List<Poll> findPollEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Poll.class));
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

    public Poll findPoll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Poll.class, id);
        } finally {
            em.close();
        }
    }

    public int getPollCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Poll> rt = cq.from(Poll.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
