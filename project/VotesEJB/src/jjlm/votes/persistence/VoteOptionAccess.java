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
import jjlm.votes.logic.to.VoteOptionTO;
import jjlm.votes.persistence.entities.VoteOption;

@Stateless
@LocalBean
public class VoteOptionAccess extends NamedAccess<VoteOption, VoteOptionTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public VoteOptionAccess() {
        super(VoteOption.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
