/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

import java.util.ArrayList;
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
        return getPolls();
    }

    public List<Poll> getPolls() {
        List<Poll> result =  em.createQuery("SELECT p FROM Poll p "
                + "", Poll.class).getResultList();
        
        return result;
    }

    public Poll getPoll(int pollId) {
        for (Poll poll : getPolls()) {
            if (poll.getId() == pollId) {
                return poll;
            }
        }
        return null;
    }

    /**
     * Returns all polls with this organizer.
     *
     * @param organizerId
     * @return
     */
    public List<Poll> getPolls(int organizerId) {

        List<Poll> result = new ArrayList();

        for (Poll poll : getPolls()) {
            for (Organizer organiser : poll.getOrganizer()) {
                if (organiser.getId() == organizerId) {
                    result.add(poll);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Get poll with titel and description.
     * @param titel
     * @param description
     * @return 
     */
    public Poll getPoll(String titel, String description) {
        for (Poll poll : getPolls()) {
            if (poll.getDescription().equals(description) && poll.getTitle().equals(titel)) {
                return poll;
            }
        }
        return null;
    }

    /**
     * Get Polls of organizer with of organizer with offset and max.
     * @param organizerId
     * @param offset
     * @param max
     * @return 
     */
    public List<Poll> getPolls(int organizerId, int offset, int max) {
        List<Poll> polls = getPolls(organizerId);
        
        return polls.subList(offset,offset + max);
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
