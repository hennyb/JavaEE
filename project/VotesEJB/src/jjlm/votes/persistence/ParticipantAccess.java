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
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.TokenTO;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Token;

@Stateless
@LocalBean
public class ParticipantAccess extends AbstractAccess<Participant, ParticipantTO> {

    @PersistenceContext(unitName = "VotesEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipantAccess() {
        super(Participant.class);
    }

    public List<Participant> getParticipantsOfPoll(int pollId) {
        return em.createQuery("SELECT p FROM Participant p"
                + " WHERE p.poll.id = :pollId", Participant.class)
                .setParameter("pollId", pollId)
                .getResultList();
    }

    public ParticipantTO getParticipantWithPoll(int participantId) {

        ParticipantTO particpant = find(participantId).createTO();

        Token t = em.createQuery("Select t FROM Token t"
                + " Where t.participant.id = :participantId", Token.class).setParameter("participantId", participantId).getSingleResult();

        TokenTO token = t.createTO();

        particpant.setToken(token);

        return particpant;

    }
}
