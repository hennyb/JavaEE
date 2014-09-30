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
import jjlm.votes.persistence.entities.VoteOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jjlm.logic.jpacontroller.exceptions.NonexistentEntityException;
import jjlm.logic.jpacontroller.exceptions.RollbackFailureException;
import jjlm.votes.persistence.entities.Item;

/**
 *
 * @author henny
 */
public class ItemJpaController implements Serializable {

    public ItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) throws RollbackFailureException, Exception {
        if (item.getOptions() == null) {
            item.setOptions(new HashSet<VoteOption>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Poll poll = item.getPoll();
            if (poll != null) {
                poll = em.getReference(poll.getClass(), poll.getId());
                item.setPoll(poll);
            }
            Set<VoteOption> attachedOptions = new HashSet<VoteOption>();
            for (VoteOption optionsVoteOptionToAttach : item.getOptions()) {
                optionsVoteOptionToAttach = em.getReference(optionsVoteOptionToAttach.getClass(), optionsVoteOptionToAttach.getId());
                attachedOptions.add(optionsVoteOptionToAttach);
            }
            item.setOptions(attachedOptions);
            em.persist(item);
            if (poll != null) {
                poll.getItems().add(item);
                poll = em.merge(poll);
            }
            for (VoteOption optionsVoteOption : item.getOptions()) {
                Item oldItemOfOptionsVoteOption = optionsVoteOption.getItem();
                optionsVoteOption.setItem(item);
                optionsVoteOption = em.merge(optionsVoteOption);
                if (oldItemOfOptionsVoteOption != null) {
                    oldItemOfOptionsVoteOption.getOptions().remove(optionsVoteOption);
                    oldItemOfOptionsVoteOption = em.merge(oldItemOfOptionsVoteOption);
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

    public void edit(Item item) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item persistentItem = em.find(Item.class, item.getId());
            Poll pollOld = persistentItem.getPoll();
            Poll pollNew = item.getPoll();
            Set<VoteOption> optionsOld = persistentItem.getOptions();
            Set<VoteOption> optionsNew = item.getOptions();
            if (pollNew != null) {
                pollNew = em.getReference(pollNew.getClass(), pollNew.getId());
                item.setPoll(pollNew);
            }
            Set<VoteOption> attachedOptionsNew = new HashSet<VoteOption>();
            for (VoteOption optionsNewVoteOptionToAttach : optionsNew) {
                optionsNewVoteOptionToAttach = em.getReference(optionsNewVoteOptionToAttach.getClass(), optionsNewVoteOptionToAttach.getId());
                attachedOptionsNew.add(optionsNewVoteOptionToAttach);
            }
            optionsNew = attachedOptionsNew;
            item.setOptions(optionsNew);
            item = em.merge(item);
            if (pollOld != null && !pollOld.equals(pollNew)) {
                pollOld.getItems().remove(item);
                pollOld = em.merge(pollOld);
            }
            if (pollNew != null && !pollNew.equals(pollOld)) {
                pollNew.getItems().add(item);
                pollNew = em.merge(pollNew);
            }
            for (VoteOption optionsOldVoteOption : optionsOld) {
                if (!optionsNew.contains(optionsOldVoteOption)) {
                    optionsOldVoteOption.setItem(null);
                    optionsOldVoteOption = em.merge(optionsOldVoteOption);
                }
            }
            for (VoteOption optionsNewVoteOption : optionsNew) {
                if (!optionsOld.contains(optionsNewVoteOption)) {
                    Item oldItemOfOptionsNewVoteOption = optionsNewVoteOption.getItem();
                    optionsNewVoteOption.setItem(item);
                    optionsNewVoteOption = em.merge(optionsNewVoteOption);
                    if (oldItemOfOptionsNewVoteOption != null && !oldItemOfOptionsNewVoteOption.equals(item)) {
                        oldItemOfOptionsNewVoteOption.getOptions().remove(optionsNewVoteOption);
                        oldItemOfOptionsNewVoteOption = em.merge(oldItemOfOptionsNewVoteOption);
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
                Integer id = item.getId();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
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
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            Poll poll = item.getPoll();
            if (poll != null) {
                poll.getItems().remove(item);
                poll = em.merge(poll);
            }
            Set<VoteOption> options = item.getOptions();
            for (VoteOption optionsVoteOption : options) {
                optionsVoteOption.setItem(null);
                optionsVoteOption = em.merge(optionsVoteOption);
            }
            em.remove(item);
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

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
