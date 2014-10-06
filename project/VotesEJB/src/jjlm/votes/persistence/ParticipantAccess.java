/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.persistence.entities.Participant;

/**
 *
 * @author henny
 */
public class ParticipantAccess extends AbstractAccess<Participant, ParticipantTO>{
    
    @PersistenceContext(unitName = "VotesEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipantAccess() {
        super(Participant.class);
    }
}
