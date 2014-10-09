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
import jjlm.votes.persistence.entities.PollState;

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

    public Poll getPoll(String name, String description) {
        return em.createQuery("SELECT p FROM Poll p"
                + " where p.name=:name"
                + " AND p.description=:description"
                + "", Poll.class)
                .setParameter("name", name)
                .setParameter("description", description)
                .getResultList().get(0);
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

    public PollState getStateOfPoll(int pollId) {

        if (!em.createNativeQuery("select id from poll p where"
                + " p.startpoll is null"
                + " and p.id = " + pollId)
                .getResultList().isEmpty()) {
            return PollState.PREPARED;
        }

        if (em.createNativeQuery("select p.id from poll p, participant"
                + " where p.id = participant.poll_id"
                + " and p.startpoll >= current_date"
                + " and participant.hasvoted = 0"
                + " and p.id = " + pollId)
                .getResultList().isEmpty()) {
            return PollState.STARTED;
        }

        if (!em.createNativeQuery("select p.id from poll p, participant"
                + " where p.id = participant.poll_id"
                + " and p.startpoll >= current_date"
                + " and participant.hasvoted = 1"
                + " and p.id = " + pollId)
                .getResultList().isEmpty()) {
            return PollState.VOTING;
        }

        if (em.createNativeQuery("select p.id from poll p, participant"
                + " where p.id = participant.POLL_ID"
                + " and p.endpoll >= current_date"
                + " and participant.hasvoted = 0"
                + " and p.id = " + pollId)
                .getResultList().isEmpty()) {
            return PollState.FINISHED;
        }

        return PollState.PREPARED;
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

    /*
     if (hideEmptyTeams) {
     return em.createQuery("SELECT COUNT(t) FROM Team t"
     + " WHERE t.course.id = :courseId "
     + " AND t.members IS NOT EMPTY",
     Long.class)
     .setParameter("courseId", courseId)
     .getSingleResult();
     }
     */
}
