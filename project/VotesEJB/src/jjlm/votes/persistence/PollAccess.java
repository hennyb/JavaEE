/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author henny
 */
public class PollAccess extends NamedAccess<Poll, PollTO>{
    
    @PersistenceContext(name="VotesEJBPU")
    EntityManager em;

    public PollAccess() {
        super(Poll.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
