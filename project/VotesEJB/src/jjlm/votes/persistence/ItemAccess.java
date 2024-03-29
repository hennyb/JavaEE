/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.persistence.entities.Item;

/**
 *
 * @author henny
 */
@Stateless
@LocalBean
public class ItemAccess extends AbstractAccess<Item, ItemTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public ItemAccess() {
        super(Item.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Item> getItems(int pollId) {

        return em.createQuery("SELECT i FROM Item i"
                + " WHERE i.poll.id = :pollId", Item.class)
                .setParameter("pollId", pollId)
                .getResultList();

    }

    public void resetAbstainedVotes(int pollId) {

        em.createQuery("update Item set abstainedVotes=0"
                + " Where poll.id = :pollId")
                .setParameter("pollId", pollId).executeUpdate();

    }

    public boolean isItemTitleUnique(int pollId, String title) {
        return em.createQuery("select count(i) From Item i"
                + " Where i.poll.id = :pollId"
                + " and i.title = :title", Long.class)
                .setParameter("pollId", pollId)
                .setParameter("title", title).getSingleResult() == 0;

    }

    public boolean isItemTitleUnique(int pollId, int itemId, String title) {
        return em.createQuery("select count(i) From Item i"
                + " Where i.poll.id = :pollId"
                + " and i.title = :title"
                + " and i.id != :itemId", Long.class)
                .setParameter("pollId", pollId)
                .setParameter("title", title)
                .setParameter("itemId", itemId)
                .getSingleResult() == 0;
    }

    public List<Integer> getItemIdsOfOrganizer(int organizerId) {
        return em.createQuery("SELECT item.id FROM Item item"
                + " where item.poll.organizer.id = :organizerId"
                + "", Integer.class)
                .setParameter("organizerId", organizerId)
                .getResultList();
    }

}
