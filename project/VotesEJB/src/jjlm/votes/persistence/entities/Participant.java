/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import jjlm.votes.logic.to.ParticipantTO;

/**
 *
 * @author henny
 */
@Entity
public class Participant extends AbstractEntity<Participant, ParticipantTO> {

    private static final long serialVersionUID = 3438053285465095313L;

    private String email;
    private boolean hasVoted;

    private Poll poll;

    public Participant() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    @ManyToOne
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public ParticipantTO createTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
