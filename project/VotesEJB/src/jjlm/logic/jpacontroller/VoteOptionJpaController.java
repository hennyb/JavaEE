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
import jjlm.votes.persistence.entities.Item;
import jjlm.votes.persistence.entities.VoteOption;

/**
 *
 * @author henny
 */
public class VoteOptionJpaController implements Serializable {

    public VoteOptionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VoteOption voteOption) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item item = voteOption.getItem();
            if (item != null) {
                item = em.getReference(item.getClass(), item.getId());
                voteOption.setItem(item);
            }
            em.persist(voteOption);
            if (item != null) {
                item.getOptions().add(voteOption);
                item = em.merge(item);
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

    public void edit(VoteOption voteOption) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VoteOption persistentVoteOption = em.find(VoteOption.class, voteOption.getId());
            Item itemOld = persistentVoteOption.getItem();
            Item itemNew = voteOption.getItem();
            if (itemNew != null) {
                itemNew = em.getReference(itemNew.getClass(), itemNew.getId());
                voteOption.setItem(itemNew);
            }
            voteOption = em.merge(voteOption);
            if (itemOld != null && !itemOld.equals(itemNew)) {
                itemOld.getOptions().remove(voteOption);
                itemOld = em.merge(itemOld);
            }
            if (itemNew != null && !itemNew.equals(itemOld)) {
                itemNew.getOptions().add(voteOption);
                itemNew = em.merge(itemNew);
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
                Integer id = voteOption.getId();
                if (findVoteOption(id) == null) {
                    throw new NonexistentEntityException("The voteOption with id " + id + " no longer exists.");
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
            VoteOption voteOption;
            try {
                voteOption = em.getReference(VoteOption.class, id);
                voteOption.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The voteOption with id " + id + " no longer exists.", enfe);
            }
            Item item = voteOption.getItem();
            if (item != null) {
                item.getOptions().remove(voteOption);
                item = em.merge(item);
            }
            em.remove(voteOption);
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

    public List<VoteOption> findVoteOptionEntities() {
        return findVoteOptionEntities(true, -1, -1);
    }

    public List<VoteOption> findVoteOptionEntities(int maxResults, int firstResult) {
        return findVoteOptionEntities(false, maxResults, firstResult);
    }

    private List<VoteOption> findVoteOptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VoteOption.class));
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

    public VoteOption findVoteOption(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VoteOption.class, id);
        } finally {
            em.close();
        }
    }

    public int getVoteOptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VoteOption> rt = cq.from(VoteOption.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
