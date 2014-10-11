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
import jjlm.votes.logic.to.TokenTO;
import jjlm.votes.persistence.entities.Token;

@Stateless
@LocalBean
public class TokenAccess extends AbstractAccess<Token, TokenTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public TokenAccess() {
        super(Token.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Token> getTokenOfParticipant(int participantId) {
        return em.createQuery("Select t FROM Token t"
                + " WHERE t.participant.id = :participantId", Token.class)
                .setParameter("participantId", participantId)
                .getResultList();
    }

    public List<Token> getTokensOfPoll(int pollId) {
        return em.createQuery("Select t FROM Token t"
                + " WHERE t.poll.id = :pollId", Token.class)
                .setParameter("pollId", pollId)
                .getResultList();

    }
    
    public Token getTokensOfPollAndParticipant(int pollId, int participantId) {
        return em.createQuery("Select t FROM Token t"
                + " WHERE t.poll.id = :pollId"
                + " AND t.participant.id = :participantId", Token.class)
                .setParameter("pollId", pollId)
                .setParameter("participantId", participantId)
                .getSingleResult();

    }

    public Token getTokenBySignature(String signature) {
        return em.createQuery("Select t From Token t"
                + " Where t.signature = :signature", Token.class)
                .setParameter("signature", signature)
                .getSingleResult();
    }

}
