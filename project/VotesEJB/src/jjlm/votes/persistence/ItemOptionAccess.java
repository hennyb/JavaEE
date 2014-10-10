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
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.persistence.entities.ItemOption;

@Stateless
@LocalBean
public class ItemOptionAccess extends AbstractAccess<ItemOption, ItemOptionTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public ItemOptionAccess() {
        super(ItemOption.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<ItemOption> getOptions(int itemId) {

        return em.createQuery("SELECT o FROM ItemOption o"
                + " WHERE o.item.id = :itemId", ItemOption.class)
                .setParameter("itemId", itemId)
                .getResultList();

    }

    public void resetCount(int pollID) {

        em.createQuery("update ItemOption set count=0"
                + " Where item.poll.id = :pollId")
                .setParameter("pollId", pollID).executeUpdate();
    }
    
    public void incrementCount(int optionId){
        em.createQuery("update ItemOption i"
                + " set i.votes=i.votes+1"
                + " Where i.id = :optionId")
                .setParameter("optionId", optionId).executeUpdate();
    }

}
