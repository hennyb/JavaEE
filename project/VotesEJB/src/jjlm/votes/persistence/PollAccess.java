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
        return em.createQuery("SELECT p FROM Poll p"
                + " where p.organizer.id= :organizerId"
                + "", Poll.class)
                .setParameter("organizerId", organizerId)
                .getResultList();

    }

    public Long getPollCount(int organizerId) {

        return em.createQuery("Select count(p) from Poll p"
                + " where p.organizer.id = :organizerId", Long.class)
                .setParameter("organizerId", organizerId)
                .getSingleResult();

    }

    public List<Poll> getPolls(int organizerId, int offset, int limit) {
        System.out.println("oid: " + organizerId);
        return em.createQuery("SELECT p FROM Poll p"
                + " where p.organizer.id= :organizerId"
                + "", Poll.class)
                .setParameter("organizerId", organizerId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Integer> getPollIdsOfOrganizer(int organizerId) {
        return em.createQuery("SELECT p.id FROM Poll p"
                + " where p.organizer.id= :organizerId"
                + "", Integer.class)
                .setParameter("organizerId", organizerId)
                .getResultList();
    }

    public boolean uniqueTitle(String title) {
        return em.createQuery("SELECT COUNT(p) FROM Poll p"
                + " WHERE p.title = :title"
                + "", Long.class)
                .setParameter("title", title)
                .getSingleResult() == 0;
    }

    public boolean uniqueTitle(String title, int pollId) {
        return em.createQuery("SELECT COUNT(p) FROM Poll p"
                + " WHERE p.title = :title"
                + " and p.id != :pollId"
                + "", Long.class)
                .setParameter("title", title)
                .setParameter("pollId", pollId)
                .getSingleResult() == 0;
    }

    public boolean allItemsValid(int pollId) {

        return em.createQuery("Select count(i) FROM Item i"
                + " Where i.poll.id = :pollId"
                + " and i.valid = 0", Long.class)
                .setParameter("pollId", pollId)
                .getSingleResult() == 0;
    }

    public boolean isTitleUnique(int pollId, String title) {
        return em.createQuery("select count(p) From Poll p"
                + " Where p.id = :pollId"
                + " and p.title = :title", Long.class)
                .setParameter("pollId", pollId)
                .setParameter("title", title)
                .getSingleResult() == 0;
    }

    public long getParticipation(int pollId) {
        return em.createQuery("SELECT COUNT(p) FROM Participant p"
                + " WHERE p.poll.id = :pollId"
                + " and p.hasVoted = 1"
                + "", Long.class)
                .setParameter("pollId", pollId)
                .getSingleResult();
    }

    public long getParticipations(int pollId) {
        return em.createQuery("SELECT COUNT(p) FROM Participant p"
                + " WHERE p.poll.id = :pollId"
                + "", Long.class)
                .setParameter("pollId", pollId)
                .getSingleResult();
    }
}
