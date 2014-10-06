/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Poll;

@Stateless
@LocalBean
public class PollAccess extends AbstractAccess<Poll, PollTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    @EJB
    OrganizerAccess oa;

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
        System.out.println("oid: " + organizerId);
        return em.createQuery("SELECT p FROM Poll p, IN(p.organizer) o"
                + " where o.id= :organizerId"
                + "", Poll.class)
                .setParameter("organizerId", organizerId)
                .getResultList();

    }

    public Poll getPoll(String name, String description) {
        return em.createQuery("SELECT p FROM Poll p"
                + " where p.name=:name"
                + " AND p.description=:description"
                + "", Poll.class)
                .setParameter("name", name)
                .setParameter("description", description)
                .getResultList().get(0);
    }

    public List<Poll> getPolls(int organizerId, int offset, int max) {
        System.out.println("oid: " + organizerId);
        return em.createQuery("SELECT p FROM Poll p, IN(p.organizer) o"
                + " where o.id= :organizerId"
                + "", Poll.class)
                .setParameter("organizerId", organizerId)
                .setFirstResult(offset)
                .setMaxResults(max)
                .getResultList();
    }

    public Poll addOrganizerToPoll(int pollId, int organizerId) {
        Poll p = find(pollId);
        Organizer o = oa.find(organizerId);

        for (Poll currentPoll : o.getPolls()) {
            if (currentPoll.equals(p)) {
                return p;
            }
        }
        o.getPolls().add(p);
        p.getOrganizer().add(o);

        oa.edit(o);
        p = edit(p);

        return p;
    }

}
