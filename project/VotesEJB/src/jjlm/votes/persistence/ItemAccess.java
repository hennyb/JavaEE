/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

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
public class ItemAccess extends NamedAccess<Item, ItemTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public ItemAccess() {
        super(Item.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
