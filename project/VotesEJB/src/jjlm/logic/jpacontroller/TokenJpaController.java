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
import jjlm.votes.persistence.entities.Poll;
import jjlm.votes.persistence.entities.Token;

/**
 *
 * @author henny
 */
public class TokenJpaController implements Serializable {

    public TokenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Token token) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Poll poll = token.getPoll();
            if (poll != null) {
                poll = em.getReference(poll.getClass(), poll.getId());
                token.setPoll(poll);
            }
            em.persist(token);
            if (poll != null) {
                poll.getTokens().add(token);
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

    public void edit(Token token) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Token persistentToken = em.find(Token.class, token.getId());
            Poll pollOld = persistentToken.getPoll();
            Poll pollNew = token.getPoll();
            if (pollNew != null) {
                pollNew = em.getReference(pollNew.getClass(), pollNew.getId());
                token.setPoll(pollNew);
            }
            token = em.merge(token);
            if (pollOld != null && !pollOld.equals(pollNew)) {
                pollOld.getTokens().remove(token);
                pollOld = em.merge(pollOld);
            }
            if (pollNew != null && !pollNew.equals(pollOld)) {
                pollNew.getTokens().add(token);
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
                Integer id = token.getId();
                if (findToken(id) == null) {
                    throw new NonexistentEntityException("The token with id " + id + " no longer exists.");
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
            Token token;
            try {
                token = em.getReference(Token.class, id);
                token.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The token with id " + id + " no longer exists.", enfe);
            }
            Poll poll = token.getPoll();
            if (poll != null) {
                poll.getTokens().remove(token);
                poll = em.merge(poll);
            }
            em.remove(token);
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

    public List<Token> findTokenEntities() {
        return findTokenEntities(true, -1, -1);
    }

    public List<Token> findTokenEntities(int maxResults, int firstResult) {
        return findTokenEntities(false, maxResults, firstResult);
    }

    private List<Token> findTokenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Token.class));
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

    public Token findToken(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Token.class, id);
        } finally {
            em.close();
        }
    }

    public int getTokenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Token> rt = cq.from(Token.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
