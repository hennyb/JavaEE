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
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Poll;

@Stateless
@LocalBean
public class PollAccess extends NamedAccess<Poll, PollTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public PollAccess() {
        super(Poll.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Poll> getAllPolls() {
        return em.createQuery("SELECT p FROM Poll p "
                + "", Poll.class).getResultList();
    }

    public List<Poll> getPolls(int organizerId) {
        System.out.println("oid: "+ organizerId);
        return em.createQuery("SELECT p FROM Poll p, IN(p.organizer) o"
                + " where o.id= :organizerId"
                + "", Poll.class)
                .setParameter("organizerId", organizerId)
                .getResultList();

    }

}
