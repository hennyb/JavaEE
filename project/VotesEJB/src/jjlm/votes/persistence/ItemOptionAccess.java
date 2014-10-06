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
}
